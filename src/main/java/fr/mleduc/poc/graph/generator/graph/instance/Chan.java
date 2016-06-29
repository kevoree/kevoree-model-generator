package fr.mleduc.poc.graph.generator.graph.instance;

import fr.mleduc.poc.graph.generator.graph.typedef.TypeDef;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chan other = (Chan) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
