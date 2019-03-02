package com.example.presence.capture;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@Configuration
@ComponentScan(basePackages = "com.example.presence.capture")
public class CaptureConfiguration {
}
