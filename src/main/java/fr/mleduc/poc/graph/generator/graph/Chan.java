package fr.mleduc.poc.graph.generator.graph;

/**
 * Created by mleduc on 27/06/16.
 * <p>
 * Note : currently the only TD supported is WSChan and channels settings is a
 * WSChannelSettings.
 */
public class Chan extends Instance {
	private final String name;
	private TypeDef typeDef;

	public Chan(final String name, final TypeDef typeDef) {
		super(typeDef.getDefaultDictionary());
		this.name = name;
		this.setTypeDef(typeDef);
	}

	public String getName() {
		return name;
	}

	public TypeDef getTypeDef() {
		return typeDef;
	}

	public void setTypeDef(final TypeDef typeDef) {
		this.typeDef = typeDef;
	}

}
