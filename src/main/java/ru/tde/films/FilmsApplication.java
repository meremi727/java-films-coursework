package ru.tde.films;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "Films application", shortName = "Films")
public class FilmsApplication implements AppShellConfigurator {
	public static void main(String[] args) {
		SpringApplication.run(FilmsApplication.class, args);
	}
}