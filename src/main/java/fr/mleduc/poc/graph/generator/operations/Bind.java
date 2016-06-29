package fr.mleduc.poc.graph.generator.operations;

public class Bind implements IOperation {

	@Override
	public String toString() {
		return "Bind [nodeName=" + nodeName + ", componentName=" + componentName + ", port=" + port + ", chanName="
				+ chanName + "]";
	}

	private final String nodeName;
	private final String componentName;
	private final String port;
	private final String chanName;

	public Bind(final String nodeName, final String componentName, final String port, final String chanName) {
		this.nodeName = nodeName;
		this.componentName = componentName;
		this.port = port;
		this.chanName = chanName;
	}

}
