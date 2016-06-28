package fr.mleduc.poc.graph.generator.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mleduc on 24/06/16.
 */
public class Graph {

	private final List<Node> nodes = new ArrayList<>();
	private final List<Component> components = new ArrayList<>();
	private final List<Chan> chans = new ArrayList<>();
	private final List<Bind> binds = new ArrayList<>();

	private final Random generator;

	public Graph(final Random generator) {
		this.generator = generator;
	}

	public void addNode(Node n) {
		this.nodes.add(n);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Chan> getChans() {
		return this.chans;
	}

	public List<Bind> getBinds() {
		return this.binds;
	}

	public List<Component> getComponents() {
		return this.components;
	}

	public Node getRandomNode() {
		return this.nodes.get(generator.nextInt(this.nodes.size()));
	}

	public void addComponentToNode(final Node node, final Component component) {
		node.addComponent(component);
		component.setNode(node);
		this.components.add(component);
	}

	public void addChan(Chan chan) {
		this.chans.add(chan);
	}

	public Chan getRandomChan() {
		return this.chans.get(generator.nextInt(this.chans.size()));
	}

	public void bind(final Component component, final String port, final Chan chan) {
		this.binds.add(new Bind(component, port, chan));
	}
}
