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

package pl.jgardo.spring;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.jgardo.spring.constructor.ConstructorConfig;
import pl.jgardo.spring.empty.EmptyConfig;
import pl.jgardo.spring.field.FieldConfig;
import pl.jgardo.spring.setters.SetterConfig;

public class SpringInitializationBenchmark {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(SpringInitializationBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 0, time = 1)
    @Measurement(time = 1, iterations = 1)
    @BenchmarkMode(Mode.SingleShotTime)
    public String setUpSpringContextWithEmptyContext() {
        String[] args = new String[0];
        ApplicationContext ctx = new AnnotationConfigApplicationContext(EmptyConfig.class);
        return "";
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 0, time = 1)
    @Measurement(time = 1, iterations = 1)
    @BenchmarkMode(Mode.SingleShotTime)
    public String setUpSpringContextWithFields() {
        String[] args = new String[0];
        ApplicationContext ctx = new AnnotationConfigApplicationContext(FieldConfig.class);
        return "";
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 0, time = 1)
    @Measurement(time = 1, iterations = 1)
    @BenchmarkMode(Mode.SingleShotTime)
    public String setUpSpringContextWithSetters() {
        String[] args = new String[0];
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SetterConfig.class);
        return "";
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 0, time = 1)
    @Measurement(time = 1, iterations = 1)
    @BenchmarkMode(Mode.SingleShotTime)
    public String setUpSpringContextWithConstructor() {
        String[] args = new String[0];
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConstructorConfig.class);
        return "";
    }

//    DLA 800 i 600 krawędzi

//    Benchmark                                           Mode  Cnt  Score   Error  Units
//    SpringInitializationBenchmark.constructor     ss       3,373           s/op
//    SpringInitializationBenchmark.setUpSpringContextWithEmptyContext    ss       0,749           s/op
//    SpringInitializationBenchmark.field          ss       2,834           s/op
//    SpringInitializationBenchmark.setter         ss       3,242           s/op

//    DLA 800 i 2000 krawędzi

//    Benchmark                                           Mode  Cnt  Score   Error  Units
//    SpringInitializationBenchmark.constructor     ss       3,732           s/op
//    SpringInitializationBenchmark.setUpSpringContextWithEmptyContext    ss       0,729           s/op
//    SpringInitializationBenchmark.field          ss       3,144           s/op
//    SpringInitializationBenchmark.setter         ss       4,865           s/op

//    DLA 800 i 10000 krawędzi

//    Benchmark                                           Mode  Cnt  Score   Error  Units
//    SpringInitializationBenchmark.constructor     ss       4,246           s/op
//    SpringInitializationBenchmark.setUpSpringContextWithEmptyContext    ss       0,711           s/op
//    SpringInitializationBenchmark.field          ss       3,441           s/op
//    SpringInitializationBenchmark.setter         ss       5,712           s/op
}
