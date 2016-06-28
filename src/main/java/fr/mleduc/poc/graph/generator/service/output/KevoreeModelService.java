package fr.mleduc.poc.graph.generator.service.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.kevoree.Channel;
import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.Dictionary;
import org.kevoree.Instance;
import org.kevoree.MBinding;
import org.kevoree.Port;
import org.kevoree.TypeDefinition;
import org.kevoree.Value;
import org.kevoree.factory.DefaultKevoreeFactory;
import org.kevoree.factory.KevoreeFactory;
import org.kevoree.pmodeling.api.json.JSONModelSerializer;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.instance.Chan;
import fr.mleduc.poc.graph.generator.graph.instance.Component;
import fr.mleduc.poc.graph.generator.registry.KevoreeRegistryResolver;
import fr.mleduc.poc.graph.generator.registry.TypeDefinitionResolver;
import fr.mleduc.poc.graph.generator.registry.TypeFQN;

/**
 * Created by mleduc on 27/06/16.
 */
public class KevoreeModelService {

	private final KevoreeRegistryResolver kevoreeRegistryResolver = new KevoreeRegistryResolver();

	public String process(final Graph graph) {
		final KevoreeFactory kevoreeFactory = new DefaultKevoreeFactory();
		final ContainerRoot root = kevoreeFactory.createContainerRoot();
		kevoreeFactory.root(root);

		initNodes(graph, kevoreeFactory, root);
		initComponents(graph, kevoreeFactory, root);
		initChans(graph, kevoreeFactory, root);

		final JSONModelSerializer jsonSerializer = kevoreeFactory.createJSONSerializer();
		return jsonSerializer.serialize(root);
	}

	private void initChans(final Graph graph, final KevoreeFactory kevoreeFactory, final ContainerRoot root) {
		graph.getChans().stream().forEach(chan -> {
			final Channel channelInstance = kevoreeFactory.createChannel();
			channelInstance.setStarted(true);
			channelInstance.setName(chan.getName());
			final String typeDefName = chan.getTypeDef().getName();
			final TypeDefinition componentTypeDefinition = resolveTypeDef(kevoreeFactory, root, typeDefName);
			channelInstance.setTypeDefinition(componentTypeDefinition);
			defineDictionary(kevoreeFactory, chan.getDictionary(), channelInstance);
			root.addHubs(channelInstance);
		});

		graph.getBinds().forEach(bind -> {
			final Chan chan = bind.getChan();
			final Component component = bind.getComponent();

			root.getHubs().stream().filter(channelInstance -> Objects.equals(channelInstance.getName(), chan.getName()))
					.findFirst().ifPresent(channelInstance -> {
						root.getNodes().stream().filter(containerNode1 -> Objects.equals(containerNode1.getName(),
								component.getNode().getName())).findFirst().ifPresent(containerNode2 -> {
									containerNode2.getComponents()
											.stream().filter(componentInstance -> Objects
													.equals(componentInstance.getName(), component.getName()))
											.findFirst().ifPresent(componentInstance -> {

												/*
												 * warning : currently a
												 * conflict can arise if a DU
												 * with an input port and and
												 * output port with the similar
												 * names is used during the
												 * generation (only the provided
												 * port will be used).
												 */
												final Port providedPort = componentInstance
														.findProvidedByID(bind.getPort());
												if (providedPort != null) {
													attachPort(root, kevoreeFactory, channelInstance, providedPort);
												} else {
													final Port requiredPort = componentInstance
															.findRequiredByID(bind.getPort());
													if (requiredPort != null) {
														attachPort(root, kevoreeFactory, channelInstance, requiredPort);
													}
												}
											});

								});
						;
					});

		});

	}

	private void attachPort(final ContainerRoot root, final KevoreeFactory kevoreeFactory, final Channel channelInstance,
			final Port port) {
		final MBinding mBinding = kevoreeFactory.createMBinding();
		mBinding.setPort(port);
		mBinding.setHub(channelInstance);
		port.getBindings().add(mBinding);
		channelInstance.getBindings().add(mBinding);
		root.addMBindings(mBinding);
	}

	private void initComponents(final Graph graph, final KevoreeFactory kevoreeFactory, final ContainerRoot root) {
		graph.getComponents().stream().forEach(component -> {
			root.getNodes().stream().filter(t -> Objects.equals(t.getName(), component.getNode().getName())).findFirst()
					.ifPresent(node -> {
						final ComponentInstance componentInstance = kevoreeFactory.createComponentInstance();
						componentInstance.setStarted(true);
						componentInstance.setName(component.getName());

						final String typeDefName = component.getTypeDef().getName();
						final TypeDefinition componentTypeDefinition = resolveTypeDef(kevoreeFactory, root,
								typeDefName);

						componentInstance.setTypeDefinition(componentTypeDefinition);

						defineDictionary(kevoreeFactory, component.getDictionary(), componentInstance);

						componentInstance.addAllRequired(component.getTypeDef().getOutputs().stream().map(portName -> {
							final Port port = kevoreeFactory.createPort();
							port.setName(portName);
							port.setBindings(new ArrayList<>());
							return port;
						}).collect(Collectors.toList()));

						componentInstance.addAllProvided(component.getTypeDef().getInputs().stream().map(portName -> {
							final Port port = kevoreeFactory.createPort();
							port.setName(portName);
							port.setBindings(new ArrayList<>());
							return port;
						}).collect(Collectors.toList()));

						node.addComponents(componentInstance);
					});

		});
	}

	private void initNodes(final Graph graph, final KevoreeFactory kevoreeFactory, final ContainerRoot root) {
		final List<ContainerNode> nodes = graph.getNodes().stream().map(node -> {
			final ContainerNode containerNode = kevoreeFactory.createContainerNode();

			final String typeDefName = node.getTypeDef().getName();
			final TypeDefinition nodeTypeDefinition = resolveTypeDef(kevoreeFactory, root, typeDefName);

			containerNode.setName(node.getName());
			containerNode.setStarted(true);
			containerNode.setTypeDefinition(nodeTypeDefinition);

			defineDictionary(kevoreeFactory, node.getDictionary(), containerNode);

			return containerNode;
		}).collect(Collectors.toList());
		root.addAllNodes(nodes);

	}

	private TypeDefinition resolveTypeDef(final KevoreeFactory kevoreeFactory, final ContainerRoot root,
			final String typeDefName) {
		try {
			final List<TypeFQN> fqns = new ArrayList<>();
			fqns.add(new TypeFQN(typeDefName));
			kevoreeRegistryResolver.resolve(fqns, root, kevoreeFactory);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final TypeDefinition nodeTypeDefinition = TypeDefinitionResolver.resolve(root, null, typeDefName, null);
		return nodeTypeDefinition;
	}

	private void defineDictionary(final KevoreeFactory kevoreeFactory, final Map<String, String> refDictionary,
			final Instance containerNode) {
		final Dictionary dictionary = kevoreeFactory.createDictionary();

		dictionary.setValues(refDictionary.entrySet().stream().map(entry -> {
			final Value value = kevoreeFactory.createValue();
			value.setName(entry.getKey());
			value.setValue(entry.getValue());
			return value;
		}).collect(Collectors.toList()));
		containerNode.setDictionary(dictionary);
	}
}
