# Kevoree Model Generator
This project help creating massive model composed of hundreds of instances with just a few lines on configuration

## Usage
The generation can be deterministic by defining the seed used to initialize the random number.

## TODO

  * Alter a model

## Limitations
This is a first version of the tool.

It allows only the initialization of :
 * nodes :
   * JavaNode
   * JavascriptNode
 * groups : WSGroup (master = node0, port = 9000)
 * channels :
   * WSChan
 * components :
   * Ticker
   * ConsolePrinter


The version of the instance cannot be configured for now.

## Examples
### Generation of a small model in kevscript
```java
public class Application {

    public static void main(String[] args) {
        final GraphService graphService = new GraphService()
                .withNodes(5)
                .withComponents(3)
                .withChannels(2)
                .withGroups(1);

        final Graph graph = graphService.generate().getGraph();

        final String kevs = new KevPrinterService().process(graph);
        System.out.println(kevs);
    }
}
```