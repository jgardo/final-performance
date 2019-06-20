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

package pl.jgardo.methods;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class MethodsWithHierarchyBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(MethodsWithHierarchyBenchmark.class.getSimpleName())
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
    private static Superclass SUPER = new Superclass();
    private static Superclass POLIMORFIC = new SubClass();
    private static SubClass SUB = new SubClass();

    @Benchmark
    public int super_FinalSuper() {
        return SUPER.finalSuper();
    }

    @Benchmark
    public int polimorfic_FinalSuper() {
        return POLIMORFIC.finalSuper();
    }

    @Benchmark
    public int sub_FinalSuper() {
        return SUB.finalSuper();
    }

    @Benchmark
    public int super_NonFinalSuper() {
        return SUPER.nonFinal();
    }

    @Benchmark
    public int polimorfic_NonFinalSuper() {
        return POLIMORFIC.nonFinal();
    }

    @Benchmark
    public int sub_NonFinalSuper() {
        return SUB.nonFinal();
    }

    @Benchmark
    public int super_InheritedToMarkAsFinal() {
        return SUPER.inheritedToMarkAsFinal();
    }

    @Benchmark
    public int polimorfic_InheritedToMarkAsFinal() {
        return POLIMORFIC.inheritedToMarkAsFinal();
    }

    @Benchmark
    public int sub_InheritedToMarkAsFinal() {
        return SUB.inheritedToMarkAsFinal();
    }

    @Benchmark
    public int sub_FinalInSub() {
        return SUB.finalInSub();
    }

    @Benchmark
    public int super_NotOverrided() {
        return SUPER.notOverrided();
    }

    @Benchmark
    public int sub_NotOverrided() {
        return SUB.notOverrided();
    }

    @Benchmark
    public int polimorfic_NotOverrided() {
        return POLIMORFIC.notOverrided();
    }

}
