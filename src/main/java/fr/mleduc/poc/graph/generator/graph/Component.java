package fr.mleduc.poc.graph.generator.graph;

/**
 * Created by mleduc on 24/06/16.
 */
public class Component extends Instance {
	private final String name;
	private final ComponentTypeDef typeDef;
	private Node node;

	public Component(final String name, final ComponentTypeDef typeDef) {
		super(typeDef.getDefaultDictionary());
		this.name = name;
		this.typeDef = typeDef;
	}

	public String getName() {
		return name;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public ComponentTypeDef getTypeDef() {
		return this.typeDef;
	}
}
