package com.b2w.project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.project.domain.Planeta;
import com.b2w.project.impl.FilmImpl;
import com.b2w.project.repository.PlanetaRepository;
import com.b2w.project.service.exception.ObjectNotFoundException;
import com.b2w.project.service.exception.UnavailableServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class PlanetaServiceTest {

	@Autowired
	private PlanetaService planetaService;
	
	@Autowired
	private PlanetaRepository planetaRepository;
	
	private static Planeta findPlaneta;
	private static Planeta findPlaneta2;
	
	@Before
	public void init() {
		planetaRepository.deleteAll();
		Planeta p1 = new Planeta(null,"Terra","Temperate","Forests, mountains, lakes",0);
		Planeta p2 = new Planeta(null,"Marte","Arid","rock, desert, mountain",0);		
		planetaRepository.saveAll(Arrays.asList(p1,p2));
		
		Planeta p3 = new Planeta(null,"Naboo","Temperate","Grassy hills, swamps, forests, mountains",0);
		findPlaneta = planetaRepository.save(p3);
		
		Planeta p4 = new Planeta(null,"Dagobah","Temperate","Grassy hills, swamps, forests, mountains",0);
		findPlaneta2 = planetaRepository.save(p4);
	}
	
	@Test
	public void testfindAllPlaneta() {

		Page<Planeta> planetas = planetaService.findAll(0, 12, "nome", "ASC");

		assertEquals(planetas.getTotalElements(), 4);
		assertEquals(planetas.getTotalPages(), 1);
		assertEquals(planetas.getContent().get(0).getNome(), "Dagobah");
		
	}
	
	@Test
	public void testAddPlaneta() {
		
		Planeta planeta = new Planeta(null,"Dagobah","Temperate","Forests, mountains, lakes");
		Planeta newPlaneta = planetaService.add(planeta);
		
		assertNotNull(newPlaneta);
		assertNotNull(newPlaneta.getId());
		assertEquals("Dagobah", newPlaneta.getNome());
		
	}
	

	
	@Test
	public void testFindByNamePlaneta() {
		
		Page<Planeta> planetas = planetaService.findByName("Naboo",0, 12, "nome", "ASC");

		assertEquals(planetas.getTotalElements(), 1);
		assertEquals(planetas.getTotalPages(), 1);
		assertEquals(planetas.getContent().get(0).getNome(), "Naboo");
		
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testFindByNamePlanetaNotFound() {
		
		Page<Planeta> planetas = planetaService.findByName("ABC",0, 12, "nome", "ASC");

		assertEquals(planetas.getTotalElements(), 0);
		assertEquals(planetas.getTotalPages(), 0);
		assertEquals(planetas.getContent().get(0).getNome(), "Naboo");
		
	}
	
	@Test
	public void testFindByIdPlaneta() {
		
		Planeta planeta = planetaService.findById(findPlaneta.getId());
		assertEquals(planeta.getId(), findPlaneta.getId());
		assertEquals(planeta.getNome(), "Naboo");
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testFindByIdPlanetaNotFound() {
		
		Planeta planeta = planetaService.findById("1232");
		
	}

	@Test
	public void testRemovePlaneta() {
		planetaService.delete(findPlaneta2.getId());
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void testRemovePlanetaIdNotFound() {
		
		planetaService.delete("1234");

	}	
	
	@Test
	public void testNumberFilmsPlaneta() throws IOException {
		assertEquals(planetaService.searchFilms("Dagobah").intValue(), 3);
	}
	
	@Test
	public void testNumberFilmsPlanetaNotFound() throws IOException {
		assertEquals(planetaService.searchFilms("BAC").intValue(), 0);
	}
	
	@Test(expected = UnavailableServiceException.class)
	public void testNumberFilmsPlanetaErrorServer() throws IOException {
		FilmImpl film = new FilmImpl();
		film.setUrl("https://swapi.co/api/abc?search=");
		assertEquals(film.quantityFilms("Dagobah"), 3);
	}

}
