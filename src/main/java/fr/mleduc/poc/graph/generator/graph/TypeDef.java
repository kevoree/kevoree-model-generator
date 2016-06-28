package fr.mleduc.poc.graph.generator.graph;

import java.util.Map;

/**
 * Created by mleduc on 24/06/16.
 */
public class TypeDef {
    private String name;
    private Map<String, String> defaultDictionary;
    

    public TypeDef(final String name, final Map<String, String> defaultDictionary) {
        this.name = name;
        this.defaultDictionary = defaultDictionary;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getDefaultDictionary() {
        return this.defaultDictionary;
    }
}
