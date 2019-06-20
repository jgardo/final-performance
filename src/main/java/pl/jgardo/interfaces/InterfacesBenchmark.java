package pl.jgardo.interfaces;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import pl.jgardo.classes.hierarchy.with.FinalClass;
import pl.jgardo.classes.hierarchy.with.NonFinalClass;
import pl.jgardo.classes.hierarchy.with.Superclass;

public class InterfacesBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(InterfacesBenchmark.class.getSimpleName())
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

    private static final SingleClass SINGLE_CLASS = new SingleClass();
    private static final DoubleClass1 DOUBLE_CLASS_1 = new DoubleClass1();
    private static final DoubleClass2 DOUBLE_CLASS_2 = new DoubleClass2();
    private static final DefaultClass DEFAULT_CLASS = new DefaultClass();

    private static SingleClass SINGLE_CLASS_CONCRETE = SINGLE_CLASS;
    private static SingleInterface SINGLE_CLASS_INTERFACE = SINGLE_CLASS;

    private static DoubleClass1 DOUBLE_CLASS_CONCRETE = DOUBLE_CLASS_1;
    private static DoubleInterface DOUBLE_CLASS_INTERFACE = DOUBLE_CLASS_1;

    private static DefaultClass DEFAULT_CLASS_CONCRETE = DEFAULT_CLASS;
    private static DefaultInterface DEFAULT_CLASS_INTERFACE = DEFAULT_CLASS;

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int singleConcrete() {
        return SINGLE_CLASS_CONCRETE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int singleInteface() {
        return SINGLE_CLASS_INTERFACE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int doubleConcrete() {
        return DOUBLE_CLASS_CONCRETE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int doubleInteface() {
        return DOUBLE_CLASS_INTERFACE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int defaultConcrete() {
        return DEFAULT_CLASS_CONCRETE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int defaultInteface() {
        return DEFAULT_CLASS_INTERFACE.someMethodInvocation();
    }
}
