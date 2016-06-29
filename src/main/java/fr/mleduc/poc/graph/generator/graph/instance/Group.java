package fr.mleduc.poc.graph.generator.graph.instance;

import fr.mleduc.poc.graph.generator.graph.typedef.GroupTypeDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group extends Instance {

    private final String name;
    private final List<Node> nodes = new ArrayList<>();
    private GroupTypeDef typeDef;
    private Map<String, Map<String, String>> fragments = new HashMap<>();

    public Group(final String name, final GroupTypeDef typeDef) {
        super(typeDef.getDefaultDictionary());
        this.name = name;
        this.setTypeDef(typeDef);
    }

    public String getName() {
        return name;
    }

    public void addNode(final Node node, Map<String, String> nodeFragment) {
        this.getNodes().add(node);
        this.fragments.put(node.getName(), nodeFragment);
    }

    public GroupTypeDef getTypeDef() {
        return typeDef;
    }

    public void setTypeDef(GroupTypeDef typeDef) {
        this.typeDef = typeDef;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Map<String, String> getNodeFragment(final String node) {
        return this.fragments.get(node);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    
    
}
