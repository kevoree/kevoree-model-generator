package fr.mleduc.poc.graph.generator.graph;

import fr.mleduc.poc.graph.generator.graph.settings.ChannelSettings;

/**
 * Created by mleduc on 27/06/16.
 * <p>
 * Note : currently the only TD supported is WSChan and channels settings is a WSChannelSettings.
 */
public class Chan {
    private final String name;
    private final ChannelSettings settings;

    public Chan(String name, ChannelSettings settings) {
        this.name = name;
        this.settings = settings;
    }

    public String getName() {
        return name;
    }

    public ChannelSettings getSettings() {
        return settings;
    }
}
