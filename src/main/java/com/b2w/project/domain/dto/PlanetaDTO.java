package com.b2w.project.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.b2w.project.domain.Planeta;


public class PlanetaDTO  implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	@NotEmpty(message="Nome é obrigatório")
	private String nome;
	@NotEmpty(message="Clima é obrigatório")
	private String clima;
	@NotEmpty(message="Terreno é obrigatório")
	private String terreno;

	private Integer quantidadeDeFilmes;
	
	public PlanetaDTO() {
	}

	public PlanetaDTO(Planeta obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.clima = obj.getClima();
		this.terreno = obj.getTerreno();
		this.quantidadeDeFilmes = obj.getQuantidadeDeFilmes();
	}
	
	public Planeta fromPlaneta() {
		return new Planeta(this.getId(), this.getNome(), this.getClima(), this.getTerreno());
	}

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}
	


	public Integer getQuantidadeDeFilmes() {
		return quantidadeDeFilmes;
	}

	public void setQuantidadeDeFilmes(Integer quantidadeDeFilmes) {
		this.quantidadeDeFilmes = quantidadeDeFilmes;
	}
}
