package com.microfinanceBank.Transaction;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info =
@Info(title = "TRANSACTION API", version = "${springdoc.version}", description = "Documentation TRANSACTION API v1.0")
		,servers = {@Server(url = "http://localhost:8765/"+"${spring.application.name}"+"/")
},security = @SecurityRequirement(name = "Bearer Authentication")
)
@EnableAsync
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@EnableRetry
public class TransactionApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public TaskExecutor getAsyncExecutor(){
		ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(60);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		executor.afterPropertiesSet();
		return executor;
	}


//	@Bean
//	public ObjectMapper objectMapper(){
//		ObjectMapper mapper=new ObjectMapper();
//		mapper.registerModule(new JavaTimeModule());
//		mapper.registerModule(new Jdk8Module());
//		return mapper;
//	}

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}
//
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
//			}
//		};
//}

}
