package pl.jgardo.field.bool;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Random;

@State(Scope.Benchmark)
public class BooleanFieldBenchmark {

    private final StateHolderNonFinal stateHolderNonFinal;
    private final StateHolderFinal stateHolderFinal;
    private static final StateHolderNonFinal stateHolderNonFinalStatic = new StateHolderNonFinal();
    static {stateHolderNonFinalStatic.setFeatureOn(true);}
    private static final StateHolderFinal stateHolderFinalStatic = new StateHolderFinal(true);
    public BooleanFieldBenchmark() {
        stateHolderNonFinal = new StateHolderNonFinal();
        stateHolderNonFinal.setFeatureOn(true);
        stateHolderFinal = new StateHolderFinal(true);
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(BooleanFieldBenchmark.class.getSimpleName())
                .forks(1)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(3)
                .measurementIterations(5)
                .measurementTime(TimeValue.seconds(3))
                .threads(1)
                .mode(Mode.Throughput)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int finalConst() {
        final StateHolderFinal stateHolderFinal = new StateHolderFinal(true);
        return stateHolderFinal.doSth();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int nonFinalConst() {
        final StateHolderNonFinal stateHolderNonFinal = new StateHolderNonFinal();
        stateHolderNonFinal.setFeatureOn(true);
        return stateHolderNonFinal.doSth();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int finalField() {
        return stateHolderFinal.doSth();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int nonFinalField() {
        return stateHolderNonFinal.doSth();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int finalStaticField() {
        return stateHolderFinalStatic.doSth();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int nonFinalStaticField() {
        return stateHolderNonFinalStatic.doSth();
    }
}
