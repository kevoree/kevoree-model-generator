package fr.mleduc.poc.graph.generator.service.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.Dictionary;
import org.kevoree.Value;
import org.kevoree.TypeDefinition;
import org.kevoree.factory.DefaultKevoreeFactory;
import org.kevoree.factory.KevoreeFactory;
import org.kevoree.pmodeling.api.json.JSONModelSerializer;

import fr.mleduc.poc.graph.generator.graph.Graph;
import fr.mleduc.poc.graph.generator.registry.KevoreeRegistryResolver;
import fr.mleduc.poc.graph.generator.registry.TypeDefinitionResolver;
import fr.mleduc.poc.graph.generator.registry.TypeFQN;

/**
 * Created by mleduc on 27/06/16.
 */
public class KevoreeModelService {
    
    private final KevoreeRegistryResolver kevoreeRegistryResolver = new KevoreeRegistryResolver();
    
    public String process(final Graph graph) {
        final KevoreeFactory kevoreeFactory = new DefaultKevoreeFactory();
        final ContainerRoot root = kevoreeFactory.createContainerRoot();
        kevoreeFactory.root(root);

        final List<ContainerNode> nodes = graph.getNodes().stream().map(node -> {
            final ContainerNode containerNode = kevoreeFactory.createContainerNode();

            
            try {
                final List<TypeFQN> fqns = new ArrayList<>();
                fqns.add(new TypeFQN(node.getTypeDef().getName()));
                kevoreeRegistryResolver.resolve(fqns, root, kevoreeFactory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            final TypeDefinition nodeTypeDefinition = TypeDefinitionResolver.resolve(root, null, node.getTypeDef().getName(), null);
            containerNode.setName(node.getName());
            nodeTypeDefinition.setName(node.getTypeDef().getName());
            containerNode.setStarted(true);
            containerNode.setTypeDefinition(nodeTypeDefinition);
            final Dictionary dictionary = kevoreeFactory.createDictionary();
            
            dictionary.setValues(node.getDictionary().entrySet().stream().map(new Function<Entry<String, String>, Value>() {

                @Override
                public Value apply(Entry<String, String> entry) {
                    final Value value = kevoreeFactory.createValue();
                    value.setName(entry.getKey());
                    value.setValue(entry.getValue());
                    return value;
                }
            }).collect(Collectors.toList()));
            containerNode.setDictionary(dictionary);
            
            return containerNode;
        }).collect(Collectors.toList());
        
        root.addAllNodes(nodes);

        final JSONModelSerializer jsonSerializer = kevoreeFactory.createJSONSerializer();
        return jsonSerializer.serialize(root);
    }
}
