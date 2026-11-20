package com.aquamonitor.platform.u202211295;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AquaMonitorPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(AquaMonitorPlatformApplication.class, args);
    }
}
