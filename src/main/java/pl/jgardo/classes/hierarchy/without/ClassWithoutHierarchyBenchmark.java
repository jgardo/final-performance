package pl.jgardo.classes.hierarchy.without;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class ClassWithoutHierarchyBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ClassWithoutHierarchyBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private static final FinalClass FINAL_CLASS = new FinalClass();
    private static final NonFinalClass NON_FINAL_CLASS = new NonFinalClass();

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public int invokeMethodInNonFinalClass() {
        return NON_FINAL_CLASS.someMethodInvocation();
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public int invokeMethodInFinal() {
        return FINAL_CLASS.someMethodInvocation();
    }
}
