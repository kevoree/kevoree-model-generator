package fr.mleduc.poc.graph.generator.graph;

import java.util.Map;

public abstract class Instance {

    private final Map<String, String> dictionary;

    public Instance(final Map<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public Map<String, String> getDictionary() {
        return dictionary;
    }

}
