package fr.mleduc.poc.graph.generator.registry;

public class TypeFQN {
	public String name;
	public String version;

	public TypeFQN(final String name) {
		this(name, null);
	}

	public TypeFQN(final String name, final String version) {
		this.name = name;
		this.version = version;
	}
}
