package pl.jgardo.variables;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class VariableBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(VariableBenchmark.class.getSimpleName())
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
    public String testFinal() {
        final String a = "a";
        final String b = "b";
        return a + b;
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public String testNonFinal() {
        String a = "a";
        String b = "b";
        return a + b;
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int countHashWithFinals() {
        final int a = countHashPrivate(2);
        final int b = countHashPrivate(4);
        final int n = 20;
        return a * b + n *b;
    }

    private int countHashPrivate(final int n) {
        final int a = 3;
        final int b = 2;
        return a * b + n *b;
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int countHashWithoutFinals() {
        int a = countHashPrivateWithoutFinals(2);
        int b = countHashPrivateWithoutFinals(4);
        int n = 20;
        return a * b + n *b;
    }

    private int countHashPrivateWithoutFinals(int n) {
        int a = 3;
        int b = 2;
        return a * b + n *b;
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int stupidLoopWithFinals() {
        boolean zZ = true;
        while (zZ) {
            final int xX = 1001;         // <------------- xX
            final int yY = 1002;         // <------------- yY
            zZ = (xX == yY);
        }
        return 1;
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int stupidLoopWithoutFinals() {
        boolean zZ = true;
        while (zZ) {
            int xX = 1001;         // <------------- xX
            int yY = 1002;         // <------------- yY
            zZ = (xX == yY);
        }
        return 1;
    }
}
