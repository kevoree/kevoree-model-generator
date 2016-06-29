package fr.mleduc.poc.graph.generator.service.output;

import java.util.List;

import fr.mleduc.poc.graph.generator.operations.Attach;
import fr.mleduc.poc.graph.generator.operations.Bind;
import fr.mleduc.poc.graph.generator.operations.CreateChannel;
import fr.mleduc.poc.graph.generator.operations.CreateComponent;
import fr.mleduc.poc.graph.generator.operations.CreateGroup;
import fr.mleduc.poc.graph.generator.operations.CreateNode;
import fr.mleduc.poc.graph.generator.operations.IOperation;
import fr.mleduc.poc.graph.generator.operations.RemoveComponent;
import fr.mleduc.poc.graph.generator.operations.Set;
import fr.mleduc.poc.graph.generator.operations.SetFragment;

public interface IOperationExecutor<T> {

	T process(List<IOperation> operations);

	T set(Set opp);

	T bind(Bind opp);

	T createChannel(CreateChannel chan);

	T createComponent(CreateComponent component);

	T setFragment(SetFragment set);

	T createNode(CreateNode node);

	T createGroup(CreateGroup group);

	T attach(Attach attach);
	
	T removeComponent(RemoveComponent component);

}