package fr.mleduc.poc.graph.generator.service.output;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import fr.mleduc.poc.graph.generator.operations.Attach;
import fr.mleduc.poc.graph.generator.operations.Bind;
import fr.mleduc.poc.graph.generator.operations.CreateChannel;
import fr.mleduc.poc.graph.generator.operations.CreateComponent;
import fr.mleduc.poc.graph.generator.operations.CreateGroup;
import fr.mleduc.poc.graph.generator.operations.CreateNode;
import fr.mleduc.poc.graph.generator.operations.IOperation;
import fr.mleduc.poc.graph.generator.operations.Set;
import fr.mleduc.poc.graph.generator.operations.SetFragment;

/**
 * Created by mleduc on 24/06/16.
 */
public class KevPrinterService {
	public String process(final List<IOperation> operations) {

		return StringUtils.join(operations.stream().map(opp -> {
			final String ret;
			if (opp instanceof Attach) {
				ret = attach((Attach) opp);
			} else if (opp instanceof CreateGroup) {
				ret = createGroup((CreateGroup) opp);
			} else if (opp instanceof CreateNode) {
				ret = createNode((CreateNode) opp);
			} else if (opp instanceof SetFragment) {
				ret = setFragment((SetFragment) opp);
			} else if (opp instanceof CreateComponent) {
				ret = createComponent((CreateComponent) opp);
			} else if (opp instanceof CreateChannel) {
				ret = createChannel((CreateChannel) opp);
			} else if (opp instanceof Bind) {
				ret = bind((Bind) opp);
			} else if (opp instanceof Set) {
				ret = set((Set) opp);
			} else {
				ret = "// TODO " + opp;
			}

			return ret;
		}).collect(Collectors.toList()), "\n");

	}

	private String set(Set opp) {
		return "set " + opp.getInstance() + "." + opp.getKey() + "= '" + opp.getValue() + "'";
	}

	private String bind(Bind opp) {
		return "bind " + opp.getNodeName() + "." + opp.getComponentName() + "." + opp.getPort() + " "
				+ opp.getChanName();
	}

	private String createChannel(CreateChannel chan) {
		return "add " + chan.getName() + ": " + chan.getTypeDef().getName();
	}

	private String createComponent(CreateComponent component) {
		return "add " + component.getNodeName() + "." + component.getName() + ": " + component.getTypeDef().getName();
	}

	private String setFragment(SetFragment set) {
		return "set " + set.getGroupName() + "." + set.getKey() + "/" + set.getNodeName() + " = '" + set.getValue()
				+ "'";
	}

	private String createNode(CreateNode node) {
		return "add " + node.getName() + ": " + node.getTypeDef().getName();
	}

	private String createGroup(CreateGroup opp) {
		return "add " + opp.getName() + ": " + opp.getTypeDef().getName();
	}

	private String attach(Attach opp) {

		return "attach " + opp.getNodeName() + " " + opp.getGroupName();
	}
}
