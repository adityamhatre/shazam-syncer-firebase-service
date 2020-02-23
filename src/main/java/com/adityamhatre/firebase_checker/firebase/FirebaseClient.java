package com.adityamhatre.firebase_checker.firebase;

import com.adityamhatre.firebase_checker.Kafka.KafkaProducer;
import com.adityamhatre.firebase_checker.service.DBService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Component
public class FirebaseClient {

	private final DBService dbService;
	private final KafkaProducer kafkaProducer;

	FirebaseClient(DBService dbService, KafkaProducer kafkaProducer) {
		this.dbService = dbService;
		this.kafkaProducer = kafkaProducer;
		startFirebase();
	}

	private void startFirebase() {
		try {
			InputStream serviceAccount = getClass().getResourceAsStream("/static/firebase-service-account.json");
			if (serviceAccount == null) {
				log.error("Cannot find firebase service account json file");
				return;
			}

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://my-shazam-songs.firebaseio.com")
					.build();

			FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
			FirebaseDatabase database = FirebaseDatabase.getInstance(firebaseApp);

			database.getReference("users").addChildEventListener(new SimplifiedChildEventListener(childAddedSnapshot -> {
				log.info("Got new user from firebase: {}", childAddedSnapshot.getKey());
				Map map = (Map) childAddedSnapshot.getValue();
				UserDTO user = UserDTO.builder()
						.objectId(childAddedSnapshot.getKey())
						.deviceFcmToken(map.get("fcm_token").toString())
						.inid(map.get("inid").toString())
						.codever(map.get("codever").toString())
						.username(map.get("name").toString())
						.frequency(map.containsKey("frequency") ? Integer.parseInt(map.get("frequency").toString()) : 5)
						.build();

				if (!dbService.isExistingUser(user)) {
					kafkaProducer.sendToReceiveNewUserTopic(user);
				} else {
					log.warn("User {} already existing in db, Skipping bootstrapping", user);
				}
			}, childChangedSnapshot -> {
			}));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
