package com.config;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class Receiver {
	/**
	 * 处理消息
	 * @param content
	 * @throws IOException 
	 */
	@RabbitListener(queues = RabbitConfig.QUEUE_FIRST) //监听队列
	public void processMessage1(String content, Channel channel, Message message) throws IOException {
		try {
			System.out.println("消费者1收到消息：" + content);
			//手动确认消息,消费者发送一个消息应答rabbitMQ才会删除消息
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
			//抛弃此条消息
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		} catch (Exception e) {
			e.printStackTrace();
			//重新放入队列
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
	}
	
	@RabbitListener(queues = RabbitConfig.QUEUE_SECOND)
	public void processMessage2(String content, Channel channel, Message message) throws IOException {
		System.out.println("消费者2收到消息：" + content);
		try {
			//手动确认消息,消费者发送一个消息应答rabbitMQ才会删除消息
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
			//抛弃此条消息
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		} catch (Exception e) {
			e.printStackTrace();
			//重新放入队列
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
		
	}
}
