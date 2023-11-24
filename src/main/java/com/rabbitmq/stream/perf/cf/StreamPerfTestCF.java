/*
 * RabbitMQ Stream Perf Test for CF
 *  Copyright (c) 2021-2023 Broadcom. All Rights Reserved. The term "Broadcom" refers to Broadcom Inc. and/or its subsidiaries.
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  This product is licensed to you under the Apache 2.0 license (the "License").
 *  You may not use this product except in compliance with the Apache 2.0 License.
 *
 *  This product may include a number of subcomponents with separate copyright notices
 *  and license terms. Your use of these subcomponents is subject to the terms and
 *  conditions of the subcomponent's license, as noted in the LICENSE file.
 */

package com.rabbitmq.stream.perf.cf;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.rabbitmq.stream.perf.StreamPerfTest;

/**
 *
 */
public class StreamPerfTestCF {

    public static void main(String[] args) throws Exception {
        String uris = uris(System.getenv("VCAP_SERVICES"));
        if (uris == null) {
            throw new IllegalArgumentException("Unable to retrieve broker URI(s) from VCAP_SERVICES");
        }
        if (args == null) {
            args = new String[0];
        }
        args = Arrays.copyOf(args, args.length + 2);
        args[args.length - 2] = "--uris";
        args[args.length - 1] = uris;

        //ToDo: convert ENV_VARS to command-line args

        StreamPerfTest streamPerfTest = new StreamPerfTest(args, System.out, System.err, null);
        streamPerfTest.call();
    }

    static String uris(String vcapServices) {
        if (vcapServices == null || vcapServices.trim().isEmpty()) {
            return null;
        }
        Gson gson = new Gson();
        JsonObject servicesType = (JsonObject) gson.fromJson(vcapServices, JsonElement.class);
        String uris = null;
        for (String serviceType : servicesType.keySet()) {
            JsonArray services = servicesType.getAsJsonArray(serviceType);
            uris = extractUrisFromServices(services, service -> service.get("tags").getAsJsonArray().contains(new JsonPrimitive("amqp")));
            if (uris != null) {
                break;
            }
            uris = extractUrisFromServices(services, service -> true);
            if (uris != null) {
                break;
            }
        }
        return uris;
    }

    static String extractUrisFromServices(JsonArray services, Predicate<JsonObject> shouldInspectService) {
        for (JsonElement service : services) {
            if (shouldInspectService.test(service.getAsJsonObject())) {
                JsonObject credentials = services.get(0).getAsJsonObject().get("credentials").getAsJsonObject();
                if (credentials.get("uris") == null && credentials.get("urls") == null) {
                    break;
                }
                JsonArray uris = credentials.get("uris") == null ? credentials.get("urls").getAsJsonArray() : credentials.get("uris").getAsJsonArray();
                Stream.Builder<String> builder = Stream.builder();
                uris.forEach(uri -> builder.accept(uri.getAsString()));
                return String.join(",", builder.build().collect(Collectors.toList()));
            }
        }
        return null;
    }

    static String evaluate(String input, UnaryOperator<String> lookup) {
        Set<String> variables = extractVariables(input);
        if (!variables.isEmpty()) {
            for (String variable : variables) {
                input = input.replace("${" + variable + "}", lookup.apply(variable));
            }
        }
        return input;
    }

    static Set<String> extractVariables(String input) {
        Matcher m = Pattern.compile("\\$\\{(\\w*?)\\}").matcher(input);
        Set<String> variables = new LinkedHashSet<>();
        while (m.find()) {
            variables.add(m.group(1));
        }
        return variables;
    }
}
