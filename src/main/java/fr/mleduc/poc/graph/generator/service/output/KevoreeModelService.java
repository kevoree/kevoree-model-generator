package fr.mleduc.poc.graph.generator.service.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.Dictionary;
import org.kevoree.Instance;
import org.kevoree.TypeDefinition;
import org.kevoree.Value;
import org.kevoree.factory.DefaultKevoreeFactory;
import org.kevoree.factory.KevoreeFactory;
import org.kevoree.pmodeling.api.json.JSONModelSerializer;

import fr.mleduc.poc.graph.generator.graph.Component;
import fr.mleduc.poc.graph.generator.graph.Graph;
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

        final JSONModelSerializer jsonSerializer = kevoreeFactory.createJSONSerializer();
        return jsonSerializer.serialize(root);
    }

    private void initComponents(final Graph graph, final KevoreeFactory kevoreeFactory, final ContainerRoot root) {
        graph.getComponents().stream().forEach(new Consumer<Component>() {

            @Override
            public void accept(final Component component) {
                root.getNodes().stream().filter(t -> Objects.equals(t.getName(), component.getNode().getName()))
                        .findFirst().ifPresent(new Consumer<ContainerNode>() {

                    @Override
                    public void accept(final ContainerNode node) {
                        final ComponentInstance componentInstance = kevoreeFactory.createComponentInstance();
                        componentInstance.setName(component.getName());

                        final String typeDefName = component.getTypeDef().getName();
                        final TypeDefinition componentTypeDefinition = resolveTypeDef(kevoreeFactory, root,
                                typeDefName);

                        componentInstance.setTypeDefinition(componentTypeDefinition);

                        defineDictionary(kevoreeFactory, component.getDictionary(), componentInstance);
                        node.addComponents(componentInstance);
                    }
                });
                ;

            }
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
