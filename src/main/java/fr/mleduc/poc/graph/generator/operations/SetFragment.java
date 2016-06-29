package fr.mleduc.poc.graph.generator.operations;

public class SetFragment implements IOperation {

	private final String groupName;
	private final String key;
	private final String nodeName;
	private final String value;

	public SetFragment(final String groupName, final String key, final String nodeName, final String value) {
		this.groupName = groupName;
		this.key = key;
		this.nodeName = nodeName;
		this.value = value;
	}

}
