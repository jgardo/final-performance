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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.List;

public class BiggerJacksonBenchmark {

//    Benchmark                                               Mode  Cnt      Score      Error  Units
//    BiggerJacksonBenchmark.deserializeFinal                thrpt   10  27214,995 ± 1215,234  ops/s
//    BiggerJacksonBenchmark.deserializeNonFinal             thrpt   10  24306,791 ± 1378,712  ops/s
//    BiggerJacksonBenchmark.deserializeNonFinalConstructor  thrpt   10  26878,968 ± 1172,339  ops/s

//    Benchmark                                               Mode  Cnt      Score      Error  Units
//    BiggerJacksonBenchmark.deserializeFinal                thrpt   10  27167,707 ± 1663,209  ops/s
//    BiggerJacksonBenchmark.deserializeNonFinal             thrpt   10  24398,581 ± 1344,902  ops/s
//    BiggerJacksonBenchmark.deserializeNonFinalConstructor  thrpt   10  27658,662 ± 1383,121  ops/s

//    Benchmark                                               Mode  Cnt      Score      Error  Units
//    BiggerJacksonBenchmark.deserializeFinal                thrpt   10  27252,955 ± 1515,591  ops/s
//    BiggerJacksonBenchmark.deserializeNonFinal             thrpt   10  24676,079 ± 1217,475  ops/s
//    BiggerJacksonBenchmark.deserializeNonFinalConstructor  thrpt   10  27273,651 ± 1540,966  ops/s

