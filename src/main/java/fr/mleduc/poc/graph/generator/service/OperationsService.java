package fr.mleduc.poc.graph.generator.service;

import java.util.ArrayList;
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

		final List<Bind> sBind = sComponent.stream().map(component -> {
			return (Bind) null;
		}).collect(Collectors.toList());

		final ArrayList<IOperation> ret = new ArrayList<>();
		ret.addAll(sGroups);
		ret.addAll(sNodes);
		ret.addAll(sAttach);
		ret.addAll(sComponent);
		ret.addAll(sChannels);
		ret.addAll(sBind);
		return ret;
	}

	public List<IOperation> next(final Graph graph) {
		// TODO Auto-generated method stub
		return null;
	}

}
