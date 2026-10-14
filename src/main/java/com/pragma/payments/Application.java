package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class Application {
    private final Environment environment;

    public Application(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("Application started with active profiles: " +
            Arrays.toString(environment.getActiveProfiles()));
        
        // Validar configuración crítica
        validateCriticalConfiguration();
    }

    private void validateCriticalConfiguration() {
        String datasourceUrl = environment.getProperty("spring.datasource.url");
        if (datasourceUrl == null || !datasourceUrl.startsWith("jdbc:postgresql:")) {
            throw new IllegalStateException("La configuración del datasource es inválida. " +
                "Verifica la propiedad 'spring.datasource.url' en application.yml");
        }
        
        Integer bulkheadMaxConcurrent = environment.getProperty(
            "resilience4j.bulkhead.instances.processPayment.maxConcurrentCalls", Integer.class);
        if (bulkheadMaxConcurrent == null || bulkheadMaxConcurrent <= 0) {
            throw new IllegalStateException("La configuración de Resilience4j Bulkhead es inválida. " +
                "Verifica la propiedad 'resilience4j.bulkhead.instances.processPayment.maxConcurrentCalls'");
        }
    }
}