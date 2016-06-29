package fr.mleduc.poc.graph.generator.operations;

import fr.mleduc.poc.graph.generator.graph.typedef.GroupTypeDef;

public class CreateGroup implements IOperation {

	private final String name;
	private final GroupTypeDef typeDef;

	public CreateGroup(String name, GroupTypeDef typeDef) {
		this.name = name;
		this.typeDef = typeDef;
	}

	public String getName() {
		return this.name;
	}

	public GroupTypeDef getTypeDef() {
		return this.typeDef;
	}

	@Override
	public String toString() {
		return "CreateGroup [name=" + name + ", typeDef=" + typeDef + "]";
	}

}
