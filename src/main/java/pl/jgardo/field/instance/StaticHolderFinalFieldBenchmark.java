package pl.jgardo.field.instance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticHolderFinalFieldBenchmark {

    private static final Integer[] INTS = {1,2,3,4,5,6,7,8,9,0};

    private static final ObjectHolder objectHolderFinal = new ObjectHolder(INTS);
    private static ObjectHolder objectHolderNonFinal = new ObjectHolder(INTS);

    static class ObjectHolder {
        private final List<Integer> finalPointFinals;
        private List<Integer> nonFinalPointFinals;

        ObjectHolder(Integer[] points) {
            this.finalPointFinals = new ArrayList<>(Arrays.asList(points));
            this.nonFinalPointFinals = new ArrayList<>(Arrays.asList(points));
        }

        int getFromFinalList(int i) {
            return finalPointFinals.get(i);
        }

        int getFromNonFinalList(int i) {
            return nonFinalPointFinals.get(i);
        }
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(StaticHolderFinalFieldBenchmark.class.getSimpleName())
                .forks(1)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(3)
                .measurementIterations(5)
                .measurementTime(TimeValue.seconds(2))
                .threads(1)
                .mode(Mode.Throughput)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public double f_f() {
        return objectHolderFinal.getFromFinalList(0);
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public double f_n() {
        return  objectHolderFinal.getFromNonFinalList(0);
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public double n_f() {
        return objectHolderNonFinal.getFromFinalList(0);
    }
}
