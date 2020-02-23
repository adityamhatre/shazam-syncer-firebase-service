package com.adityamhatre.firebase_checker.Kafka;

import dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.adityamhatre.firebase_checker.Kafka.Topics.ON_RECEIVE_NEW_USER;

@Component
@Slf4j
public class KafkaProducer {
	private final KafkaTemplate<String, UserDTO> userDTOKafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, UserDTO> kafkaTemplate) {
		this.userDTOKafkaTemplate = kafkaTemplate;
	}

	public void sendToReceiveNewUserTopic(UserDTO user) {
		log.info("Sending data to {} channel", ON_RECEIVE_NEW_USER);
		this.userDTOKafkaTemplate.send(ON_RECEIVE_NEW_USER.getTopicName(), user);
	}
}
