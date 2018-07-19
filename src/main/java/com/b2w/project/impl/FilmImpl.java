package com.b2w.project.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.b2w.project.service.exception.UnavailableServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Busca a quantidade de aparições pelo nome do Filme na API swapi.co
 * */

public class FilmImpl implements Film{
	
	private Integer qtdFilmes;
	private String url = "https://swapi.co/api/planets?search=";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int quantityFilms(String nameFilm) throws IOException{
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:55.0) Gecko/20100101 Firefox/55.0");
		this.url += nameFilm;
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		try {
		ResponseEntity<String> response = restTemplate.exchange(this.url, HttpMethod.GET, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(response.getBody());
			JsonNode locatedNode = rootNode.path("results").findValue("films");
			List<String> list = mapper.readValue(locatedNode.toString(), new TypeReference<List<String>>() {
			});
			this.qtdFilmes = list.size();
			
		}else {
			this.qtdFilmes = 0;
		}
		
		}catch(HttpClientErrorException e) {
			throw new UnavailableServiceException("Web service Star War não está disponível");
		}catch(NullPointerException e) {
			this.qtdFilmes = 0;
		}catch(ResourceAccessException e){
			throw new UnavailableServiceException("Web service Star War não está disponível");
		}
		
		return this.qtdFilmes;
	}

}
