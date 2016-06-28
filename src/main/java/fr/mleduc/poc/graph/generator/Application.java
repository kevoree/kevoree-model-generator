package fr.mleduc.poc.graph.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.settings.ChannelSettings;
import fr.mleduc.poc.graph.generator.service.GraphService;
import fr.mleduc.poc.graph.generator.service.output.KevoreeModelService;

/**
 * Created by mleduc on 24/06/16.
 */
public class Application {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //long seed = 3197945307415816667L; 
        long seed = initSeed(args);

        final Random generator = new Random();
        generator.setSeed(seed);

        final GraphService graphService = new GraphService(generator).withNodes(2).withComponents(500)
                .withChannels(250).withChannelSettings(new ChannelSettings("ws.kevoree.org", "benchmark", 80));

        final Graph graph = graphService.generate().getGraph();

        // final String kevs = new KevPrinterService().process(graph);
        // System.out.println(kevs);
        final String model = new KevoreeModelService().process(graph);
        IOUtils.write(model, new FileOutputStream(new File("model.json")), Charset.defaultCharset());
        System.out.println("Model saved in model.json");
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
