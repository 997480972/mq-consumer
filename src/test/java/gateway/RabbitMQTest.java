package gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.config.RabbitConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 测试发送消息到队列
	 */
	@Test
	public void sendToQueue(){
		for (int i = 0; i < 10; i++) {
			rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_FIRST, "你好，这是消息hello"+ i);
		}
		while(true){
			
		}
	}
	
	/**
	 * 测试发送消息到topic交换机
	 */
	@Test
	public void sendTopic(){
		for (int i = 0; i < 10; i++) {
			rabbitTemplate.convertAndSend("topicExchange","topic.msg", "主题消息：topic.hello");
		}
	}
}
