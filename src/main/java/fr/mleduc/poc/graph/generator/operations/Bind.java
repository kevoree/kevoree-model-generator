package fr.mleduc.poc.graph.generator.operations;

public class Bind implements IOperation {

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

	public String getChanName() {
		return chanName;
	}

	public String getComponentName() {
		return componentName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "Bind [nodeName=" + nodeName + ", componentName=" + componentName + ", port=" + port + ", chanName="
				+ chanName + "]";
	}

}
