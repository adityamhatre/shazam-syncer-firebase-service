package com.adityamhatre.firebase_checker.Kafka;

import lombok.Getter;

enum Topics {
	ON_RECEIVE_NEW_USER(TopicConstants.ON_RECEIVE_NEW_USER, 1);

	@Getter
	private final String topicName;

	@Getter
	private final int numPartitions;

	Topics(String topicName, int numPartitions) {
		this.topicName = topicName;
		this.numPartitions = numPartitions;
	}

	static class TopicConstants {
		static final String ON_RECEIVE_NEW_USER = "on_receive_new_user";
	}

}
