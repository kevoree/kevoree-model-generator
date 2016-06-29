package fr.mleduc.poc.graph.generator.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import fr.mleduc.poc.graph.generator.graph.instance.Chan;
import fr.mleduc.poc.graph.generator.graph.instance.Component;
import fr.mleduc.poc.graph.generator.graph.instance.Group;
import fr.mleduc.poc.graph.generator.graph.instance.Instance;
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

	public void addComponentToNode(final Node node, final Component component) {
		node.addComponent(component);
		component.setNode(node);
		this.components.add(component);
	}

	public void addNodeToGroup(final Group group, final Node node) {
		group.addNode(node);
		node.setGroup(group);
	}

	public void addGroup(final Group group) {
		this.groups.add(group);
	}

	public void addChan(final Chan chan) {
		this.chans.add(chan);
	}

	public void bind(final Component component, final String port, final Chan chan) {
		this.binds.add(new Bind(component, port, chan));
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void addComponent(final Component component) {
		this.components.add(component);

	}

	public Node getNode(final String nodeName) {
		return this.nodes.stream().filter(node -> Objects.equals(nodeName, node.getName())).findFirst().orElse(null);
	}

	public Group getGroup(final String groupName) {
		return this.groups.stream().filter(group -> Objects.equals(group.getName(), groupName)).findFirst()
				.orElse(null);
	}

	public Component getComponent(final String nodeName, final String componentName) {
		return this.components.stream().filter(component -> Objects.equals(component.getNode().getName(), nodeName))
				.filter(component -> Objects.equals(component.getName(), componentName)).findFirst().orElse(null);
	}

	public Chan getChan(final String chanName) {
		return this.chans.stream().filter(chan -> Objects.equals(chan.getName(), chanName)).findFirst().orElse(null);
	}

	public Instance getInstance(final String instance) {
		final Optional<Node> node = Optional.ofNullable(this.getNode(instance));
		final Optional<Group> group = Optional.ofNullable(this.getGroup(instance));
		final String[] path = instance.split("\\.");
		final Optional<Component> comp;
		if (path.length == 2) {
			comp = Optional.ofNullable(this.getComponent(path[0], path[1]));
		} else {
			comp = Optional.empty();
		}
		final Optional<Chan> chan = Optional.ofNullable(this.getChan(instance));
		return Stream.of(node, group, comp, chan).filter(Optional::isPresent).map(Optional::get).findFirst().orElse(null);

	}
}
