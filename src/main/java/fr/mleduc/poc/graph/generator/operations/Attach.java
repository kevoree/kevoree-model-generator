package fr.mleduc.poc.graph.generator.operations;

public class Attach implements IOperation {

	private final String nodeName;
	private final String groupName;

	public Attach(String nodeName, String groupName) {
		this.nodeName = nodeName;
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "Attach [nodeName=" + getNodeName() + ", groupName=" + getGroupName() + "]";
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getGroupName() {
		return groupName;
	}

}
