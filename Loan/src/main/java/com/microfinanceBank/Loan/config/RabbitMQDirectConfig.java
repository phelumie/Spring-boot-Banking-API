package com.microfinanceBank.Loan.config;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@EnableRabbit
@Configuration
public class RabbitMQDirectConfig implements RabbitListenerConfigurer {
    public static final String LOAN_ANALYSIS_QUEUE="LoanAnalysis.queue";
    public static final String LOAN_WITHDRAW_QUEUE="LoanWithdraw.queue";


	@Bean
	Queue LoanAnalysisQueue() { return new Queue("LoanAnalysisQueue", false); }

	@Bean
	Queue LoanWithdrawQueue() { return new Queue("LoanWithdrawQueue", false); }

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("direct-exchange");
	}


	@Bean
	Binding LoanAnalysisBinding(Queue LoanAnalysisQueue, DirectExchange exchange) {
		return BindingBuilder.bind(LoanAnalysisQueue).to(exchange).with(LOAN_ANALYSIS_QUEUE);
	}

	@Bean
	Binding LoanWithdrawBinding(Queue LoanWithdrawQueue, DirectExchange exchange) {
		return BindingBuilder.bind(LoanWithdrawQueue).to(exchange).with(LOAN_WITHDRAW_QUEUE);
	}


	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Jackson2JsonMessageConverter producerJsonMessageConverter(){

		return new Jackson2JsonMessageConverter(objectMapper());
	}
	@Bean
	public MappingJackson2MessageConverter consumerJacksonMessageConverter(){
		MappingJackson2MessageConverter converter=new MappingJackson2MessageConverter();
		converter.getObjectMapper().configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH,true);
		return converter;
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper(){
		JavaTimeModule module=new JavaTimeModule();
		return new ObjectMapper().registerModule(module);
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerFactory(){
		DefaultMessageHandlerMethodFactory factory=new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJacksonMessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
		rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerFactory());
	}

	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//		simpleMessageListenerContainer.setChannelTransacted(true);
//		simpleMessageListenerContainer.setTransactionManager(transactionManager(connectionFactory));
		return simpleMessageListenerContainer;
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter( producerJsonMessageConverter());
//	  	rabbitTemplate.setChannelTransacted(true);
		return rabbitTemplate;
	}
}
