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
public class KevPrinterService implements IOperationExecutor<String> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#process(
	 * java.util.List)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#set(fr.
	 * mleduc.poc.graph.generator.operations.Set)
	 */
	@Override
	public String set(Set opp) {
		return "set " + opp.getInstance() + "." + opp.getKey() + "= '" + opp.getValue() + "'";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#bind(fr.
	 * mleduc.poc.graph.generator.operations.Bind)
	 */
	@Override
	public String bind(Bind opp) {
		return "bind " + opp.getNodeName() + "." + opp.getComponentName() + "." + opp.getPort() + " "
				+ opp.getChanName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#
	 * createChannel(fr.mleduc.poc.graph.generator.operations.CreateChannel)
	 */
	@Override
	public String createChannel(CreateChannel chan) {
		return "add " + chan.getName() + ": " + chan.getTypeDef().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#
	 * createComponent(fr.mleduc.poc.graph.generator.operations.CreateComponent)
	 */
	@Override
	public String createComponent(CreateComponent component) {
		return "add " + component.getNodeName() + "." + component.getName() + ": " + component.getTypeDef().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#
	 * setFragment(fr.mleduc.poc.graph.generator.operations.SetFragment)
	 */
	@Override
	public String setFragment(SetFragment set) {
		return "set " + set.getGroupName() + "." + set.getKey() + "/" + set.getNodeName() + " = '" + set.getValue()
				+ "'";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#
	 * createNode(fr.mleduc.poc.graph.generator.operations.CreateNode)
	 */
	@Override
	public String createNode(CreateNode node) {
		return "add " + node.getName() + ": " + node.getTypeDef().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#
	 * createGroup(fr.mleduc.poc.graph.generator.operations.CreateGroup)
	 */
	@Override
	public String createGroup(CreateGroup opp) {
		return "add " + opp.getName() + ": " + opp.getTypeDef().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mleduc.poc.graph.generator.service.output.IOperationExecutor#attach(fr
	 * .mleduc.poc.graph.generator.operations.Attach)
	 */
	@Override
	public String attach(Attach opp) {

		return "attach " + opp.getNodeName() + " " + opp.getGroupName();
	}
}
