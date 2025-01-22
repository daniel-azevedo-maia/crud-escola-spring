package br.com.danielazevedo.escolaspring.infrastructure.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.com.danielazevedo.escolaspring.application.dto.EstudanteDTO;
import br.com.danielazevedo.escolaspring.application.dto.mapper.EstudanteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import br.com.danielazevedo.escolaspring.domain.model.Curso;
import br.com.danielazevedo.escolaspring.domain.model.Estudante;

import br.com.danielazevedo.escolaspring.application.service.EstudanteService;

@RestController
@RequestMapping("/api/estudantes")
@RequiredArgsConstructor
public class EstudanteController {

	private final EstudanteService estudanteService;
	private final EstudanteMapper estudanteMapper;

	@GetMapping
	public List<EstudanteDTO> listarTodos() {
		return estudanteService.listar().stream()
				.map(estudanteMapper::toDto)
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstudanteDTO> buscarPorId(@PathVariable Long id) {
		return estudanteService.localizarPorId(id)
				.map(estudanteMapper::toDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/buscar")
	public List<EstudanteDTO> buscarPorNomeOuEmail(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) String email) {
		if ((nome == null || nome.isBlank()) && (email == null || email.isBlank())) {
			throw new IllegalArgumentException("Pelo menos um parâmetro de busca (nome ou email) deve ser informado");
		}
		return estudanteService.buscarPorNomeOuEmail(nome, email).stream()
				.map(estudanteMapper::toDto)
				.collect(Collectors.toList());
	}


	@PostMapping
	public ResponseEntity<EstudanteDTO> criar(@Valid @RequestBody EstudanteDTO estudanteDTO) {
		Estudante estudante = estudanteMapper.toEntity(estudanteDTO);
		Estudante estudanteSalvo = estudanteService.salvar(estudante);
		return ResponseEntity.status(HttpStatus.CREATED).body(estudanteMapper.toDto(estudanteSalvo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstudanteDTO> atualizar(@PathVariable Long id, @RequestBody EstudanteDTO estudanteDTO) {
		return estudanteService.atualizar(id, estudanteDTO)
				.map(estudanteMapper::toDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		estudanteService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
