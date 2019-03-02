package com.example.presence;

import com.example.presence.capture.CaptureConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import({ CaptureConfiguration.class })
public class PresenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresenceApplication.class, args);
	}

}
