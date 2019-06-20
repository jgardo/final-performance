package pl.jgardo.field.statics;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import pl.jgardo.classes.hierarchy.with.FinalClass;
import pl.jgardo.classes.hierarchy.with.Superclass;

public class FinalClassWithHierarchyBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(FinalClassWithHierarchyBenchmark.class.getSimpleName())
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

    private static final FinalClass S_F_SUB_CONCRETE = new FinalClass();
    private static final Superclass S_F_SUB_POLIMORPHIC = new FinalClass();
    private static final Superclass S_F_SUPER_CONCRETE = new Superclass();

    private static FinalClass S_NF_SUB_CONCRETE = new FinalClass();
    private static Superclass S_NF_SUB_POLIMORPHIC = new FinalClass();
    private static Superclass S_NF_SUPER_CONCRETE = new Superclass();

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int S_F_SUB_CONCRETE() {
        return S_F_SUB_CONCRETE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int S_F_SUB_POLIMORPHIC() {
        return S_F_SUB_POLIMORPHIC.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int S_F_SUPER_CONCRETE() {
        return S_F_SUPER_CONCRETE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int S_NF_SUB_CONCRETE() {
        return S_NF_SUB_CONCRETE.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int S_NF_SUB_POLIMORPHIC() {
        return S_NF_SUB_POLIMORPHIC.someMethodInvocation();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int S_NF_SUPER_CONCRETE() {
        return S_NF_SUPER_CONCRETE.someMethodInvocation();
    }
}
