/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package pl.jgardo.field.enums;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class EnumFieldBenchmark {


    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(EnumFieldBenchmark.class.getSimpleName())
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

    enum FinalSum {
        ONE_PLUS_ONE(1,1),
        TWO_PLUS_TWO(2,2),
        ;
        private final int numberOne;
        private final int numberTwo;

        FinalSum(int numberOne, int numberTwo) {
            this.numberOne = numberOne;
            this.numberTwo = numberTwo;
        }

        public int getSum() {
            return numberOne + numberTwo;
        }
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int enumWithFinal() {
        return FinalSum.ONE_PLUS_ONE.getSum() + FinalSum.TWO_PLUS_TWO.getSum();
    }

    enum NonFinalSum {
        ONE_PLUS_ONE(1,1),
        TWO_PLUS_TWO(2,2),
        ;
        private int numberOne;
        private int numberTwo;

        NonFinalSum(int numberOne, int numberTwo) {
            this.numberOne = numberOne;
            this.numberTwo = numberTwo;
        }

        public int getSum() {
            return numberOne + numberTwo;
        }
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public int enumWithoutFinal() {
        return NonFinalSum.ONE_PLUS_ONE.getSum() + NonFinalSum.TWO_PLUS_TWO.getSum();
    }
}
