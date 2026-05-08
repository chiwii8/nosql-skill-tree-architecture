package com.nosql_tree;

import com.nosql_tree.user.infrastructure.outbound.mongodb.SpringDataMongoRepository;
import com.nosql_tree.user.infrastructure.outbound.mongodb.UserDocument;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class NosqlTreeApplication {

	@Bean
	CommandLineRunner inicializarDatos(SpringDataMongoRepository repository) { // Inyectamos el repo directamente para probar
		return args -> {
			UserDocument doc = new UserDocument();
			doc.setId("1");
			doc.setName("Pedro");

			repository.save(doc);

			// --- ESTA ES LA PRUEBA ---
			long count = repository.count();
			System.out.println("📊 Total de documentos en la colección: " + count);

			repository.findAll().forEach(u ->
					System.out.println("👤 Usuario en BD: " + u.getName() + " con ID: " + u.getId())
			);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(NosqlTreeApplication.class, args);
	}

}
