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
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.jgardo.InvocableBean;
import pl.jgardo.spring.constructor.ConstructorConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// not working - nondeterministic results
@Deprecated
public class SpringInvocationBenchmark {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(SpringInvocationBenchmark.class.getSimpleName())
                .forks(1)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(3)
                .measurementIterations(5)
                .measurementTime(TimeValue.seconds(3))
                .mode(Mode.Throughput)
                .shouldDoGC(true)
//                .addProfiler("stack", "lines=5;top=3;detailLine=true;excludePackages=true;period=1")
                .build();

        new Runner(opt).run();
    }

    private final static int[] INDEXES_TO_LAUNCH = {1,10,20,30,34,64,73,78,52,44} ;

    @State(Scope.Benchmark)
    public static class FieldContext {
        private ApplicationContext applicationContext;
        private List<InvocableBean> beans;

        @Setup
        public void setup() {
            applicationContext = new AnnotationConfigApplicationContext(ConstructorConfig.class);
            Map<String, InvocableBean> beansOfType = applicationContext.getBeansOfType(InvocableBean.class);
            beans = beansOfType.keySet().stream()
                    .sorted()
                    .map(beansOfType::get)
                    .collect(Collectors.toList());
            beans = Arrays.stream(INDEXES_TO_LAUNCH)
                    .mapToObj(beans::get)
                    .collect(Collectors.toList());
//            beans.stream()
//                    .map(InvocableBean::getClass)
//                    .map(Class::getSimpleName)
//                    .forEachOrdered(System.out::println);
        }

        @TearDown
        public void tearDown() {
            applicationContext = null;
            beans = null;
        }
    }

    @State(Scope.Benchmark)
    public static class SetterContext {
        private ApplicationContext applicationContext;
        private List<InvocableBean> beans;

        @Setup
        public void setup() {
            applicationContext = new AnnotationConfigApplicationContext(ConstructorConfig.class);
            Map<String, InvocableBean> beansOfType = applicationContext.getBeansOfType(InvocableBean.class);
            beans = beansOfType.keySet().stream()
                    .sorted()
                    .map(beansOfType::get)
                    .collect(Collectors.toList());
            beans = Arrays.stream(INDEXES_TO_LAUNCH)
                    .mapToObj(beans::get)
                    .collect(Collectors.toList());
        }

        @TearDown
        public void tearDown() {
            applicationContext = null;
            beans = null;
        }
    }

    @State(Scope.Benchmark)
    public static class ConstructorContext {
        private ApplicationContext applicationContext;
        private List<InvocableBean> beans;

        @Setup
        public void setup() {
            applicationContext = new AnnotationConfigApplicationContext(ConstructorConfig.class);
            Map<String, InvocableBean> beansOfType = applicationContext.getBeansOfType(InvocableBean.class);
            beans = beansOfType.keySet().stream()
                    .sorted()
                    .map(beansOfType::get)
                    .collect(Collectors.toList());
            beans = Arrays.stream(INDEXES_TO_LAUNCH)
                    .mapToObj(beans::get)
                    .collect(Collectors.toList());
        }

        @TearDown
        public void tearDown() {
            applicationContext = null;
            beans = null;
        }
    }

    @Benchmark
//    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public String field(FieldContext context) {
        return execute(context.beans);
    }

    @Benchmark
//    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public String setter(SetterContext context) {
        return execute(context.beans);
    }

    @Benchmark
//    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public String constructor(ConstructorContext context) {
        return execute(context.beans);
    }

    private String execute(List<InvocableBean> beans) {
        for (InvocableBean invocableBean : beans) {
            invocableBean.invoke();
        }
        return "";
    }

}
