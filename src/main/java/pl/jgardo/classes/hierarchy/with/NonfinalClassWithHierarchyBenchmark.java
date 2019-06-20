package pl.jgardo.classes.hierarchy.with;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class NonfinalClassWithHierarchyBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(NonfinalClassWithHierarchyBenchmark.class.getSimpleName())
                .forks(1)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(2)
                .measurementIterations(10)
                .measurementTime(TimeValue.seconds(1))
                .threads(1)
                .mode(Mode.Throughput)
                .build();

        new Runner(opt).run();
    }

    private static final Superclass SUPERCLASS = new Superclass();
    private static final FinalClass FINAL_CLASS = new FinalClass();
    private static final NonFinalClass NON_FINAL_CLASS = new NonFinalClass();

    private static Superclass STATIC_SUPERCLASS = SUPERCLASS;
    private static Superclass STATIC_FINAL_CLASS_AS_SUPERCLASS = FINAL_CLASS;
    private static Superclass STATIC_NON_FINAL_CLASS_AS_SUPERCLASS = NON_FINAL_CLASS;
    private static FinalClass STATIC_FINAL_CLASS = FINAL_CLASS;
    private static NonFinalClass STATIC_NON_FINAL_CLASS = NON_FINAL_CLASS;

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int Superclass() {
        return STATIC_SUPERCLASS.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int FinalAsSuperclass() {
        return STATIC_FINAL_CLASS_AS_SUPERCLASS.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int NonFinalAsSuperclass() {
        return STATIC_NON_FINAL_CLASS_AS_SUPERCLASS.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int Final() {
        return STATIC_FINAL_CLASS.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int NonFinal() {
        return STATIC_NON_FINAL_CLASS.someMethodInvocation();
    }
}
