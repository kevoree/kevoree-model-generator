package fr.mleduc.poc.graph.generator.service.output;

import fr.mleduc.poc.graph.generator.graph.Chan;
import fr.mleduc.poc.graph.generator.graph.Component;
import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mleduc on 24/06/16.
 */
public class KevPrinterService {
	public String process(final Graph graph) {
		final Stream<String> nodesStream = graph.getNodes().stream()
				.map(component -> "add " + component.getName() + ": " + component.getTypeDef().getName());

		final Stream<String> componentsStream = graph.getComponents().stream().map(component -> "add "
				+ component.getNode().getName() + "." + component.getName() + ": " + component.getTypeDef().getName());

		final Stream<String> chansStream = graph.getChans().stream().map(chan -> {
			final List<String> lst = new ArrayList<>();
			lst.add("add " + chan.getName() + ": WSChan");
			lst.add("set " + chan.getName() + ".host = '" + chan.getDictionary().get("host") + "'");
			lst.add("set " + chan.getName() + ".port = '" + chan.getDictionary().get("port") + "'");
			lst.add("set " + chan.getName() + ".path = '" + chan.getDictionary().get("path") + "'");
			return lst.stream();
		}).flatMap(Function.identity());

		final Stream<String> bindsStream = graph.getBinds().stream().map(bind -> {
			final Component component = bind.getComponent();
			final Node node = component.getNode();
			final String port = bind.getPort();
			final Chan chan = bind.getChan();
			final String portPath = node.getName() + "." + component.getName() + "." + port;
			return "bind " + portPath + " " + chan.getName();
		});

		final List<String> res = Stream.of(nodesStream, componentsStream, chansStream, bindsStream)
				.flatMap(Function.identity()).collect(Collectors.toList());
		return StringUtils.join(res, "\n");
	}
}
