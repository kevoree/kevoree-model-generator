package fr.mleduc.poc.graph.generator.graph.settings;

/**
 * Created by mleduc on 27/06/16.
 */
public class ChannelSettings {
    private final String host;
    private final String path;
    private final int port;

    public ChannelSettings(String host, String path, int port) {
        this.host = host;
        this.path = path;
        this.port = port;
    }

    public ChannelSettings() {
        this.host = "ws.kevoree.org";
        this.path = "benchmark";
        this.port = 80;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }
}
