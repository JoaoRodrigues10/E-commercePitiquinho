package br.loja.pitiquinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.loja.pitiquinho")
public class PitiquinhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PitiquinhoApplication.class, args);
	}

}
