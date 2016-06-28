package fr.mleduc.poc.graph.generator.service.output;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.graph.instance.Chan;
import fr.mleduc.poc.graph.generator.graph.instance.Component;
import fr.mleduc.poc.graph.generator.graph.instance.Node;

/**
 * Created by mleduc on 24/06/16.
 */
public class KevPrinterService {
	public String process(final Graph graph) {

		final Stream<String> nodesStream = graph.getNodes().stream()
				.map(component -> "add " + component.getName() + ": " + component.getTypeDef().getName());

		final Stream<String> groupsStream = graph.getGroups().stream().map(group -> {
			final List<String> lst = new ArrayList<>();
			lst.add("add " + group.getName() + ": " + group.getTypeDef().getName());

			lst.addAll(group.getDictionary().entrySet().stream()
					.map(entry -> "set " + group.getName() + "." + entry.getKey() + " = '" + entry.getValue() + "'")
					.collect(Collectors.toList()));

			final Stream<String> attachs = group.getNodes().stream()
					.map(node -> "attach " + node.getName() + " " + group.getName());

			final Stream<String> sets = group.getNodes()
					.stream().map(
							node -> group.getNodeFragment(node.getName()).entrySet()
									.stream().map(entry -> "set " + group.getName() + "." + entry.getKey() + "/"
											+ node.getName() + "= '" + entry.getValue() + "'"))
					.flatMap(Function.identity());
			return Stream.of(lst.stream(), attachs, sets).flatMap(Function.identity());
		}).flatMap(Function.identity());

		final Stream<String> componentsStream = graph.getComponents().stream().map(component -> "add "
				+ component.getNode().getName() + "." + component.getName() + ": " + component.getTypeDef().getName());

		final Stream<String> chansStream = graph.getChans().stream().map(chan -> {
			final List<String> lst = new ArrayList<>();
			lst.add("add " + chan.getName() + ": WSChan");
			lst.addAll(chan.getDictionary().entrySet().stream()
					.map(entry -> "set " + chan.getName() + "." + entry.getKey() + " = '" + entry.getValue() + "'")
					.collect(Collectors.toList()));
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

		final List<String> res = Stream.of(nodesStream, groupsStream, componentsStream, chansStream, bindsStream)
				.flatMap(Function.identity()).collect(Collectors.toList());
		return StringUtils.join(res, "\n");
	}
}
