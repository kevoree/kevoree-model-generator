package fr.mleduc.poc.graph.generator.registry;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.kevoree.ContainerRoot;
import org.kevoree.TypeDefinition;
import org.kevoree.pmodeling.api.KMFContainer;
import org.kevoree.pmodeling.api.util.ModelVisitor;

import jet.runtime.typeinfo.JetValueParameter;

/**
 * Created with IntelliJ IDEA. User: duke Date: 25/11/2013 Time: 16:04
 */
public class TypeDefinitionResolver {

    private static final IVersionResolver resolver = new SemverVersionResolver();

    public static TypeDefinition resolve(final ContainerRoot model, final String[] packages, final String typeDefName,
            final String version) {

        org.kevoree.Package pack = null;
        if (packages != null) {
            for (int i = 0; i < packages.length - 1; i++) {
                if (pack == null) {
                    pack = model.findPackagesByID(packages[i]);
                } else {
                    pack = pack.findPackagesByID(packages[i]);
                }
            }
        }
        final ArrayList<TypeDefinition> selected = new ArrayList<TypeDefinition>();
        if (pack == null) {
            for (final org.kevoree.Package loopPack : model.getPackages()) {
                loopPack.deepVisitContained(new ModelVisitor() {
                    @Override
                    public void visit(@JetValueParameter(name = "elem") @NotNull final KMFContainer kmfContainer,
                            @JetValueParameter(name = "refNameInParent") @NotNull final String s,
                            @JetValueParameter(name = "parent") @NotNull final KMFContainer kmfContainer2) {
                        if (kmfContainer instanceof TypeDefinition) {
                            final TypeDefinition casted = (TypeDefinition) kmfContainer;
                            String name = casted.getName();
                            if (name.contains(".")) {
                                name = name.substring(name.lastIndexOf(".") + 1);
                            }
                            if (name.equals(typeDefName)) {
                                selected.add((TypeDefinition) kmfContainer);
                            }
                        }
                    }
                });
            }
        } else {
            for (final TypeDefinition td : pack.getTypeDefinitions()) {
                if (td.getName().equals(packages[packages.length - 1])) {
                    selected.add(td);
                }
            }
        }
        final TypeDefinition bestTD = resolver.findBestVersion(typeDefName, version, selected);

        // Still not found :( try again
        return bestTD;
    }
}
