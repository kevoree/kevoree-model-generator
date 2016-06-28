package fr.mleduc.poc.graph.generator.service;

import fr.mleduc.poc.graph.generator.graph.ComponentTypeDef;
import fr.mleduc.poc.graph.generator.graph.TypeDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        final Map<String, String> tickerDefaultDictionary = new HashMap<>();
        tickerDefaultDictionary.put("period", "3000");
        tickerDefaultDictionary.put("random", "false");
        components.add(new ComponentTypeDef("Ticker", tickerDefaultDictionary, null, outputsTicker));
        List<String> inputsConsole = new ArrayList<>();
        inputsConsole.add("input");
        components.add(new ComponentTypeDef("ConsolePrinter", new HashMap<>(), inputsConsole, null));

        HashMap<String, String> javaNodeDefaultDictionary = new HashMap<>();
        javaNodeDefaultDictionary.put("jvmArgs", "");
        javaNodeDefaultDictionary.put("log", "INFO");
        nodes.add(new TypeDef("JavaNode", javaNodeDefaultDictionary));
        Map<String, String> javascriptNodeDefaultDictionary = new HashMap<>();
        javascriptNodeDefaultDictionary.put("logLevel", "DEBUG");
        nodes.add(new TypeDef("JavascriptNode", javascriptNodeDefaultDictionary));
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
