package com.b2w.project.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.b2w.project.domain.Planeta;
import com.b2w.project.domain.dto.PlanetaDTO;
import com.b2w.project.service.PlanetaService;
import com.b2w.project.utils.URLDecode;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/planetas")
public class PlanetaResource {

	@Autowired
	private PlanetaService planetaService;
	
	@ApiOperation(value="Cadastra um novo planeta")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PlanetaDTO dto){
		Planeta planeta = planetaService.add(dto.fromPlaneta());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(planeta.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Busca paginada todos os planetas")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<PlanetaDTO>> findAll(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="order", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction
			){
		
		Page<Planeta> list = planetaService.findAll(page, linesPerPage, orderBy, direction);
		Page<PlanetaDTO> dto = list.map(obj -> new PlanetaDTO(obj));
		return ResponseEntity.ok().body(dto);
	}
	
	@ApiOperation(value="Encontra um planeta pelo ID")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<PlanetaDTO> findById(@PathVariable String id){	
		Planeta planeta = planetaService.findById(id);
		return ResponseEntity.ok().body(new PlanetaDTO(planeta));
	}
	
	@ApiOperation(value="Busca pagina por nome do planeta")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public ResponseEntity<Page<PlanetaDTO>> findByName(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="12")Integer linesPerPage, 
			@RequestParam(value="order", defaultValue="name")String order, 
			@RequestParam(value="direction", defaultValue="ASC")String direction	
			){
		nome = URLDecode.decodeParam(nome);
		Page<Planeta> list = planetaService.findByName(nome, page, linesPerPage, order, direction);
		Page<PlanetaDTO> dto = list.map(obj -> new PlanetaDTO(obj));
		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value="Exclui um planeta por ID")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id){	
		planetaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

