package fr.mleduc.poc.graph.generator.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.mleduc.poc.graph.generator.graph.typedef.ComponentTypeDef;
import fr.mleduc.poc.graph.generator.graph.typedef.GroupTypeDef;
import fr.mleduc.poc.graph.generator.graph.typedef.TypeDef;

/**
 * Created by mleduc on 24/06/16.
 */
public class TypeDefService {

	private final List<ComponentTypeDef> components = new ArrayList<>();
	private final List<TypeDef> nodes = new ArrayList<>();
	private final List<TypeDef> chans = new ArrayList<>();
	private final List<GroupTypeDef> groups = new ArrayList<>();

	private final Random generator;

	public TypeDefService(final Random generator) {
		this.generator = generator;

		initGroups();
		initComponents();
		initNodes();
		initChans();
	}

	private void initGroups() {
		final Map<String, String> wsGroupDefaultDictionary = new HashMap<>();
		wsGroupDefaultDictionary.put("master", "node0");
		wsGroupDefaultDictionary.put("onConnect", "");
		wsGroupDefaultDictionary.put("onDisconnect", "");
		final Map<String, String> wsGroupDefaultFragmentDictionary = new HashMap<>();
		wsGroupDefaultFragmentDictionary.put("port", "9000");
		groups.add(new GroupTypeDef("WSGroup", wsGroupDefaultDictionary, wsGroupDefaultFragmentDictionary));

	}

	private void initComponents() {
		final List<String> outputsTicker = new ArrayList<>();
		outputsTicker.add("tick");
		final Map<String, String> tickerDefaultDictionary = new HashMap<>();
		tickerDefaultDictionary.put("period", "3000");
		tickerDefaultDictionary.put("random", "false");
		components.add(new ComponentTypeDef("Ticker", tickerDefaultDictionary, null, outputsTicker));
		final List<String> inputsConsole = new ArrayList<>();
		inputsConsole.add("input");
		components.add(new ComponentTypeDef("ConsolePrinter", new HashMap<>(), inputsConsole, null));
	}

	private void initNodes() {
		final HashMap<String, String> javaNodeDefaultDictionary = new HashMap<>();
		javaNodeDefaultDictionary.put("jvmArgs", "");
		javaNodeDefaultDictionary.put("log", "INFO");
		nodes.add(new TypeDef("JavaNode", javaNodeDefaultDictionary));
		final Map<String, String> javascriptNodeDefaultDictionary = new HashMap<>();
		javascriptNodeDefaultDictionary.put("logLevel", "INFO");
		nodes.add(new TypeDef("JavascriptNode", javascriptNodeDefaultDictionary));
	}

	private void initChans() {
		final Map<String, String> wsChanDefaultDictionary = new HashMap<>();
		wsChanDefaultDictionary.put("host", "ws.kevoree.org");
		wsChanDefaultDictionary.put("port", "80");
		wsChanDefaultDictionary.put("path", "benchmark");
		chans.add(new TypeDef("WSChan", wsChanDefaultDictionary));
	}

	public ComponentTypeDef getRandomComponent() {
		return getRandomOnList(this.components);
	}

	public TypeDef getRandomNode() {
		return getRandomOnList(this.nodes);
	}

	private <T extends TypeDef> T getRandomOnList(final List<T> components1) {
		return components1.get(generator.nextInt(components1.size()));
	}

	public TypeDef getRandomChan() {
		return getRandomOnList(this.chans);
	}

	public GroupTypeDef getRandomGroup() {
		return getRandomOnList(this.groups);
	}
}
