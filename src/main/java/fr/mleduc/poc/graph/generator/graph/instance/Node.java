package fr.mleduc.poc.graph.generator.graph.instance;

import java.util.ArrayList;
import java.util.List;

import fr.mleduc.poc.graph.generator.graph.typedef.TypeDef;

/**
 * Created by mleduc on 24/06/16.
 */
public class Node extends Instance {
	private final String name;
	private final List<Component> components = new ArrayList<>();
	private final TypeDef typeDef;
	private Group group;

	public Node(final String name, final TypeDef typeDef) {
		super(typeDef.getDefaultDictionary());
		this.name = name;
		this.typeDef = typeDef;
	}

	public String getName() {
		return name;
	}

	public void addComponent(final Component component) {
		this.components.add(component);
	}

	public TypeDef getTypeDef() {
		return typeDef;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(final Group group) {
		this.group = group;

	}

}