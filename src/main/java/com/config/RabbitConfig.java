package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitConfig {
	public static final String QUEUE_FIRST = "queue1"; //队列名
	public static final String QUEUE_SECOND = "queue2"; //队列名
	
	/**
	 * 声明队列
	 * @return
	 */
	@Bean
	public Queue queueFirst() {
		return new Queue(QUEUE_FIRST);
	}
	@Bean
	public Queue queueSecond() {
		return new Queue(QUEUE_SECOND);
	}

	/**
	 * 声明fanout交换机
	 * @return
	 */
	@Bean
	public Exchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");
	}
	
	/**
	 * 队列绑定fanout交换机，不需要路由键（路由键会忽略）
	 * @param queueFirst
	 * @param fanoutExchange
	 * @return
	 */
	@Bean
	Binding bindingExchangeMessage(Queue queueFirst, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queueFirst).to(fanoutExchange);
	}
	@Bean
	Binding bindingExchangeMessage1(Queue queueSecond, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queueSecond).to(fanoutExchange);
	}

	/**
	 * 声明topic交换机
	 * @return
	 */
	@Bean
	public Exchange topicExchange() {
		return new TopicExchange("topicExchange");
	}
	/**
	 * 队列绑定topic交换机
	 * @param queueFirst 队列Bean
	 * @param topicExchange 交换机Bean
	 * @return
	 */
	@Bean
	Binding bindingExchangeMessage(Queue queueFirst, TopicExchange topicExchange) {
		return BindingBuilder.bind(queueFirst).to(topicExchange).with("topic.*");
	}
	@Bean
	Binding bindingExchangeMessage1(Queue queueSecond, TopicExchange topicExchange) {
		return BindingBuilder.bind(queueSecond).to(topicExchange).with("topic.#");
	}
}
