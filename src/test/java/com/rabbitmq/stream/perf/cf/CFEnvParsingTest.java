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
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import static com.rabbitmq.stream.perf.cf.StreamPerfTestCF.argsFromEnv;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SystemStubsExtension.class)
public class CFEnvParsingTest {
//    final static String BATCH_SIZE = "90";
//    final static String CONFIRMS = "10101";
//    final static String CONSUMERS_BY_CONNECTION = "2";
//    final static String CODEC = "simple"; //can be qpic or simple
//    final static String TRACKING_CONSUMERS_BY_CONNECTION = "45";
//    final static String CONSUMER_NAMES = "uuid";
//    final static String COMPRESSION = "gzip";
//    final static String LEADER_LOCATOR = "random";
//    final static String MAX_AGE = "P5DT8H";
//    final static String MAX_LENGTH_BYTES = "10gb";
//    final static String MONITORING_PORT = "8081";
//    final static String OFFSET = "last";
//    final static String PRODUCERS_BY_CONNECTION = "2";
//    final static String PRODUCER_NAMES = "uuid";
//    final static String RATE = "100";
//    final static String SIZE = "12";
//    final static String STREAM_COUNT = "10";
//    final static String STORE_EVERY = "3";
//    final static String SUB_ENTRY_SIZE = "5";
//    final static String SERVER_NAME_INDICATION = "foo";
//    final static String STREAMS = "default";
//    final static String URIS = "should not be here";
//    final static String PRODUCERS = "4";
//    final static String CONSUMERS = "8";
//    final static String NOT_A_FLAG = "not a flag";
    // VERSION
    // SUMMARY_FILE
    // PROMETHEUS
    // MONITORING
    // METRICS_BYTE_RATES
    // LOAD_BALANCER
    // DELETE_STREAMS


    @Test
    public void extractBatchSize() {
        assertArrayEquals(new String[]{"--batch-size", "90","--confirms","10101"}, argsFromEnv());
    }
}
