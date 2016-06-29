package fr.mleduc.poc.graph.generator.operations;

import fr.mleduc.poc.graph.generator.graph.typedef.ComponentTypeDef;

public class CreateComponent implements IOperation {

	private final String name;
	private final String nodeName;
	@Override
	public String toString() {
		return "CreateComponent [name=" + name + ", nodeName=" + nodeName + ", typeDef=" + typeDef + "]";
	}

	private final ComponentTypeDef typeDef;

	public CreateComponent(final String name, final String nodeName, final ComponentTypeDef typeDef) {
		this.name = name;
		this.nodeName = nodeName;
		this.typeDef = typeDef;
	}

	public ComponentTypeDef getTypeDef() {
		return typeDef;
	}

	public String getName() {
		return name;
	}

	public String getNodeName() {
		return nodeName;
	}

}
