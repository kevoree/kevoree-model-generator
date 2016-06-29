package fr.mleduc.poc.graph.generator.service;

import java.util.List;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.instance.Chan;
import fr.mleduc.poc.graph.generator.graph.instance.Component;
import fr.mleduc.poc.graph.generator.graph.instance.Group;
import fr.mleduc.poc.graph.generator.graph.instance.Instance;
import fr.mleduc.poc.graph.generator.graph.instance.Node;
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
import fr.mleduc.poc.graph.generator.service.output.IOperationExecutor;

public class GraphService implements IOperationExecutor<Graph> {

	private final Graph graph;

	public GraphService() {
		this.graph = new Graph();
	}

	public GraphService(final Graph graph) {
		super();
		this.graph = graph;
	}

	@Override
	public Graph attach(final Attach opp) {
		final Group group = this.graph.getGroup(opp.getGroupName());
		final Node node = this.graph.getNode(opp.getNodeName());
		this.graph.addNodeToGroup(group, node);
		return this.graph;
	}

	@Override
	public Graph bind(final Bind opp) {
		final Component component = this.graph.getComponent(opp.getNodeName(), opp.getComponentName());
		final Chan chan = this.getGraph().getChan(opp.getChanName());
		this.graph.bind(component, opp.getPort(), chan);
		return this.graph;
	}

	@Override
	public Graph createChannel(final CreateChannel chan) {
		this.graph.addChan(new Chan(chan.getName(), chan.getTypeDef()));
		return this.graph;
	}

	@Override
	public Graph createComponent(final CreateComponent component) {
		final Node node = this.graph.getNode(component.getNodeName());
		this.graph.addComponentToNode(node, new Component(component.getName(), component.getTypeDef()));
		return this.graph;
	}

	@Override
	public Graph createGroup(final CreateGroup opp) {
		this.graph.addGroup(new Group(opp.getName(), opp.getTypeDef()));
		return graph;
	}

	@Override
	public Graph createNode(final CreateNode node) {
		this.graph.addNode(new Node(node.getName(), node.getTypeDef()));
		return this.graph;
	}

	public Graph getGraph() {
		return graph;
	}

	@Override
	public Graph process(final List<IOperation> operations) {
		operations.stream().forEach(opp -> {
			if (opp instanceof Attach) {
				attach((Attach) opp);
			} else if (opp instanceof CreateGroup) {
				createGroup((CreateGroup) opp);
			} else if (opp instanceof CreateNode) {
				createNode((CreateNode) opp);
			} else if (opp instanceof SetFragment) {
				setFragment((SetFragment) opp);
			} else if (opp instanceof CreateComponent) {
				createComponent((CreateComponent) opp);
			} else if (opp instanceof CreateChannel) {
				createChannel((CreateChannel) opp);
			} else if (opp instanceof Bind) {
				bind((Bind) opp);
			} else if (opp instanceof Set) {
				set((Set) opp);
			} else if (opp instanceof RemoveComponent) {
				removeComponent((RemoveComponent) opp);
			} else {

			}
		});
		return this.graph;
	}

	@Override
	public Graph set(final Set opp) {
		final Instance instance = this.graph.getInstance(opp.getInstance());
		instance.getDictionary().put(opp.getKey(), opp.getValue());
		return this.graph;
	}

	@Override
	public Graph setFragment(final SetFragment set) {
		final Group group = this.graph.getGroup(set.getGroupName());
		group.getNodeFragment(set.getNodeName()).put(set.getKey(), set.getValue());
		return this.graph;
	}

	@Override
	public Graph removeComponent(final RemoveComponent component) {
		this.graph.removeComponent(component.getNodeName(), component.getName());
		return this.graph;
	}

}
