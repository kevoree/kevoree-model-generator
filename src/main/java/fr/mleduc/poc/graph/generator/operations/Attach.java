package fr.mleduc.poc.graph.generator.operations;

public class Attach implements IOperation {

	private String nodeName;
	private String groupName;

	public Attach(String nodeName, String groupName) {
		this.nodeName = nodeName;
		this.groupName = groupName;
	}

}
