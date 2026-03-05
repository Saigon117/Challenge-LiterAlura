package com.literalura.literalura;

import com.literalura.literalura.principal.Principal;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Aquí cumplimos con llamar al método para exhibir el menú
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();
	}
}