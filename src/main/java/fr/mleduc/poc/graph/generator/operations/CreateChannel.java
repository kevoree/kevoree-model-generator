package fr.mleduc.poc.graph.generator.operations;

import java.util.Map;

import fr.mleduc.poc.graph.generator.graph.typedef.TypeDef;

public class CreateChannel implements IOperation {

	private final String name;
	private final TypeDef typeDef;
	private final Map<String, String> dictionary;

	@Override
	public String toString() {
		return "CreateChannel [name=" + name + ", typeDef=" + typeDef + ", dictionary=" + dictionary + "]";
	}

	public CreateChannel(final String name, final TypeDef typeDef, final Map<String, String> dictionary) {
		this.name = name;
		this.typeDef = typeDef;
		this.dictionary = dictionary;
	}

	public String getName() {
		return name;
	}

}
