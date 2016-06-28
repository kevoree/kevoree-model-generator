package fr.mleduc.poc.graph.generator.graph.instance;

import java.util.Map;

public abstract class Instance {

	private Map<String, String> dictionary;

	public Instance(final Map<String, String> dictionary) {
		this.dictionary = dictionary;
	}

	public Map<String, String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(final Map<String, String> dictionary) {
		this.dictionary = dictionary;
	}

}
