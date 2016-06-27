package fr.mleduc.poc.graph.generator.service;

import fr.mleduc.poc.graph.generator.graph.ComponentTypeDef;
import fr.mleduc.poc.graph.generator.graph.TypeDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mleduc on 24/06/16.
 */
public class TypeDefService {

    private final List<ComponentTypeDef> components = new ArrayList<>();
    private final List<TypeDef> nodes = new ArrayList<>();

    private final Random generator;

    public TypeDefService(final Random generator) {
        this.generator = generator;
        final List<String> outputsTicker = new ArrayList<>();
        outputsTicker.add("tick");
        components.add(new ComponentTypeDef("Ticker", null, outputsTicker));
        List<String> inputsConsole = new ArrayList<>();
        inputsConsole.add("input");
        components.add(new ComponentTypeDef("ConsolePrinter", inputsConsole, null));

        nodes.add(new TypeDef("JavaNode"));
        nodes.add(new TypeDef("JavascriptNode"));
    }


    public ComponentTypeDef getRandomComponent() {
        return getRandomOnList(this.components);
    }

    public TypeDef getRandomNode() {
        return getRandomOnList(this.nodes);
    }

    private <T extends TypeDef> T getRandomOnList(List<T> components1) {
        return components1.get(generator.nextInt(components1.size()));
    }
}
