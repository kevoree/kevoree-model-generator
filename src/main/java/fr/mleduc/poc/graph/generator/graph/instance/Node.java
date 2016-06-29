package fr.mleduc.poc.graph.generator.graph.instance;

import fr.mleduc.poc.graph.generator.graph.typedef.TypeDef;

/**
 * Created by mleduc on 24/06/16.
 */
public class Node extends Instance {
	private final String name;
	private final TypeDef typeDef;
	private Group group;

	public Node(final String name, final TypeDef typeDef) {
		super(typeDef.getDefaultDictionary());
		this.name = name;
		this.typeDef = typeDef;
	}

	public String getName() {
		return name;
	}

	public TypeDef getTypeDef() {
		return typeDef;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(final Group group) {
		this.group = group;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Node other = (Node) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
