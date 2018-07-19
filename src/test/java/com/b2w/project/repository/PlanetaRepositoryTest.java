package com.b2w.project.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.project.domain.Planeta;
import com.b2w.project.service.exception.ObjectNotFoundException;

@RunWith(SpringRunner.class)
@DataMongoTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PlanetaRepositoryTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private PlanetaRepository repository;
	
	@Before
	public void init() {
		repository.deleteAll();
	}

	@Test
	public void testSaveAndFindIdPlaneta() throws Exception {
		mongoTemplate.save(new Planeta("5b50610fd5e02a3560899655","Terra","Temperate","Forests, mountains, lakes"));

		Optional<Planeta> obj = this.repository.findById("5b50610fd5e02a3560899655");
		Planeta newPlaneta = obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		assertThat(newPlaneta.getNome()).isEqualTo("Terra");
		assertThat(newPlaneta.getId()).isEqualTo("5b50610fd5e02a3560899655");
	}
	

	@Test(expected=ObjectNotFoundException.class)
	public void testSaveAndFindIdPlanetaNotFound() throws Exception {
		Optional<Planeta> obj = this.repository.findById("12345");
		Planeta newPlaneta = obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	@Test
	public void testSaveAndFindNamePlaneta() throws Exception {
		mongoTemplate.save(new Planeta("5b50610fd5e02a3560899655","Naboo","Temperate","Forests, mountains, lakes"));
		PageRequest pageRequest = PageRequest.of(0, 12, Direction.valueOf("ASC"), "nome");
		Page<Planeta> planetas = repository.findByNomeContainingIgnoreCase("Naboo",pageRequest);
		
		assertEquals(planetas.getTotalElements(), 1);
		assertEquals(planetas.getTotalPages(), 1);
		assertEquals(planetas.getContent().get(0).getNome(), "Naboo");
	}
	
	@Test
	public void testSaveAndFindNamePlanetaNotFound() throws Exception {
		PageRequest pageRequest = PageRequest.of(0, 12, Direction.valueOf("ASC"), "nome");
		Page<Planeta> planetas = repository.findByNomeContainingIgnoreCase("Venus",pageRequest);
		assertEquals(planetas.getTotalElements(), 0);
		assertEquals(planetas.getTotalPages(), 0);
	}

	@Test
	public void testListPlanetas() throws Exception {
		mongoTemplate.save(new Planeta("5b50610fd5e02a3560899655","Naboo","Temperate","Forests, mountains, lakes"));
		mongoTemplate.save(new Planeta("5b50610fd5e02a3560899656","Terra","Temperate","Forests, mountains, lakes"));
		
		List<Planeta> list = repository.findAll();
		assertEquals(list.get(0).getNome(), "Naboo");
		assertEquals(list.get(1).getId(), "5b50610fd5e02a3560899656");
		assertEquals(list.size(), 2);
		
	}

	@Test
	public void testRemovePlanetas() throws Exception {
		mongoTemplate.save(new Planeta("5b50610fd5e02a3560899657","Saturno","Temperate","Forests, mountains, lakes"));
		repository.deleteById("5b50610fd5e02a3560899657");
	}

}
