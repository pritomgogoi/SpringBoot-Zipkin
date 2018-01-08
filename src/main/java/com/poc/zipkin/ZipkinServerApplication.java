package com.poc.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import zipkin.server.EnableZipkinServer;

import java.util.logging.Logger;

@EnableZipkinServer
@SpringBootApplication
public class ZipkinServerApplication extends SpringBootServletInitializer {

    private String environment = System.getProperty("CLOUD_ENVIRONMENT");
    private static final Logger LOG = Logger.getLogger(ZipkinServerApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(ZipkinServerApplication.class);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        if (environment != null) {
            LOG.info("Running in aws .....");
            return (container -> container.setPort(8080));
        } else {
            LOG.info("Running locally .....");
            return (container -> container.setPort(9411));
        }
    }
}
