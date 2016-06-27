package fr.mleduc.poc.graph.generator.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mleduc on 24/06/16.
 */
public class Node {
    private final String name;
    private final List<Component> components = new ArrayList<>();
    private final TypeDef typeDef;

    public Node(String name, TypeDef typeDef) {
        this.name = name;
        this.typeDef = typeDef;
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
}
