package fr.mleduc.poc.graph.generator.operations;

import fr.mleduc.poc.graph.generator.graph.typedef.TypeDef;

public class CreateNode implements IOperation {

	private final String name;
	private final TypeDef typeDef;

	public CreateNode(final String name, final TypeDef typeDef) {
		this.name = name;
		this.typeDef = typeDef;
	}

	@Override
	public String toString() {
		return "CreateNode [name=" + name + ", typeDef=" + typeDef + "]";
	}

	public String getName() {
		return name;
	}

}
