package fr.mleduc.poc.graph.generator.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mleduc on 24/06/16.
 */
public class ComponentTypeDef extends TypeDef {
	private final List<String> inputs;
	private final List<String> outputs;

	public ComponentTypeDef(String name, final Map<String, String> defaultDictionary, List<String> inputPorts,
			List<String> outputPorts) {
		super(name, defaultDictionary);
		if (inputPorts != null) {
			inputs = inputPorts;
		} else {
			inputs = new ArrayList<>();
		}

		if (outputPorts != null) {
			outputs = outputPorts;
		} else {
			outputs = new ArrayList<>();
		}
	}

	public List<String> getInputs() {
		return inputs;
	}

	public List<String> getOutputs() {
		return outputs;
	}

}
