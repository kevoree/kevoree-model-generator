package fr.mleduc.poc.graph.generator.graph;

import fr.mleduc.poc.graph.generator.graph.instance.Chan;
import fr.mleduc.poc.graph.generator.graph.instance.Component;

/**
 * Created by mleduc on 27/06/16.
 */
public class Bind {
	private final Component component;
	private final String port;
	private final Chan chan;

	public Bind(Component component, String port, Chan chan) {
		this.component = component;
		this.port = port;
		this.chan = chan;
	}

	public Component getComponent() {
		return component;
	}

	public String getPort() {
		return port;
	}

	public Chan getChan() {
		return chan;
	}
}
