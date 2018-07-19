package com.b2w.project.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/*
 * Gera a documentação da API
 * http://localhost:8080/swagger-ui.html
 * */
@Configuration
@EnableSwagger2
@Profile("prod")
public class SwaggerConfig {

	private final ResponseMessage m201 = simpleMessage(201, "Recurso criado");
	private final ResponseMessage m204put = simpleMessage(204, "Atualização ok");
	private final ResponseMessage m204del = simpleMessage(204, "Deleção ok");
	private final ResponseMessage m403 = simpleMessage(403, "Não autorizado");
	private final ResponseMessage m404 = simpleMessage(404, "Não encontrado");
	private final ResponseMessage m405 = simpleMessage(405, "Recurso Incorreto");
	private final ResponseMessage m422 = simpleMessage(422, "Erro de validação");
	private final ResponseMessage m500 = simpleMessage(500, "Erro inesperado");
	private final ResponseMessage m503 = simpleMessage(503, "Erro Comunicação com Servidor Star Wars");

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m403, m422, m500,m503))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404,m405, m500)).select()
				.apis(RequestHandlerSelectors.basePackage("com.b2w.project.resource"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API Planetas", "Hora do Desafio", "Versão 1.0", "https://www.danielcavalcanti.com.br",
				new Contact("Daniel Cavalcanti", "https://www.danielcavalcanti.com.br",
						"daniel.cavalcanti@outlook.com.br"),
				"", "", Collections.emptyList() // Vendor Extensions
		);
	}

	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}
}