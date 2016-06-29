package fr.mleduc.poc.graph.generator.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.mleduc.poc.graph.generator.graph.instance.Chan;
import fr.mleduc.poc.graph.generator.graph.instance.Component;
import fr.mleduc.poc.graph.generator.graph.instance.Group;
import fr.mleduc.poc.graph.generator.graph.instance.Node;

/**
 * Created by mleduc on 24/06/16.
 */
public class Graph {

	private final List<Node> nodes = new ArrayList<>();
	private final List<Component> components = new ArrayList<>();
	private final List<Chan> chans = new ArrayList<>();
	private final List<Bind> binds = new ArrayList<>();
	private final List<Group> groups = new ArrayList<>();

	private final Random generator;

	public Graph(final Random generator) {
		this.generator = generator;
	}

	public void addNode(final Node n) {
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

	public void addNodeToGroup(final Group group, final Node node, Map<String,String> nodeFragment) {
		group.addNode(node, nodeFragment);
		node.setGroup(group);
	}

	public void addGroup(final Group group) {
		this.groups.add(group);
	}

	public void addChan(final Chan chan) {
		this.chans.add(chan);
	}

	public Chan getRandomChan() {
		return this.chans.get(generator.nextInt(this.chans.size()));
	}

	public void bind(final Component component, final String port, final Chan chan) {
		this.binds.add(new Bind(component, port, chan));
	}

	public List<Group> getGroups() {
		return groups;
	}

	public Group getRandomGroup() {
		return this.groups.get(generator.nextInt(this.groups.size()));
	}
}
