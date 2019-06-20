package pl.jgardo;

import com.github.javafaker.Faker;
import com.squareup.javapoet.*;
import org.jgrapht.Graph;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.util.SupplierUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ObjectsGenerator {

    public static final String FIELD_PACKAGES = "pl.jgardo.spring.field.service";
    public static final String SETTER_PACKAGES = "pl.jgardo.spring.setters.service";
    public static final String CONSTRUCTOR_PACKAGES = "pl.jgardo.spring.constructor.service";
    public static final int VERTICES = 800;
    public static final int EDGES = 10000;
    private static Random RANDOM = new Random();
    private static Set<String> fieldNamesSet = new HashSet<>(1000);
    private static MultiValueMap<String, String> methodsNames = new LinkedMultiValueMap<>();
    private static MultiValueMap<String, MethodSpec> methodsForClasses = new LinkedMultiValueMap<>();
    public static void main(String[] args) {
        DirectedAcyclicGraph<String, DefaultEdge> dag = generateDAG();

        generateMethodNames(dag);
        for (String className : dag.vertexSet()) {
            String[] fields = dag.outgoingEdgesOf(className).stream()
                    .map(dag::getEdgeTarget)
                    .toArray(String[]::new);
            for (String methodName : methodsNames.get(className)) {
                CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
                int statementsCount = RANDOM.nextInt(3);

                StringBuilder returnStatement = new StringBuilder("return " + RANDOM.nextInt(100000));
                for (int i = 0; i < statementsCount; i++) {
                    if (fields.length > 0) {
                        String fieldName = fields[RANDOM.nextInt(fields.length)];
                        List<String> methodNamesForField = methodsNames.getOrDefault(fieldName, Collections.emptyList());
                        if (methodNamesForField.size() > 0) {
                            String invokedMethodName = methodNamesForField.get(RANDOM.nextInt(methodNamesForField.size()));
                            codeBlockBuilder.add("int int"+i+" = " + decapitalize(fieldName) + "." + invokedMethodName + "();\n");
                            returnStatement.append(" + int").append(i);
                        }
                    }
                }
                codeBlockBuilder.add(returnStatement.append(";\n").toString());

                MethodSpec methodSpec = MethodSpec.methodBuilder(methodName)
                        .addCode(codeBlockBuilder.build())
                        .returns(int.class)
                        .addModifiers(Modifier.PUBLIC)
                        .build();

                methodsForClasses.add(className, methodSpec);
            }

            CodeBlock.Builder invokeBlock = CodeBlock.builder();
            invokeBlock.add("return 0");
            methodsNames.get(className).forEach(method -> {
                invokeBlock.add(" + " +method+ "()\n");
            });
            invokeBlock.add(";");
            MethodSpec methodSpec = MethodSpec.methodBuilder("invoke")
                    .addCode(invokeBlock.build())
                    .returns(int.class)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .build();

            methodsForClasses.add(className, methodSpec);
        }


        cleanOldClasses();

        createFieldInjections(dag);
        createSetterInjections(dag);
        createConstructorInjections(dag);
    }

    private static String decapitalize(String fieldName) {
        return fieldName.substring(0,1).toLowerCase() + fieldName.substring(1);
    }

    private static void generateMethodNames(DirectedAcyclicGraph<String, DefaultEdge> dag) {
        String[] allMethodNames = fieldNamesSet.toArray(new String[0]);
        dag.vertexSet().forEach(className -> {
            final int methodsCount = RANDOM.nextInt(4) + 1;
            Set<String> names = new HashSet<>();
            for (int i = 0; i < methodsCount; i++) {
                String methodName = allMethodNames[RANDOM.nextInt(allMethodNames.length)];
                methodName = "do" +methodName;
                names.add(methodName);
            }
            methodsNames.addAll(className, new ArrayList<>(names));
        });
    }

    private static void cleanOldClasses() {
        try {
            Files.walk(Paths.get("./src/main/java/pl/jgardo/spring/field/service"))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.walk(Paths.get("./src/main/java/pl/jgardo/spring/setters/service"))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.walk(Paths.get("./src/main/java/pl/jgardo/spring/constructor/service"))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFieldInjections(DirectedAcyclicGraph<String, DefaultEdge> dag) {
        for (String vertex : dag.vertexSet()) {
            List<FieldSpec> fields = new ArrayList<>();
            List<MethodSpec> methods = new ArrayList<>();
            for (DefaultEdge edge : dag.outgoingEdgesOf(vertex)) {
                String className = dag.getEdgeTarget(edge);
                String fieldName = className.substring(0,1).toLowerCase() + className.substring(1);
                FieldSpec field = FieldSpec.builder(ClassName.get(FIELD_PACKAGES, className), fieldName)
                        .addModifiers(Modifier.PRIVATE)
                        .addAnnotation(Autowired.class)
                        .build();

                fields.add(field);
            }

            TypeSpec clazz = TypeSpec.classBuilder(vertex)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Service.class)
                    .addFields(fields)
                    .addSuperinterface(InvocableBean.class)
                    .addMethods(methods)
                    .addMethods(methodsForClasses.getOrDefault(vertex, Collections.emptyList()))
                    .build();

            JavaFile javaFile = JavaFile.builder(FIELD_PACKAGES, clazz)
                    .build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createSetterInjections(DirectedAcyclicGraph<String, DefaultEdge> dag) {
        for (String vertex : dag.vertexSet()) {
            List<FieldSpec> fields = new ArrayList<>();
            List<MethodSpec> methods = new ArrayList<>();
            for (DefaultEdge edge : dag.outgoingEdgesOf(vertex)) {
                String className = dag.getEdgeTarget(edge);
                String fieldName = className.substring(0,1).toLowerCase() + className.substring(1);
                FieldSpec field = FieldSpec.builder(ClassName.get(SETTER_PACKAGES, className), fieldName)
                        .addModifiers(Modifier.PRIVATE)
                        .build();

                fields.add(field);

                MethodSpec setter = MethodSpec.methodBuilder("set" + className)
                        .addParameter(ClassName.get(SETTER_PACKAGES, className), fieldName)
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Autowired.class)
                        .addCode(CodeBlock.builder()
                                .addStatement("this." + fieldName + " = " + fieldName + ";\n")
                                .build())
                        .build();

                methods.add(setter);
            }

            TypeSpec clazz = TypeSpec.classBuilder(vertex)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Service.class)
                    .addFields(fields)
                    .addSuperinterface(InvocableBean.class)
                    .addMethods(methods)
                    .addMethods(methodsForClasses.getOrDefault(vertex, Collections.emptyList()))
                    .build();

            JavaFile javaFile = JavaFile.builder(SETTER_PACKAGES, clazz)
                    .build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createConstructorInjections(DirectedAcyclicGraph<String, DefaultEdge> dag) {
        for (String vertex : dag.vertexSet()) {
            List<FieldSpec> fields = new ArrayList<>();
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Autowired.class);

            for (DefaultEdge edge : dag.outgoingEdgesOf(vertex)) {
                String className = dag.getEdgeTarget(edge);
                String fieldName = className.substring(0,1).toLowerCase() + className.substring(1);
                FieldSpec field = FieldSpec.builder(ClassName.get(CONSTRUCTOR_PACKAGES, className), fieldName)
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                        .build();

                fields.add(field);

                constructorBuilder
                        .addParameter(ClassName.get(CONSTRUCTOR_PACKAGES, className), fieldName)
                        .addCode("this." + fieldName + " = " + fieldName + ";\n");
            }

            TypeSpec clazz = TypeSpec.classBuilder(vertex)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Service.class)
                    .addFields(fields)
                    .addSuperinterface(InvocableBean.class)
                    .addMethod(constructorBuilder.build())
                    .addMethods(methodsForClasses.getOrDefault(vertex, Collections.emptyList()))
                    .build();

            JavaFile javaFile = JavaFile.builder(CONSTRUCTOR_PACKAGES, clazz)
                    .build();
            try {
                javaFile.writeTo(Paths.get("./src/main/java"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static DirectedAcyclicGraph<String, DefaultEdge> generateDAG() {
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            String randomName = faker.address().cityName()
                    .replaceAll(" ", "")
                    .replaceAll("'","")
                    .concat("Service");
            if (SourceVersion.isIdentifier(randomName)) {
                fieldNamesSet.add(randomName);
            }
        }

        List<String> fieldNames =  new ArrayList<>(fieldNamesSet);

        AtomicInteger index = new AtomicInteger(0);
        Supplier<String> supplier = () -> fieldNames.get(index.getAndIncrement());

        DirectedAcyclicGraph<String, DefaultEdge> dag = new DirectedAcyclicGraph<>(
                supplier, SupplierUtil.DEFAULT_EDGE_SUPPLIER, false);
        RepeatableRandomGraphGenerator<String, DefaultEdge> graphGen =
                new RepeatableRandomGraphGenerator<>(VERTICES, EDGES, 9, 100);
        graphGen.generateGraph(dag);
        return dag;
    }

    public static class RepeatableRandomGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
        private Random randomizer;
        private int numOfVertexes;
        private int numOfEdges;
        private int maxEdgeThreshold;

        public RepeatableRandomGraphGenerator(int vertices, int edges, long seed, int maxEdgeThreshold)
        {
            this.numOfVertexes = vertices;
            this.numOfEdges = edges;
            this.randomizer = new Random(seed);
            this.maxEdgeThreshold = maxEdgeThreshold;
        }

        @Override
        public void generateGraph(Graph<V, E> graph, Map<String, V> namedVerticesMap)
        {
            List<V> vertices = new ArrayList<>(numOfVertexes);
            Set<Integer> edgeGeneratorIds = new HashSet<>();

            for (int i = 0; i < numOfVertexes; i++) {
                vertices.add(graph.addVertex());
            }

            for (int i = 0; i < numOfEdges; i++) {
                Integer edgeGeneratorId;
                int fromVertexId;
                int toVertexId;
                int outDegree;
                do {
                    edgeGeneratorId = randomizer.nextInt(numOfVertexes * (numOfVertexes - 1));
                    fromVertexId = edgeGeneratorId / numOfVertexes;
                    toVertexId = edgeGeneratorId % (numOfVertexes - 1);

                    V vertex = vertices.get(fromVertexId);
                    Set<E> outgouingEdges = graph.outgoingEdgesOf(vertex);

                    outDegree = outgouingEdges.size();
                } while (edgeGeneratorIds.contains(edgeGeneratorId) || outDegree > maxEdgeThreshold);

                try {
                    graph.addEdge(vertices.get(fromVertexId), vertices.get(toVertexId));
                    edgeGeneratorIds.add(edgeGeneratorId);
                } catch (IllegalArgumentException e) {
                    // okay, that's fine; omit cycle
                }
            }
        }
    }
}
