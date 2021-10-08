# RabbitMQ PerfTest for Cloud Foundry

This is a template project to easily run [RabbitMQ Performance Tool](https://www.rabbitmq.com/java-tools.html#throughput-load-testing)
on [Cloud Foundry](https://www.cloudfoundry.org/). It has been tested against
[VMware Tanzu Application Service](https://tanzu.vmware.com/application-service).

## How to use

Pre-requisites: JDK 8 or higher and
[Cloud Foundry Command Line Interface](https://docs.cloudfoundry.org/cf-cli/) installed.

* Compile and package the application: `./mvnw clean package -Dmaven.test.skip`
* Configure the application with the `manifest.yml` file. PerfTest is configured
with environment variables or with the `JBP_CONFIG_JAVA_MAIN` manifest key.
* Push the application to Cloud Foundry: `cf push`.

The Cloud Foundry application must be bound to a RabbitMQ service. It will automatically
get the connection information from the `VCAP_SERVICES` environment variable.

Consult [Stream Perf Test documentation](https://rabbitmq.github.io/rabbitmq-stream-java-client/stable/htmlsingle/#the-performance-tool)
to learn more about the available options.

You can use environment variables in the manifest to configure PerfTest.
The environment variable names use a snake-case version of the command line
argument long names, e.g. `--multi-ack-every` becomes `MULTI_ACK_EVERY`. Run the following
command to see all the available environment variables:

```
./mvnw -q compile exec:java -Dexec.args="-env"
```

Alternatively, you can use the `JBP_CONFIG_JAVA_MAIN` manifest key to specify
the command line for PerfTest. Run the following command to see
all the available command line arguments:

```
./mvnw -q compile exec:java -Dexec.args="--help"
```

Below are some simple examples showing configuration with both ways.

[comment]: <> (PerfTest runs 1 producer and 1 consumer by default, the following snippet)

[comment]: <> (shows how to use 1 producer and 2 consumers:)

[comment]: <> (```yaml)

[comment]: <> (# with environment variables)

[comment]: <> (env:)

[comment]: <> (  PRODUCERS: 1)

[comment]: <> (  CONSUMERS: 2)

[comment]: <> (# or with the full command line)

[comment]: <> (env:)

[comment]: <> (  JBP_CONFIG_JAVA_MAIN: >)

[comment]: <> (    { arguments: "-x 1 -y 2" })

[comment]: <> (```)

[comment]: <> (PerfTest uses by default 12-byte long messages, here is how to use 1 kB messages:)

[comment]: <> (```yaml)

[comment]: <> (# with environment variables)

[comment]: <> (env:)

[comment]: <> (  PRODUCERS: 1)

[comment]: <> (  CONSUMERS: 2)

[comment]: <> (  SIZE: 1000)

[comment]: <> (# or with the full command line)

[comment]: <> (env:)

[comment]: <> (  JBP_CONFIG_JAVA_MAIN: >)

[comment]: <> (    { arguments: "-x 1 -y 2 -s 1000" })

[comment]: <> (```)

[comment]: <> (Producers publish as fast as possible by default, here is how to limit)

[comment]: <> (the publishing rate to 500 messages / second:)

[comment]: <> (```yaml)

[comment]: <> (# with environment variables)

[comment]: <> (env:)

[comment]: <> (  PRODUCERS: 1)

[comment]: <> (  CONSUMERS: 2)

[comment]: <> (  RATE: 500)

[comment]: <> (# or with the full command line)

[comment]: <> (env:)

[comment]: <> (  JBP_CONFIG_JAVA_MAIN: >)

[comment]: <> (    { arguments: "-x 1 -y 2 --rate 500" })

[comment]: <> (```)

[comment]: <> (PerfTest uses by default one queue, here is how to define a sequence of queues,)

[comment]: <> (from `test-1` to `test-10`:)

[comment]: <> (```yaml)

[comment]: <> (# with environment variables)

[comment]: <> (env:)

[comment]: <> (  PRODUCERS: 10)

[comment]: <> (  CONSUMERS: 20)

[comment]: <> (  QUEUE_PATTERN: test-%d)

[comment]: <> (  QUEUE_PATTERN_FROM: 1)

[comment]: <> (  QUEUE_PATTERN_TO: 10)

[comment]: <> (# or with the full command line)

[comment]: <> (env:)

[comment]: <> (  JBP_CONFIG_JAVA_MAIN: >)

[comment]: <> (    { arguments: "-x 10 -y 20 --queue-pattern 'test-%d' --queue-pattern-from 1 --queue-pattern-to 10" })

[comment]: <> (```)

[comment]: <> (See PerfTest documentation on [high load simulation]&#40;https://rabbitmq.github.io/rabbitmq-perf-test/milestone/htmlsingle/#simulating-high-loads&#41;)

[comment]: <> (if you want to run hundreds of connections or more.)

[comment]: <> (## Metrics Support)

[comment]: <> (PerfTest has also options for metrics. Run the following command to see the)

[comment]: <> (available options as environment variables:)

[comment]: <> (```)

[comment]: <> (./mvnw -q compile exec:java -Dexec.args="-mh -env")

[comment]: <> (```)

[comment]: <> (Launch the following command to see the available options as command line arguments:)

[comment]: <> (```)

[comment]: <> (./mvnw -q compile exec:java -Dexec.args="-mh")

[comment]: <> (```)

## PerfTest version

The Stream Client version to use is set into the `pom.xml` file, e.g. `<stream-client.version>0.4.0</stream-client.version>`.
This can be changed before packaging and deploying on Cloud Foundry. Versions are typically latest stable,
milestones or release candidates, and snapshots. To know about the available versions:

 * [Stable versions](https://repo1.maven.org/maven2/com/rabbitmq/stream-client/)
 * [Milestones and release candidates](https://packagecloud.io/app/rabbitmq/maven-milestones/search?q=stream-client)
 * [Snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/rabbitmq/stream-client/)

## Community / Support

* [GitHub Issues](https://github.com/rabbitmq/rabbitmq-stream-perf-test-for-cf/issues)

## License ##

RabbitMQ PerfTest for Tanzu Application Service is [Apache 2.0 licensed](https://www.apache.org/licenses/LICENSE-2.0.html).

_Sponsored by [VMware](https://vmware.com)_
