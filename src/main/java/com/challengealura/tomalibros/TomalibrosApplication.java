package com.challengealura.tomalibros;

import com.challengealura.tomalibros.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TomalibrosApplication implements CommandLineRunner {

	private final Principal principal;

	public TomalibrosApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(TomalibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.showMenu();
	}
}
