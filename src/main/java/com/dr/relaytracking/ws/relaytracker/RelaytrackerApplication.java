package com.dr.relaytracking.ws.relaytracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RelaytrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelaytrackerApplication.class, args);
	}
	@Value("${spring.application.name}")
	private String appName;
	
	

//	@Bean
//	public io.opentracing.Tracer zipkinTracer(){
//		OkHttpSender okHttpSender = OkHttpSender.create("http://localhost:9411/api/v1/spans");
//		AsyncReporter<Span> reporter = AsyncReporter.builder(okHttpSender).build();
//		Tracing braveTracer = Tracing.newBuilder().localServiceName(appName).reporter(reporter).build();
//		return BraveTracer.create(braveTracer);
//	}
}
