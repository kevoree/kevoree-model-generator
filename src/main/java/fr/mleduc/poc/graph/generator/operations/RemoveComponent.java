package fr.mleduc.poc.graph.generator.operations;

public class RemoveComponent implements IOperation {

	private final String name;

	private final String nodeName;

	public RemoveComponent(final String nodeName, final String name) {
		this.nodeName = nodeName;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getNodeName() {
		return nodeName;
	}

}
