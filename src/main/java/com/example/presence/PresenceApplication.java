package com.example.presence;

import com.example.presence.capture.CaptureConfiguration;
import com.example.presence.processing.ProcessingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import({CaptureConfiguration.class, ProcessingConfiguration.class})
public class PresenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresenceApplication.class, args);
	}

}