    private static final String SOME_JSON =
            "[{\"_id\":\"5c48ce270bbff3f65b7627e3\",\"index\":0,\"guid\":\"203af0c5-5c3d-42cd-8aa3-cec7f2b9312c\",\"isActive\":true,\"balance\":\"$1,115.69\",\"picture\":\"http://placehold.it/32x32\",\"age\":29,\"eyeColor\":\"blue\",\"name\":\"Mclean Warren\",\"gender\":\"male\",\"company\":\"JASPER\",\"email\":\"mcleanwarren@jasper.com\",\"phone\":\"+1 (993) 450-2059\",\"address\":\"253 Belmont Avenue, Wikieup, Mississippi, 8186\",\"about\":\"Excepteur reprehenderit est sit id in elit excepteur sint tempor. Nulla nostrud est non quis excepteur proident occaecat ex pariatur mollit nostrud fugiat. Reprehenderit elit nisi cupidatat deserunt in cillum voluptate laboris velit tempor nisi duis ut commodo. Nulla eiusmod aliqua incididunt non aute amet mollit ad tempor veniam reprehenderit minim incididunt. Amet minim consequat ea aute id mollit enim incididunt cillum est voluptate dolore.\\r\\n\",\"registered\":\"2017-10-21T10:18:25 -02:00\",\"latitude\":-22.50599,\"longitude\":-37.160108,\"tags\":[\"aute\",\"velit\",\"culpa\",\"amet\",\"veniam\",\"non\",\"proident\"],\"friends\":[{\"id\":0,\"name\":\"Cameron Acosta\"},{\"id\":1,\"name\":\"Lessie Mueller\"},{\"id\":2,\"name\":\"Doreen Thomas\"}],\"greeting\":\"Hello, Mclean Warren! You have 7 unread messages.\",\"favoriteFruit\":\"banana\"},{\"_id\":\"5c48ce2763bfae1d4e6107c2\",\"index\":1,\"guid\":\"40ba23d3-aed3-4fc0-8535-6e71c5b5bc7c\",\"isActive\":true,\"balance\":\"$2,817.43\",\"picture\":\"http://placehold.it/32x32\",\"age\":26,\"eyeColor\":\"green\",\"name\":\"Higgins Mcneil\",\"gender\":\"male\",\"company\":\"NEXGENE\",\"email\":\"higginsmcneil@nexgene.com\",\"phone\":\"+1 (905) 536-3502\",\"address\":\"244 Rockaway Parkway, Taft, Washington, 6865\",\"about\":\"Nulla amet laboris aliquip deserunt anim ad irure aliquip reprehenderit nulla. Ad eiusmod qui magna cillum cillum do sint. Amet amet dolore anim id eu consectetur occaecat ullamco consectetur labore. Mollit amet amet est velit est cupidatat ea anim officia. Nisi Lorem tempor et nisi mollit ea anim consectetur tempor.\\r\\n\",\"registered\":\"2018-05-28T07:08:50 -02:00\",\"latitude\":41.972115,\"longitude\":65.065707,\"tags\":[\"deserunt\",\"cupidatat\",\"amet\",\"culpa\",\"veniam\",\"mollit\",\"ex\"],\"friends\":[{\"id\":0,\"name\":\"Charity Hayden\"},{\"id\":1,\"name\":\"Bernadette Conley\"},{\"id\":2,\"name\":\"Wilcox Klein\"}],\"greeting\":\"Hello, Higgins Mcneil! You have 4 unread messages.\",\"favoriteFruit\":\"apple\"},{\"_id\":\"5c48ce279f55cbed28ed54ae\",\"index\":2,\"guid\":\"5d9eb99c-4ce4-4d5e-8b22-82330d0e483e\",\"isActive\":true,\"balance\":\"$2,140.47\",\"picture\":\"http://placehold.it/32x32\",\"age\":30,\"eyeColor\":\"green\",\"name\":\"Webb Glover\",\"gender\":\"male\",\"company\":\"VIRVA\",\"email\":\"webbglover@virva.com\",\"phone\":\"+1 (928) 503-2810\",\"address\":\"417 Seba Avenue, Dunlo, Montana, 7409\",\"about\":\"Exercitation ipsum enim mollit labore consequat. Deserunt veniam pariatur est cupidatat commodo. Nisi amet adipisicing ut aliqua ipsum enim occaecat reprehenderit aliquip ipsum aliqua do labore esse. Officia dolor ipsum ullamco tempor eiusmod ut fugiat magna. Dolore aliquip officia sint veniam eu minim adipisicing. Et cupidatat Lorem enim exercitation Lorem pariatur fugiat ex irure sint reprehenderit irure.\\r\\n\",\"registered\":\"2016-08-19T10:03:52 -02:00\",\"latitude\":45.646611,\"longitude\":-136.491968,\"tags\":[\"Lorem\",\"anim\",\"fugiat\",\"amet\",\"culpa\",\"Lorem\",\"id\"],\"friends\":[{\"id\":0,\"name\":\"Luz Barron\"},{\"id\":1,\"name\":\"Wolf Strickland\"},{\"id\":2,\"name\":\"Kristy Park\"}],\"greeting\":\"Hello, Webb Glover! You have 6 unread messages.\",\"favoriteFruit\":\"apple\"},{\"_id\":\"5c48ce27407df953031c8c03\",\"index\":3,\"guid\":\"6d70c9ed-b11a-4bdd-aa7e-3ff43be54962\",\"isActive\":false,\"balance\":\"$2,467.88\",\"picture\":\"http://placehold.it/32x32\",\"age\":22,\"eyeColor\":\"blue\",\"name\":\"Harrington Hahn\",\"gender\":\"male\",\"company\":\"MEDIOT\",\"email\":\"harringtonhahn@mediot.com\",\"phone\":\"+1 (899) 596-2002\",\"address\":\"348 Tompkins Place, Lookingglass, American Samoa, 6310\",\"about\":\"Proident velit commodo cupidatat quis ipsum cupidatat pariatur veniam sit. Ea Lorem dolore commodo reprehenderit excepteur duis occaecat. Irure sit dolor non eu aliquip non aliqua proident eiusmod ex proident consectetur tempor nostrud.\\r\\n\",\"registered\":\"2018-05-08T03:01:53 -02:00\",\"latitude\":-42.693531,\"longitude\":-148.231252,\"tags\":[\"laborum\",\"ad\",\"officia\",\"labore\",\"dolor\",\"est\",\"commodo\"],\"friends\":[{\"id\":0,\"name\":\"Mayo Blackburn\"},{\"id\":1,\"name\":\"Merle Hatfield\"},{\"id\":2,\"name\":\"Sherman Battle\"}],\"greeting\":\"Hello, Harrington Hahn! You have 10 unread messages.\",\"favoriteFruit\":\"apple\"},{\"_id\":\"5c48ce2774d058e94821d45e\",\"index\":4,\"guid\":\"68ac2c88-637a-4ccc-b207-ddbb809054bd\",\"isActive\":true,\"balance\":\"$1,569.40\",\"picture\":\"http://placehold.it/32x32\",\"age\":27,\"eyeColor\":\"brown\",\"name\":\"Corinne Garrett\",\"gender\":\"female\",\"company\":\"CANOPOLY\",\"email\":\"corinnegarrett@canopoly.com\",\"phone\":\"+1 (958) 543-3106\",\"address\":\"751 Hillel Place, Soham, California, 9077\",\"about\":\"Aliquip voluptate voluptate culpa ad velit magna velit. Nulla nulla enim laboris non dolore ut consequat exercitation officia. Dolore sunt magna magna commodo ipsum ullamco et laboris eiusmod nostrud dolor consequat ad exercitation.\\r\\n\",\"registered\":\"2015-01-30T08:13:07 -01:00\",\"latitude\":-84.780355,\"longitude\":-145.911346,\"tags\":[\"do\",\"et\",\"voluptate\",\"esse\",\"adipisicing\",\"elit\",\"irure\"],\"friends\":[{\"id\":0,\"name\":\"Tina Lott\"},{\"id\":1,\"name\":\"Ernestine Reed\"},{\"id\":2,\"name\":\"Espinoza Baxter\"}],\"greeting\":\"Hello, Corinne Garrett! You have 1 unread messages.\",\"favoriteFruit\":\"banana\"},{\"_id\":\"5c48ce27083a72ba1e21ce46\",\"index\":5,\"guid\":\"10dd8b88-102c-4586-a5b4-19395d2d90b0\",\"isActive\":false,\"balance\":\"$1,455.58\",\"picture\":\"http://placehold.it/32x32\",\"age\":30,\"eyeColor\":\"brown\",\"name\":\"Robert Rowe\",\"gender\":\"female\",\"company\":\"THREDZ\",\"email\":\"robertrowe@thredz.com\",\"phone\":\"+1 (815) 540-3383\",\"address\":\"543 Troy Avenue, Dalton, Oregon, 7769\",\"about\":\"Id dolore duis quis ullamco velit cupidatat nostrud ut ea elit excepteur ad exercitation mollit. Consectetur officia nostrud tempor mollit deserunt. Eu exercitation Lorem adipisicing fugiat ex consequat consequat amet eiusmod sint in eiusmod pariatur. Ad officia nulla quis aliquip consequat reprehenderit. Ullamco pariatur laboris et et labore laboris eiusmod commodo eu adipisicing qui minim nisi. Deserunt nisi commodo ullamco Lorem consectetur. Irure deserunt do pariatur consectetur enim.\\r\\n\",\"registered\":\"2016-09-18T07:00:16 -02:00\",\"latitude\":-71.839913,\"longitude\":-157.435841,\"tags\":[\"est\",\"commodo\",\"voluptate\",\"pariatur\",\"aute\",\"dolore\",\"enim\"],\"friends\":[{\"id\":0,\"name\":\"Allyson Gordon\"},{\"id\":1,\"name\":\"Parrish Flynn\"},{\"id\":2,\"name\":\"Buckner Holt\"}],\"greeting\":\"Hello, Robert Rowe! You have 7 unread messages.\",\"favoriteFruit\":\"apple\"}]";

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(BiggerJacksonBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final static TypeReference<List<ExampleNonFinal>> nonfinalTypeReference = new TypeReference<List<ExampleNonFinal>>() {};
    private final static TypeReference<List<ExampleFinal>> finalTypeReference = new TypeReference<List<ExampleFinal>>() {};
    private final static TypeReference<List<ExampleNonFinalConstructor>> nonFinalConstructorTypeReference = new TypeReference<List<ExampleNonFinalConstructor>>() {};

    private final static TypeReference<List<FinalExampleNonFinal>> finalNonfinalTypeReference = new TypeReference<List<FinalExampleNonFinal>>() {};
    private final static TypeReference<List<FinalExampleFinal>> finalFinalTypeReference = new TypeReference<List<FinalExampleFinal>>() {};
    private final static TypeReference<List<FinalExampleNonFinalConstructor>> finalNonFinalConstructorTypeReference = new TypeReference<List<FinalExampleNonFinalConstructor>>() {};

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public List<ExampleNonFinal> nonFinalClassNonFinal() throws IOException {
        return objectMapper.readValue(SOME_JSON, nonfinalTypeReference);
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public List<ExampleFinal> nonFinalClassFinal() throws IOException {
        return objectMapper.readValue(SOME_JSON, finalTypeReference);
    }


    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public List<ExampleNonFinalConstructor> nonFinalClassNonFinalConstructor() throws IOException {
        return objectMapper.readValue(SOME_JSON, nonFinalConstructorTypeReference);
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public List<ExampleNonFinal> finalClassNonFinal() throws IOException {
        return objectMapper.readValue(SOME_JSON, finalNonfinalTypeReference);
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public List<ExampleFinal> finalClassFinal() throws IOException {
        return objectMapper.readValue(SOME_JSON, finalFinalTypeReference);
    }


    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 2, time = 1)
    @Measurement(time = 1, iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public List<FinalExampleNonFinalConstructor> finalClassNonFinalConstructor() throws IOException {
        return objectMapper.readValue(SOME_JSON, finalNonFinalConstructorTypeReference);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name"
    })
    public static class FriendNonFinal {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "_id",
            "index",
            "guid",
            "isActive",
            "balance",
            "picture",
            "age",
            "eyeColor",
            "name",
            "gender",
            "company",
            "email",
            "phone",
            "address",
            "about",
            "registered",
            "latitude",
            "longitude",
            "tags",
            "friends",
            "greeting",
            "favoriteFruit"
    })
    public static class ExampleNonFinal {
        @JsonProperty("_id")
        private String id;
        private Integer index;
        private String guid;
        private Boolean isActive;
        private String balance;
        private String picture;
        private Integer age;
        private String eyeColor;
        private String name;
        private String gender;
        private String company;
        private String email;
        private String phone;
        private String address;
        private String about;
        private String registered;
        private Double latitude;
        private Double longitude;
        private List<String> tags = null;
        private List<FriendNonFinal> friends = null;
        private String greeting;
        private String favoriteFruit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getEyeColor() {
            return eyeColor;
        }

        public void setEyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getRegistered() {
            return registered;
        }

        public void setRegistered(String registered) {
            this.registered = registered;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<FriendNonFinal> getFriends() {
            return friends;
        }

        public void setFriends(List<FriendNonFinal> friends) {
            this.friends = friends;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }

        public String getFavoriteFruit() {
            return favoriteFruit;
        }

        public void setFavoriteFruit(String favoriteFruit) {
            this.favoriteFruit = favoriteFruit;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name"
    })
    public static class FriendFinal {
        private final Integer id;
        private final String name;

        @JsonCreator
        public FriendFinal(
                @JsonProperty("id") Integer id,
                @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "_id",
            "index",
            "guid",
            "isActive",
            "balance",
            "picture",
            "age",
            "eyeColor",
            "name",
            "gender",
            "company",
            "email",
            "phone",
            "address",
            "about",
            "registered",
            "latitude",
            "longitude",
            "tags",
            "friends",
            "greeting",
            "favoriteFruit"
    })
    public static class ExampleFinal {
        private final String id;
        private final Integer index;
        private final String guid;
        private final Boolean isActive;
        private final String balance;
        private final String picture;
        private final Integer age;
        private final String eyeColor;
        private final String name;
        private final String gender;
        private final String company;
        private final String email;
        private final String phone;
        private final String address;
        private final String about;
        private final String registered;
        private final Double latitude;
        private final Double longitude;
        private final List<String> tags;
        private final List<FriendFinal> friends;
        private final String greeting;
        private final String favoriteFruit;

        @JsonCreator
        public ExampleFinal(
                @JsonProperty("_id") String id,
                @JsonProperty("index") Integer index,
                @JsonProperty("guid") String guid,
                @JsonProperty("isActive") Boolean isActive,
                @JsonProperty("balance") String balance,
                @JsonProperty("picture") String picture,
                @JsonProperty("age") Integer age,
                @JsonProperty("eyeColor") String eyeColor,
                @JsonProperty("name") String name,
                @JsonProperty("gender") String gender,
                @JsonProperty("company") String company,
                @JsonProperty("email") String email,
                @JsonProperty("phone") String phone,
                @JsonProperty("address") String address,
                @JsonProperty("about") String about,
                @JsonProperty("registered") String registered,
                @JsonProperty("latitude") Double latitude,
                @JsonProperty("longitude") Double longitude,
                @JsonProperty("tags") List<String> tags,
                @JsonProperty("friends") List<FriendFinal> friends,
                @JsonProperty("greeting") String greeting,
                @JsonProperty("favoriteFruit") String favoriteFruit) {
            this.id = id;
            this.index = index;
            this.guid = guid;
            this.isActive = isActive;
            this.balance = balance;
            this.picture = picture;
            this.age = age;
            this.eyeColor = eyeColor;
            this.name = name;
            this.gender = gender;
            this.company = company;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.about = about;
            this.registered = registered;
            this.latitude = latitude;
            this.longitude = longitude;
            this.tags = tags;
            this.friends = friends;
            this.greeting = greeting;
            this.favoriteFruit = favoriteFruit;
        }

        public String getId() {
            return id;
        }

        public Integer getIndex() {
            return index;
        }

        public String getGuid() {
            return guid;
        }

        public Boolean getActive() {
            return isActive;
        }

        public String getBalance() {
            return balance;
        }

        public String getPicture() {
            return picture;
        }

        public Integer getAge() {
            return age;
        }

        public String getEyeColor() {
            return eyeColor;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getCompany() {
            return company;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getAbout() {
            return about;
        }

        public String getRegistered() {
            return registered;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<FriendFinal> getFriends() {
            return friends;
        }

        public String getGreeting() {
            return greeting;
        }

        public String getFavoriteFruit() {
            return favoriteFruit;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name"
    })
    public static class FriendNonFinalConstructor {
        private Integer id;
        private String name;

        @JsonCreator
        public FriendNonFinalConstructor(
                @JsonProperty("id") Integer id,
                @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "_id",
            "index",
            "guid",
            "isActive",
            "balance",
            "picture",
            "age",
            "eyeColor",
            "name",
            "gender",
            "company",
            "email",
            "phone",
            "address",
            "about",
            "registered",
            "latitude",
            "longitude",
            "tags",
            "friends",
            "greeting",
            "favoriteFruit"
    })
    public static class ExampleNonFinalConstructor {
        private String id;
        private Integer index;
        private String guid;
        private Boolean isActive;
        private String balance;
        private String picture;
        private Integer age;
        private String eyeColor;
        private String name;
        private String gender;
        private String company;
        private String email;
        private String phone;
        private String address;
        private String about;
        private String registered;
        private Double latitude;
        private Double longitude;
        private List<String> tags;
        private List<FinalFriendFinal> friends;
        private String greeting;
        private String favoriteFruit;

        @JsonCreator
        public ExampleNonFinalConstructor(
                @JsonProperty("_id") String id,
                @JsonProperty("index") Integer index,
                @JsonProperty("guid") String guid,
                @JsonProperty("isActive") Boolean isActive,
                @JsonProperty("balance") String balance,
                @JsonProperty("picture") String picture,
                @JsonProperty("age") Integer age,
                @JsonProperty("eyeColor") String eyeColor,
                @JsonProperty("name") String name,
                @JsonProperty("gender") String gender,
                @JsonProperty("company") String company,
                @JsonProperty("email") String email,
                @JsonProperty("phone") String phone,
                @JsonProperty("address") String address,
                @JsonProperty("about") String about,
                @JsonProperty("registered") String registered,
                @JsonProperty("latitude") Double latitude,
                @JsonProperty("longitude") Double longitude,
                @JsonProperty("tags") List<String> tags,
                @JsonProperty("friends") List<FinalFriendFinal> friends,
                @JsonProperty("greeting") String greeting,
                @JsonProperty("favoriteFruit") String favoriteFruit) {
            this.id = id;
            this.index = index;
            this.guid = guid;
            this.isActive = isActive;
            this.balance = balance;
            this.picture = picture;
            this.age = age;
            this.eyeColor = eyeColor;
            this.name = name;
            this.gender = gender;
            this.company = company;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.about = about;
            this.registered = registered;
            this.latitude = latitude;
            this.longitude = longitude;
            this.tags = tags;
            this.friends = friends;
            this.greeting = greeting;
            this.favoriteFruit = favoriteFruit;
        }

        public String getId() {
            return id;
        }

        public Integer getIndex() {
            return index;
        }

        public String getGuid() {
            return guid;
        }

        public Boolean getActive() {
            return isActive;
        }

        public String getBalance() {
            return balance;
        }

        public String getPicture() {
            return picture;
        }

        public Integer getAge() {
            return age;
        }

        public String getEyeColor() {
            return eyeColor;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getCompany() {
            return company;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getAbout() {
            return about;
        }

        public String getRegistered() {
            return registered;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<FinalFriendFinal> getFriends() {
            return friends;
        }

        public String getGreeting() {
            return greeting;
        }

        public String getFavoriteFruit() {
            return favoriteFruit;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "_id",
            "index",
            "guid",
            "isActive",
            "balance",
            "picture",
            "age",
            "eyeColor",
            "name",
            "gender",
            "company",
            "email",
            "phone",
            "address",
            "about",
            "registered",
            "latitude",
            "longitude",
            "tags",
            "friends",
            "greeting",
            "favoriteFruit"
    })
    public final static class FinalExampleNonFinal {
        @JsonProperty("_id")
        private String id;
        private Integer index;
        private String guid;
        private Boolean isActive;
        private String balance;
        private String picture;
        private Integer age;
        private String eyeColor;
        private String name;
        private String gender;
        private String company;
        private String email;
        private String phone;
        private String address;
        private String about;
        private String registered;
        private Double latitude;
        private Double longitude;
        private List<String> tags = null;
        private List<FriendNonFinal> friends = null;
        private String greeting;
        private String favoriteFruit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getEyeColor() {
            return eyeColor;
        }

        public void setEyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getRegistered() {
            return registered;
        }

        public void setRegistered(String registered) {
            this.registered = registered;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<FriendNonFinal> getFriends() {
            return friends;
        }

        public void setFriends(List<FriendNonFinal> friends) {
            this.friends = friends;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }

        public String getFavoriteFruit() {
            return favoriteFruit;
        }

        public void setFavoriteFruit(String favoriteFruit) {
            this.favoriteFruit = favoriteFruit;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name"
    })
    public final static class FinalFriendFinal {
        private final Integer id;
        private final String name;

        @JsonCreator
        public FinalFriendFinal(
                @JsonProperty("id") Integer id,
                @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "_id",
            "index",
            "guid",
            "isActive",
            "balance",
            "picture",
            "age",
            "eyeColor",
            "name",
            "gender",
            "company",
            "email",
            "phone",
            "address",
            "about",
            "registered",
            "latitude",
            "longitude",
            "tags",
            "friends",
            "greeting",
            "favoriteFruit"
    })
    public final static class FinalExampleFinal {
        private final String id;
        private final Integer index;
        private final String guid;
        private final Boolean isActive;
        private final String balance;
        private final String picture;
        private final Integer age;
        private final String eyeColor;
        private final String name;
        private final String gender;
        private final String company;
        private final String email;
        private final String phone;
        private final String address;
        private final String about;
        private final String registered;
        private final Double latitude;
        private final Double longitude;
        private final List<String> tags;
        private final List<FinalFriendFinal> friends;
        private final String greeting;
        private final String favoriteFruit;

        @JsonCreator
        public FinalExampleFinal(
                @JsonProperty("_id") String id,
                @JsonProperty("index") Integer index,
                @JsonProperty("guid") String guid,
                @JsonProperty("isActive") Boolean isActive,
                @JsonProperty("balance") String balance,
                @JsonProperty("picture") String picture,
                @JsonProperty("age") Integer age,
                @JsonProperty("eyeColor") String eyeColor,
                @JsonProperty("name") String name,
                @JsonProperty("gender") String gender,
                @JsonProperty("company") String company,
                @JsonProperty("email") String email,
                @JsonProperty("phone") String phone,
                @JsonProperty("address") String address,
                @JsonProperty("about") String about,
                @JsonProperty("registered") String registered,
                @JsonProperty("latitude") Double latitude,
                @JsonProperty("longitude") Double longitude,
                @JsonProperty("tags") List<String> tags,
                @JsonProperty("friends") List<FinalFriendFinal> friends,
                @JsonProperty("greeting") String greeting,
                @JsonProperty("favoriteFruit") String favoriteFruit) {
            this.id = id;
            this.index = index;
            this.guid = guid;
            this.isActive = isActive;
            this.balance = balance;
            this.picture = picture;
            this.age = age;
            this.eyeColor = eyeColor;
            this.name = name;
            this.gender = gender;
            this.company = company;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.about = about;
            this.registered = registered;
            this.latitude = latitude;
            this.longitude = longitude;
            this.tags = tags;
            this.friends = friends;
            this.greeting = greeting;
            this.favoriteFruit = favoriteFruit;
        }

        public String getId() {
            return id;
        }

        public Integer getIndex() {
            return index;
        }

        public String getGuid() {
            return guid;
        }

        public Boolean getActive() {
            return isActive;
        }

        public String getBalance() {
            return balance;
        }

        public String getPicture() {
            return picture;
        }

        public Integer getAge() {
            return age;
        }

        public String getEyeColor() {
            return eyeColor;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getCompany() {
            return company;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getAbout() {
            return about;
        }

        public String getRegistered() {
            return registered;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<FinalFriendFinal> getFriends() {
            return friends;
        }

        public String getGreeting() {
            return greeting;
        }

        public String getFavoriteFruit() {
            return favoriteFruit;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name"
    })
    public final static class FinalFriendNonFinalConstructor {
        private Integer id;
        private String name;

        @JsonCreator
        public FinalFriendNonFinalConstructor(
                @JsonProperty("id") Integer id,
                @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "_id",
            "index",
            "guid",
            "isActive",
            "balance",
            "picture",
            "age",
            "eyeColor",
            "name",
            "gender",
            "company",
            "email",
            "phone",
            "address",
            "about",
            "registered",
            "latitude",
            "longitude",
            "tags",
            "friends",
            "greeting",
            "favoriteFruit"
    })
    public static final class FinalExampleNonFinalConstructor {
        private String id;
        private Integer index;
        private String guid;
        private Boolean isActive;
        private String balance;
        private String picture;
        private Integer age;
        private String eyeColor;
        private String name;
        private String gender;
        private String company;
        private String email;
        private String phone;
        private String address;
        private String about;
        private String registered;
        private Double latitude;
        private Double longitude;
        private List<String> tags;
        private List<FinalFriendNonFinalConstructor> friends;
        private String greeting;
        private String favoriteFruit;

        @JsonCreator
        public FinalExampleNonFinalConstructor(
                @JsonProperty("_id") String id,
                @JsonProperty("index") Integer index,
                @JsonProperty("guid") String guid,
                @JsonProperty("isActive") Boolean isActive,
                @JsonProperty("balance") String balance,
                @JsonProperty("picture") String picture,
                @JsonProperty("age") Integer age,
                @JsonProperty("eyeColor") String eyeColor,
                @JsonProperty("name") String name,
                @JsonProperty("gender") String gender,
                @JsonProperty("company") String company,
                @JsonProperty("email") String email,
                @JsonProperty("phone") String phone,
                @JsonProperty("address") String address,
                @JsonProperty("about") String about,
                @JsonProperty("registered") String registered,
                @JsonProperty("latitude") Double latitude,
                @JsonProperty("longitude") Double longitude,
                @JsonProperty("tags") List<String> tags,
                @JsonProperty("friends") List<FinalFriendNonFinalConstructor> friends,
                @JsonProperty("greeting") String greeting,
                @JsonProperty("favoriteFruit") String favoriteFruit) {
            this.id = id;
            this.index = index;
            this.guid = guid;
            this.isActive = isActive;
            this.balance = balance;
            this.picture = picture;
            this.age = age;
            this.eyeColor = eyeColor;
            this.name = name;
            this.gender = gender;
            this.company = company;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.about = about;
            this.registered = registered;
            this.latitude = latitude;
            this.longitude = longitude;
            this.tags = tags;
            this.friends = friends;
            this.greeting = greeting;
            this.favoriteFruit = favoriteFruit;
        }

        public String getId() {
            return id;
        }

        public Integer getIndex() {
            return index;
        }

        public String getGuid() {
            return guid;
        }

        public Boolean getActive() {
            return isActive;
        }

        public String getBalance() {
            return balance;
        }

        public String getPicture() {
            return picture;
        }

        public Integer getAge() {
            return age;
        }

        public String getEyeColor() {
            return eyeColor;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getCompany() {
            return company;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getAbout() {
            return about;
        }

        public String getRegistered() {
            return registered;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<FinalFriendNonFinalConstructor> getFriends() {
            return friends;
        }

        public String getGreeting() {
            return greeting;
        }

        public String getFavoriteFruit() {
            return favoriteFruit;
        }
    }

}
