package com.bt.reflection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bt.reflection.controller.Sample1Controller;

@Configuration
public class ReflectionConfiguration {

	@Bean
	public Sample1Controller sample1Controller() {
		return new Sample1Controller();
	}
}
