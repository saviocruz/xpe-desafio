package com.xpe.arquitetura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * 
 * Esta aplicação implementa uma API REST para gerenciamento de produtos,
 * seguindo a arquitetura MVC e utilizando padrões de projeto como:
 * - Facade (ProdutoService)
 * - Adapter (ProdutoAdapter)
 * - Factory (ProdutoFactory)
 * 
 * Tecnologias utilizadas:
 * - Java 21
 * - Spring Boot 3.5
 * - H2 Database (file-based)
 * - Spring Security + JWT
 * - Swagger/OpenAPI 3.0
 */
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
