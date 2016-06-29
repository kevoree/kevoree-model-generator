package fr.mleduc.poc.graph.generator.operations;

public class Set implements IOperation {

	private final String instance;
	private final String key;
	private final String value;

	public Set(final String instance, final String key, final String value) {
		this.instance = instance;
		this.key = key;
		this.value = value;
	}

	public String getInstance() {
		return instance;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
