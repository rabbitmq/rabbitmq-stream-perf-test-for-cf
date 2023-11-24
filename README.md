# RabbitMQ Stream Perf Test for Cloud Foundry

This is a template project to easily run [RabbitMQ Performance Tool](https://www.rabbitmq.com/java-tools.html#throughput-load-testing)
on [Cloud Foundry](https://www.cloudfoundry.org/). It has been tested against
[Broadcom Tanzu Application Service](https://tanzu.vmware.com/application-service).

## How to use

Pre-requisites: JDK 8 or higher and
[Cloud Foundry Command Line Interface](https://docs.cloudfoundry.org/cf-cli/) installed.

* Compile and package the application: `./mvnw clean package -Dmaven.test.skip`
* Configure the application with the `manifest.yml` file. StreamPerfTest is configured
with environment variables.
* Push the application to Cloud Foundry: `cf push`.

The Cloud Foundry application must be bound to a RabbitMQ service. It will automatically
get the connection information from the `VCAP_SERVICES` environment variable.

Consult [Stream Perf Test documentation](https://rabbitmq.github.io/rabbitmq-stream-java-client/stable/htmlsingle/#the-performance-tool)
to learn more about the available options.

You can use environment variables in the manifest to configure PerfTest.
The environment variable names use a snake-case version of the command line
argument long names, e.g. `--stream-max-segment-size-bytes` becomes `STREAM_MAX_SEGMENT_SIZE_BYTES`. Run the following
command to see all the available environment variables:

```
./mvnw -q compile exec:java -Dexec.args="-env"
```

## Java Stream Client version

The Java Stream Client version to use is set into the `pom.xml` file, e.g. `<stream-client.version>0.4.0</stream-client.version>`.
This can be changed before packaging and deploying on Cloud Foundry. Versions are typically latest stable,
milestones or release candidates, and snapshots. To know about the available versions:

 * [Stable versions](https://repo1.maven.org/maven2/com/rabbitmq/stream-client/)
 * [Milestones and release candidates](https://packagecloud.io/app/rabbitmq/maven-milestones/search?q=stream-client)
 * [Snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/rabbitmq/stream-client/)

## Community / Support

* [GitHub Issues](https://github.com/rabbitmq/rabbitmq-stream-perf-test-for-cf/issues)

## License ##

RabbitMQ PerfTest for Tanzu Application Service is [Apache 2.0 licensed](https://www.apache.org/licenses/LICENSE-2.0.html).

Copyright 2021-2023 Broadcom. All Rights Reserved.
The term "Broadcom" refers to Broadcom Inc. and/or its subsidiaries.
