# Spring Boot Kafka Tutorial
This is a simple Kafka tutorial application built with Spring Boot. It demonstrates how to produce and consume messages using Apache Kafka with both String and JSON formats.

## 🧰 Dependencies
This project uses only two dependencies which can be added via [Spring Initializr](https://start.spring.io/):

- **Spring Web**
- **Spring for Apache Kafka**

## ⚙️ Kafka Setup

Before running the Spring Boot app, make sure Kafka and Zookeeper servers are running. You can download Kafka from the [official site](https://kafka.apache.org/downloads).

### 🧪 Start Kafka (Windows Example)

1. **Start Zookeeper**  
   Navigate to Kafka directory and run:
```
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

2. **Start Kafka Broker**  
In another terminal:
```
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

## 🔧 Application Configuration

### `application.properties`
```properties
# Consumer config
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=myGroup
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted-packages=*

# Producer config
spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# Topic name
spring.kafka.topic.name=javaguides
```
🧪 How It Works
- Topics are created in KafkaTopicConfig — one for String messages and one for JSON messages.

- Producers send messages to the Kafka topic using either plain String or a custom User object.

- Controllers are used to send data via REST endpoints.

- Consumers receive the messages from Kafka topics and log them.

KafkaConsumerConfig (Required for Spring Boot 3+)

If using Spring Boot 3 or later, a custom Kafka Consumer Config like this is required:
```
@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, User> consumerFactory() {
        JsonDeserializer<User> jsonDeserializer = new JsonDeserializer<>(User.class);
        jsonDeserializer.addTrustedPackages("com.chryzsean.springboot_kafka_tutorial.payload");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroup");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

```

### 🚀 How to Run
1. Make sure Zookeeper and Kafka are running.

2. Run the Spring Boot application from your IDE or via terminal:
```
./mvnw spring-boot:run

```  
3. Use Postman to send requests to the controllers.

## Happy coding with Kafka! 🎉
