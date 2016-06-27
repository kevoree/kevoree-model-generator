package fr.mleduc.poc.graph.generator;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.settings.ChannelSettings;
import fr.mleduc.poc.graph.generator.service.GraphService;
import fr.mleduc.poc.graph.generator.service.KevPrinterService;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Created by mleduc on 24/06/16.
 */
public class Application {

    public static void main(String[] args) {


        long seed = initSeed(args);

        final Random generator = new Random();
        generator.setSeed(seed);

        final GraphService graphService = new GraphService(generator)
                .withNodes(100)
                .withComponents(500)
                .withChannels(250)
                .withChannelSettings(new ChannelSettings("ws.kevoree.org", "benchmark", 80));

        final Graph graph = graphService.generate().getGraph();

        final String kevs = new KevPrinterService().process(graph);
        System.out.println(kevs);
    }

    private static long initSeed(String[] args) {
        long seed;
        if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
            try {
                seed = Long.parseLong(args[0]);
                System.out.println("Using user defined " + seed + " as the random generator seed");
            } catch (NumberFormatException e) {
                seed = -1;
                System.out.println("User defined seed " + args[0] + "is not a valid long");
                System.exit(-1);
            }
        } else {
            seed = new Random().nextLong();
            System.out.println("Using randomly generated " + seed + " as the random generator seed");
        }
        return seed;
    }
}
