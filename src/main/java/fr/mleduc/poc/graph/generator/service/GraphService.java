package fr.mleduc.poc.graph.generator.service;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.instance.Chan;
import fr.mleduc.poc.graph.generator.graph.instance.Component;
import fr.mleduc.poc.graph.generator.graph.instance.Group;
import fr.mleduc.poc.graph.generator.graph.instance.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by mleduc on 24/06/16.
 */
public class GraphService {

	private final Random generator;
	private final TypeDefService typeDefService;
	private int nodes = 1;
	private int components = 1;
	private int channels = 1;
	private final Graph graph;
	private int groups = 1;

	public GraphService() {
		this.generator = new Random();
		this.typeDefService = new TypeDefService(generator);
		this.graph = new Graph(generator);
	}

	public GraphService(final Random generator) {
		this.generator = generator;
		this.typeDefService = new TypeDefService(generator);
		this.graph = new Graph(generator);
	}

	public GraphService withNodes(final int nodes) {
		this.nodes = nodes;
		return this;
	}

	public GraphService withComponents(final int components) {
		this.components = components;
		return this;
	}

	public GraphService initialize() {

		IntStream.range(0, groups)
				.forEach(value -> graph.addGroup(new Group("group" + value, typeDefService.getRandomGroup())));

		IntStream.range(0, nodes).forEach(value -> {
			final Node node = new Node("node" + value, typeDefService.getRandomNode());
			graph.addNode(node);
			final Group group = graph.getRandomGroup();
			graph.addNodeToGroup(group, node, new HashMap<>(group.getTypeDef().getDefaultFragmentDictionary()));

		});

		IntStream.range(0, components).forEach(value -> {
			final Node node = graph.getRandomNode();
			final Component component = new Component("component" + value, typeDefService.getRandomComponent());
			graph.addComponentToNode(node, component);
		});

		IntStream.range(0, channels).forEach(value -> {
			final String chanId = "chan" + value;
			final Chan chan = new Chan(chanId, typeDefService.getRandomChan());
			if (chan.getTypeDef().getName().equals("WSChan")) {

				final Map<String, String> newDico = new HashMap<>(chan.getDictionary());

				newDico.put("path", newDico.get("path") + "_" + chanId);

				chan.setDictionary(newDico);
			}

			graph.addChan(chan);
		});

		graph.getComponents().stream().forEach(component -> {
			for (final String port : component.getTypeDef().getInputs()) {
				randomlyBind(component, port);
			}

			for (final String port : component.getTypeDef().getOutputs()) {
				randomlyBind(component, port);
			}
		});

		return this;
	}

	private void randomlyBind(final Component component, final String input) {
		for (int i = 1;; i++) {
			final float level = generator.nextFloat();
			if (level < activationFunction(i)) {
				final Chan chan = graph.getRandomChan();
				graph.bind(component, input, chan);
			} else {
				break;
			}
		}
	}

	private float activationFunction(final int j) {
		final float i = j;
		return 1.0f / (i * i + 1.0f);
	}

	public Graph getGraph() {
		return this.graph;
	}

	public GraphService withChannels(final int channels) {
		this.channels = channels;
		return this;
	}

	public GraphService withGroups(final int groups) {
		this.groups = groups;
		return this;
	}

	public GraphService nextGeneration() {
		// operate a serie of transformations on the current graph.
		// 1 - randomly removing nodes / same logic but by adding nodes
		// 2 - randomly removing components / same logic but by adding components
		// 3 - randomly updating dictionaries 
		// 4 - randomly removing bindings / same logic but by adding bindings
		
		//graph.getRandomComponent()
		
		/**
		 * Conception:
		 * The transformation of the graph can either be seen as a serie of modifications of the graph
		 * 
		 *  Or the whole graph can be seen as the derivate of a serie of consistents operations on a graph.
		 *  
		 *  The second solution allows us to generate updating kevscript but we have to update much for of the service logic to do so. 
		 */
		
		return null;
	}

}
