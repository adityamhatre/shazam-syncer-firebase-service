package com.adityamhatre.firebase_checker.service;

import dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import javax.annotation.PostConstruct;
import java.io.IOException;

interface DB {
	@GET("/api/users/{userObjectId}/exists")
	Call<Boolean> isExistingUser(@Path("userObjectId") String objectId);
}

@Component
public class DBService {
	@Value("${database-service:http://database-service:8080}")
	private String databaseServiceURL;

	private DB db;

	@PostConstruct
	private void init() {
		this.db = new Retrofit.Builder()
				.baseUrl(databaseServiceURL)
				.addConverterFactory(JacksonConverterFactory.create())
				.build()
				.create(DB.class);
	}

	public boolean isExistingUser(UserDTO userDTO) {
		Call<Boolean> call = db.isExistingUser(userDTO.getObjectId());
		try {
			Response<Boolean> response = call.execute();
			if (response.isSuccessful()) {
				Boolean result = response.body();
				if (result != null) {
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}

