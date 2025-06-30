package todobarato.minimarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("===========================================");
		System.out.println("    INICIANDO SISTEMA TODO BARATO");
		System.out.println("    Minimarket - Los Olivos");
		System.out.println("    RUC: 20601893407");
		System.out.println("===========================================");
		
		SpringApplication.run(Application.class, args);
		
		System.out.println("===========================================");
		System.out.println("	SISTEMA TODO BARATO INICIADO");
		System.out.println("	Entra a la web aquí: http://localhost:8080/todobarato");
		System.out.println("===========================================");
	}

}
