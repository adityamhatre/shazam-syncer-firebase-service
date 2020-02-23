package com.adityamhatre.firebase_checker.firebase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FirebaseClientTest {
	@Autowired
	FirebaseClient firebaseClient;

	@Test
	void test() {

	}

}