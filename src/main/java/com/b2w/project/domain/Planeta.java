package com.b2w.project.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Planeta implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String nome;
	private String clima;
	private String terreno;
	private Integer quantidadeDeFilmes;
	
	public Planeta() {
	}

	public Planeta(String id, String nome, String clima, String terreno) {
		this.id = id;
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;

	}
	public Planeta(String id, String nome, String clima, String terreno, Integer quantidadeDeFilmes) {
		this.id = id;
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
		this.quantidadeDeFilmes = quantidadeDeFilmes;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planeta other = (Planeta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Planeta {id=" + id + ", nome=" + nome + ", clima=" + clima + ", terreno=" + terreno
				+ ", quantidadeDeFilmes=" + quantidadeDeFilmes + "}";
	}

	
	
	
}
