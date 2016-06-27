package fr.mleduc.poc.graph.generator.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mleduc on 24/06/16.
 */
public class ComponentTypeDef extends TypeDef {
    private final List<String> inputs;
    private final List<String> outputs;


    public ComponentTypeDef(String name, List<String> inputPorts, List<String> outputPorts) {
        super(name);
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
