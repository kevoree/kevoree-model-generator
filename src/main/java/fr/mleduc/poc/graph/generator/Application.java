package fr.mleduc.poc.graph.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.kevoree.ContainerRoot;
import org.kevoree.factory.DefaultKevoreeFactory;
import org.kevoree.pmodeling.api.json.JSONModelSerializer;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.operations.IOperation;
import fr.mleduc.poc.graph.generator.service.OperationsExecutor;
import fr.mleduc.poc.graph.generator.service.OperationsService;
import fr.mleduc.poc.graph.generator.service.output.KevPrinterService;
import fr.mleduc.poc.graph.generator.service.output.KevoreeModelService;

/**
 * Created by mleduc on 24/06/16.
 */
public class Application {

	private static JSONModelSerializer createJSONSerializer = new DefaultKevoreeFactory().createJSONSerializer();

	public static void main(final String[] args) throws FileNotFoundException, IOException {

		// final long seed = -3309530520489196191L;
		final long seed = initSeed(args);

		final Random generator = new Random();
		generator.setSeed(seed);

		final int n = 4;
		final OperationsService graphService = new OperationsService(generator).withNodes(n).withComponents(n * 5)
				.withChannels((int) (n * 2.5)).withGroups(1);

		final List<IOperation> operations = graphService.initialize();
		System.out.println(operations);
		/*final OperationsExecutor operationsExecutor = new OperationsExecutor();
		final Graph graph = operationsExecutor.proceed(new Graph(generator), operations);
		final List<IOperation> operations2 = graphService.next(graph);
		final Graph graph2 = operationsExecutor.proceed(graph, operations2);

		final KevoreeModelService kevoreeModelService = new KevoreeModelService();
		final ContainerRoot m1 = kevoreeModelService.process(graph);
		final ContainerRoot m2 = kevoreeModelService.process(graph2);
		final String json1 = createJSONSerializer.serialize(m1);
		final String json2 = createJSONSerializer.serialize(m2);

		IOUtils.write(json1, new FileOutputStream(new File("model1.json")), Charset.defaultCharset());
		IOUtils.write(json2, new FileOutputStream(new File("model2.json")), Charset.defaultCharset());
		
		final String kevs1 = new KevPrinterService().process(operations);
		final String kevs2 = new KevPrinterService().process(operations2);
		
		IOUtils.write(kevs1, new FileOutputStream(new File("model1.kevs")), Charset.defaultCharset());
		IOUtils.write(kevs2, new FileOutputStream(new File("model2.kevs")), Charset.defaultCharset());
*/
		/*
		 * saveAndDisplaysModel(graphService.initialize().getGraph(), "0");
		 * saveAndDisplaysModel(graphService.nextGeneration().getGraph(), "1");
		 */
	}

	/*
	 * private static void saveAndDisplaysModel(final Graph graph, String n)
	 * throws IOException, FileNotFoundException { final ContainerRoot model =
	 * new KevoreeModelService().process(graph); IOUtils.write(new
	 * DefaultKevoreeFactory().createJSONSerializer().serialize(model), new
	 * FileOutputStream(new File("model" + n + ".json")),
	 * Charset.defaultCharset());
	 * System.out.println("Model saved in model.json");
	 * 
	 * final String kevs = new KevPrinterService().process(graph);
	 * System.out.println(kevs); }
	 */

	private static long initSeed(final String[] args) {
		long seed;
		if ((args.length > 0) && StringUtils.isNotBlank(args[0])) {
			try {
				seed = Long.parseLong(args[0]);
				System.out.println("Using user defined " + seed + " as the random generator seed");
			} catch (final NumberFormatException e) {
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
