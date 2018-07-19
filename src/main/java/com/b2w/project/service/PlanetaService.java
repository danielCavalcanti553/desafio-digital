package com.b2w.project.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.b2w.project.domain.Planeta;
import com.b2w.project.impl.FilmImpl;
import com.b2w.project.repository.PlanetaRepository;
import com.b2w.project.service.exception.ObjectNotFoundException;
import com.b2w.project.service.exception.UnavailableServiceException;


@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;
	
	/*
	 * Adiciona um planeta, incluindo o número de aparições em consulta a api Star Wars
	 * */
	public Planeta add(Planeta planeta) {
		try {
			
			planeta.setQuantidadeDeFilmes(searchFilms(planeta.getNome()));
			Planeta newPlaneta = planetaRepository.save(planeta);
			return newPlaneta;
			
		} catch (IOException e) {
			throw new UnavailableServiceException("Web service Star War não está disponível");
		}

	}
	
	/*
	 * Busca paginada, retorna todos os planetas
	 * */
	
	public Page<Planeta> findAll(Integer page, Integer linesPerPage, String order, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), order);
		return planetaRepository.findAll(pageRequest);
	}
	
	/*
	 * Busca paginada por nome, retorna todos os planetas encontrados
	 * */
	public Page<Planeta> findByName(
			String nome, 
			Integer page, 
			Integer linesPerPage, 
			String order,
			String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), order);
		return planetaRepository.findByNomeContainingIgnoreCase(nome,pageRequest);
	}
	
	/*
	 * Busca um determinado objeto por ID
	 * */
	public Planeta findById(String id) {
		Optional<Planeta> obj = planetaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	/*
	 * Deleta um objeto por ID, antes verifica se ele existe
	 * */
	public void delete(String id) {
		findById(id);
		planetaRepository.deleteById(id);
	}
	
	/*
	 * 
	 * Busca aparições na API Star Wars
	 * */
	public Integer searchFilms(String namePlanet) throws IOException {
		FilmImpl films = new FilmImpl();
		return films.quantityFilms(namePlanet);
	}
	
	
}