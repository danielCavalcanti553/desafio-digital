package com.b2w.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.b2w.project.domain.Planeta;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String>{
	/*
	 * Retorna uma consulta paginada pelo nome do Planeta, ignorando maiúsculas e minísculas
	 * */
	Page<Planeta> findByNomeContainingIgnoreCase(String nome,Pageable pageRequest);

}
