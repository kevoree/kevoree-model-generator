package fr.mleduc.poc.graph.generator.service;

import fr.mleduc.poc.graph.generator.graph.Chan;
import fr.mleduc.poc.graph.generator.graph.Component;
import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.Node;
import fr.mleduc.poc.graph.generator.graph.settings.ChannelSettings;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by mleduc on 24/06/16.
 */
public class GraphService {


    private final Random generator;
    private final TypeDefService typeDefService;
    private int nodes = 1;
    private int components = 1;
    private int channels = 1;
    private Graph graph;
    private ChannelSettings channelSettings = new ChannelSettings();

    public GraphService(final Random generator) {
        this.generator = generator;
        this.typeDefService = new TypeDefService(generator);
        this.graph = new Graph(generator);
    }

    public GraphService withNodes(int nodes) {
        this.nodes = nodes;
        return this;
    }

    public GraphService withComponents(int components) {
        this.components = components;
        return this;
    }

    public GraphService generate() {
        IntStream.range(0, nodes).forEach(value -> graph.addNode(new Node("node" + value, typeDefService.getRandomNode())));

        IntStream.range(0, components).forEach(value -> {
            final Node node = graph.getRandomNode();
            final Component component = new Component("component" + value, typeDefService.getRandomComponent());
            graph.addComponentToNode(node, component);
        });

        IntStream.range(0, channels).forEach(value -> {
            final String chanId = "chan" + value;
            final Chan chan = new Chan(chanId, new ChannelSettings(channelSettings.getHost(), channelSettings.getPath() + "_" + chanId, channelSettings.getPort()));
            graph.addChan(chan);
        });


        graph.getComponents().stream().forEach(component -> {
            for (final String port : component.getTypeDef().getInputs()) {
                randomlyBind(component, port);
            }

            for (final String port : component.getTypeDef().getOutputs()) {
                randomlyBind(component, port);
            }
        });

        return this;
    }

    private void randomlyBind(Component component, String input) {
        for (int i = 1; ; i++) {
            final float level = generator.nextFloat();
            if (level < activationFunction(i)) {
                final Chan chan = graph.getRandomChan();
                graph.bind(component, input, chan);
            } else {
                break;
            }
        }
    }

    private float activationFunction(int j) {
        float i = j;
        return 1.0f / (i * i + 1.0f);
    }

    public Graph getGraph() {
        return this.graph;
    }

    public GraphService withChannels(int channels) {
        this.channels = channels;
        return this;
    }

    public GraphService withChannelSettings(ChannelSettings channelSettings) {
        this.channelSettings = channelSettings;
        return this;
    }
}
