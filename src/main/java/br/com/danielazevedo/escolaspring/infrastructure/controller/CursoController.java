package br.com.danielazevedo.escolaspring.infrastructure.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.com.danielazevedo.escolaspring.application.dto.CursoDTO;
import br.com.danielazevedo.escolaspring.application.dto.mapper.CursoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.danielazevedo.escolaspring.domain.model.Curso;
import br.com.danielazevedo.escolaspring.application.service.CursoService;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

	private final CursoService cursoService;
	private final CursoMapper cursoMapper;

	@GetMapping
	public List<CursoDTO> listarTodos() {
		return cursoService.listar().stream()
				.map(cursoMapper::toDto)
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Long id) {
		return cursoService.localizarPorId(id)
				.map(cursoMapper::toDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/buscar")
	public List<CursoDTO> buscarPorNome(@RequestParam String nome) {
		return cursoService.buscarPorNome(nome).stream()
				.map(cursoMapper::toDto)
				.collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<CursoDTO> criar(@Valid @RequestBody CursoDTO cursoDTO) {
		Curso curso = cursoMapper.toEntity(cursoDTO);
		Curso cursoSalvo = cursoService.salvar(curso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoMapper.toDto(cursoSalvo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CursoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CursoDTO cursoDTO) {
		return cursoService.atualizar(id, cursoDTO)
				.map(cursoMapper::toDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		cursoService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
