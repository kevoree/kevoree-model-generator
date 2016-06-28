package fr.mleduc.poc.graph.generator.graph.typedef;

import java.util.Map;

public class GroupTypeDef extends TypeDef {

	private final Map<String, String> defaultFragmentDictionary;

	public GroupTypeDef(final String name, final Map<String, String> defaultDictionary,
			final Map<String, String> defaultFragmentDictionary) {
		super(name, defaultDictionary);
		this.defaultFragmentDictionary = defaultFragmentDictionary;

	}

	public Map<String, String> getDefaultFragmentDictionary() {
		return defaultFragmentDictionary;
	}

}
