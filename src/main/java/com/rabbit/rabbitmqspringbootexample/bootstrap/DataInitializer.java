package com.rabbit.rabbitmqspringbootexample.bootstrap;

import com.rabbit.rabbitmqspringbootexample.RabbitmqSpringbootExampleApplication;
import com.rabbit.rabbitmqspringbootexample.receivers.Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DataInitializer implements CommandLineRunner {

    public static final String topicExchangeName = "spring-boot-exchange";
    public static final String queueName = "spring-boot";
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public DataInitializer(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
