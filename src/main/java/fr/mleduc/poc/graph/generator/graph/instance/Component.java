package fr.mleduc.poc.graph.generator.graph.instance;

import fr.mleduc.poc.graph.generator.graph.typedef.ComponentTypeDef;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}
	
	
}
