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

package pl.jgardo.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;

public class SmallJacksonBenchmark {

    private static final String SOME_JSON =
            "{\"a\":17654323," +
            "\"b\":\"SmallJacksonBenchmark\"," +
            "\"array\":[true,false,true,false]}";

    static class NonfinalJson {
        private int a;
        private String b;
        private boolean[] array;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public boolean[] getArray() {
            return array;
        }

        public void setArray(boolean[] array) {
            this.array = array;
        }
    }


    static class FinalJson {
        private final int a;
        private final String b;
        private final boolean[] array;

        @JsonCreator
        public FinalJson(@JsonProperty("a") int a,
                         @JsonProperty("b") String b,
                         @JsonProperty("array") boolean[] array) {
            this.a = a;
            this.b = b;
            this.array = array;
        }


    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(SmallJacksonBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public void deserializeNonFinal() throws IOException {
        objectMapper.readValue(SOME_JSON, NonfinalJson.class);
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public void deserializeFinal() throws IOException {
        objectMapper.readValue(SOME_JSON, FinalJson.class);
    }



}
