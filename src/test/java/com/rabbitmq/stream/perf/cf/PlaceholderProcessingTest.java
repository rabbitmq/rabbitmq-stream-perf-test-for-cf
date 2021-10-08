/*
 * RabbitMQ Stream Perf Test for CF
 *  Copyright 2021 VMware, Inc.
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

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class PlaceholderProcessingTest {

    @Test
    public void variableExtraction() {
        assertEquals(0, StreamPerfTestCF.extractVariables("").size());
        assertEquals(0, StreamPerfTestCF.extractVariables("test").size());
        assertEquals(0, StreamPerfTestCF.extractVariables("10").size());
        assertEquals("[PORT]", StreamPerfTestCF.extractVariables("${PORT}").toString());
        assertEquals(
                "[APP_ID, INSTANCE_ID]",
                StreamPerfTestCF.extractVariables("application=${APP_ID},instance=${APP_ID}-${INSTANCE_ID}").toString()
        );
    }

    @Test
    public void evaluate() {
        UnaryOperator<String> lookup = lookup("PORT", "8080", "APP_ID", "perf-test", "INSTANCE_ID", "123");
        assertEquals(
                "", StreamPerfTestCF.evaluate("", lookup)
        );
        assertEquals(
                "test", StreamPerfTestCF.evaluate("test", lookup)
        );
        assertEquals(
                "10", StreamPerfTestCF.evaluate("10", lookup)
        );
        assertEquals(
                "8080", StreamPerfTestCF.evaluate("${PORT}", lookup)
        );
        assertEquals(
                "application=perf-test,instance=perf-test-123",
                StreamPerfTestCF.evaluate("application=${APP_ID},instance=${APP_ID}-${INSTANCE_ID}", lookup)
        );
    }

    UnaryOperator<String> lookup(String... keyValues) {
        Map<String, String> values = new HashMap<>();
        for (int i = 0; i < keyValues.length - 1; i++) {
            values.put(keyValues[i], keyValues[i + 1]);
        }
        return key -> values.get(key);
    }
}