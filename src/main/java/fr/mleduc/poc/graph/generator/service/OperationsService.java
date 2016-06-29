package fr.mleduc.poc.graph.generator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.typedef.TypeDef;
import fr.mleduc.poc.graph.generator.operations.Attach;
import fr.mleduc.poc.graph.generator.operations.Bind;
import fr.mleduc.poc.graph.generator.operations.CreateChannel;
import fr.mleduc.poc.graph.generator.operations.CreateComponent;
import fr.mleduc.poc.graph.generator.operations.CreateGroup;
import fr.mleduc.poc.graph.generator.operations.CreateNode;
import fr.mleduc.poc.graph.generator.operations.IOperation;
import fr.mleduc.poc.graph.generator.operations.RemoveComponent;
import fr.mleduc.poc.graph.generator.operations.Set;
import fr.mleduc.poc.graph.generator.operations.SetFragment;

public class OperationsService {

	private final Random generator;
	private final TypeDefService typeDefService;
	private int nodes;
	private int components;
	private int channels;
	private int groups;

	public OperationsService(final Random generator) {
		this.generator = generator;
		this.typeDefService = new TypeDefService(generator);
	}

	public OperationsService withNodes(final int nodes) {
		this.nodes = nodes;
		return this;
	}

	public OperationsService withComponents(final int components) {
		this.components = components;
		return this;
	}

	public OperationsService withChannels(final int channels) {
		this.channels = channels;
		return this;
	}

	public OperationsService withGroups(final int groups) {
		this.groups = groups;
		return this;
	}

	public List<IOperation> initialize() {
		final List<CreateGroup> sGroups = IntStream.range(0, groups).boxed()
				.map(idx -> new CreateGroup("group" + idx, typeDefService.getRandomGroup()))
				.collect(Collectors.toList());

		final List<CreateNode> sNodes = IntStream.range(0, nodes).boxed()
				.map(idx -> new CreateNode("node" + idx, typeDefService.getRandomNode())).collect(Collectors.toList());

		final List<IOperation> sAttach = sNodes.stream().map(new Function<CreateNode, Stream<IOperation>>() {
			@Override
			public Stream<IOperation> apply(final CreateNode node) {
				final CreateGroup randomGroup = sGroups.get(generator.nextInt(sGroups.size()));
				final Attach attachOperation = new Attach(node.getName(), randomGroup.getName());
				final Stream<SetFragment> sets = randomGroup.getTypeDef().getDefaultFragmentDictionary().entrySet()
						.stream().map(entry -> new SetFragment(randomGroup.getName(), entry.getKey(), node.getName(),
								entry.getValue()));
				return Stream.of(Stream.of(attachOperation), sets).flatMap(Function.identity());
			}
		}).flatMap(Function.identity()).collect(Collectors.toList());

		final List<CreateComponent> sComponent = IntStream.range(0, components).boxed().map(idx -> {
			final CreateNode randomNode = sNodes.get(generator.nextInt(sNodes.size()));
			return new CreateComponent("component" + idx, randomNode.getName(), typeDefService.getRandomComponent());
		}).collect(Collectors.toList());

		final List<CreateChannel> sChannels = IntStream.range(0, channels).boxed().map(idx -> {
			final String chanId = "chan" + idx;
			final TypeDef randomTypeDef = typeDefService.getRandomChan();
			final Map<String, String> chanDico = new HashMap<>(randomTypeDef.getDefaultDictionary());
			if (Objects.equals(randomTypeDef.getName(), "WSChan")) {
				chanDico.put("path", chanDico.get("path") + "_" + chanId);
			}
			return new CreateChannel(chanId, randomTypeDef, chanDico);
		}).collect(Collectors.toList());

		final List<Set> sConfigureChannels = sChannels.stream().map(chan -> {
			return chan.getDictionary().entrySet().stream()
					.map(entry -> new Set(chan.getName(), entry.getKey(), entry.getValue()));
		}).flatMap(Function.identity()).collect(Collectors.toList());

		final List<Bind> sBind = sComponent.stream().map(new Function<CreateComponent, Stream<Bind>>() {
			@Override
			public Stream<Bind> apply(final CreateComponent component) {
				final Stream<Bind> inputs = component.getTypeDef().getInputs().stream().map(input -> {
					return randomlyBind(component.getNodeName(), component.getName(), input, sChannels);
				}).flatMap(Function.identity());
				final Stream<Bind> outputs = component.getTypeDef().getOutputs().stream().map(input -> {
					return randomlyBind(component.getNodeName(), component.getName(), input, sChannels);
				}).flatMap(Function.identity());
				return Stream.concat(inputs, outputs);
			}
		}).flatMap(Function.identity()).collect(Collectors.toList());

		final ArrayList<IOperation> ret = new ArrayList<>();
		ret.addAll(sGroups);
		ret.add(new Set("group0", "master", "node0"));
		ret.addAll(sNodes);
		ret.addAll(sAttach);
		ret.addAll(sComponent);
		ret.addAll(sChannels);
		ret.addAll(sConfigureChannels);
		ret.addAll(sBind);
		return ret;
	}

	private Stream<Bind> randomlyBind(final String nodeName, final String componentName, final String port,
			final List<CreateChannel> channels) {
		final List<Bind> ret = new ArrayList<>();
		for (int i = 1;; i++) {
			final float level = generator.nextFloat();
			if (level < activationFunction(i)) {
				final CreateChannel chan = channels.get(generator.nextInt(channels.size()));
				ret.add(new Bind(nodeName, componentName, port, chan.getName()));
			} else {
				break;
			}
		}
		return ret.stream();
	}

	private float activationFunction(final int j) {
		final float i = j;
		return 1.0f / (i * i + 1.0f);
	}

	public List<IOperation> next(final Graph graph) {
		Collections.shuffle(graph.getComponents(), generator);
		return graph.getComponents().stream().limit(10)
				.map(component -> new RemoveComponent(component.getNode().getName(), component.getName()))
				.collect(Collectors.toList());
	}

}
