package fr.mleduc.poc.graph.generator.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mleduc on 24/06/16.
 */
public class Node {
    private final String name;
    private final List<Component> components = new ArrayList<>();
    private final TypeDef typeDef;
    private final Map<String, String> dictionary;

    public Node(final String name, final TypeDef typeDef) {
        this.name = name;
        this.typeDef = typeDef;
        this.dictionary = typeDef.getDefaultDictionary();
    }

    public String getName() {
        return name;
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public TypeDef getTypeDef() {
        return typeDef;
    }

    public Map<String, String> getDictionary() {
        return dictionary;
    }
}
