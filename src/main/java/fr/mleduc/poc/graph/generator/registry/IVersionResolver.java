package fr.mleduc.poc.graph.generator.registry;

import java.util.List;

import org.kevoree.TypeDefinition;

public interface IVersionResolver {

	TypeDefinition findBestVersion(String typeDefName, String expectedVersion, List<TypeDefinition> availableTypeDef);
}
