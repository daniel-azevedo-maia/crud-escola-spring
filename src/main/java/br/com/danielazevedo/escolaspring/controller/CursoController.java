package br.com.danielazevedo.escolaspring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielazevedo.escolaspring.exception.EntidadeEmUsoException;
import br.com.danielazevedo.escolaspring.exception.EntidadeNaoEncontradaException;
import br.com.danielazevedo.escolaspring.model.Curso;
import br.com.danielazevedo.escolaspring.model.Estudante;
import br.com.danielazevedo.escolaspring.repository.CursoRepository;
import br.com.danielazevedo.escolaspring.service.CursoService;
import br.com.danielazevedo.escolaspring.service.EstudanteService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

	private CursoRepository cursoRepository;

	private CursoService cursoService;

	@Autowired
	private EstudanteService estudanteService;

	// Ponto de injeção de dependência do CursoRepository
	public CursoController(CursoRepository cursoRepository, CursoService cursoService) {
		this.cursoRepository = cursoRepository;
		this.cursoService = cursoService;
	}

	// Listar todos os cursos

	@GetMapping("/listarTodos")
	public List<Curso> listarTodos() {
		return cursoService.listar();
	}

	// Localizar cursos por ID

	@GetMapping("/{cursoId}")
	public ResponseEntity<Curso> localizarPorId(@PathVariable Long cursoId) {
		Optional<Curso> curso = cursoService.localizarPorId(cursoId);
		if (curso.isPresent()) {
			return ResponseEntity.ok(curso.get());
		}

		return ResponseEntity.notFound().build();
	}

	// Cadastrar novo curso

	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarCurso(@RequestBody Curso curso) {
		try {
			cursoService.cadastrarCurso(curso);
			return ResponseEntity.status(HttpStatus.CREATED).body(curso);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Atualizar dados do curso

	@PutMapping("/{cursoId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cursoId, @RequestBody Curso curso) {
		try {

			Curso cursoAtual = cursoRepository.findById(cursoId).orElse(null);

			if (cursoAtual != null) {
				BeanUtils.copyProperties(curso, cursoAtual, "id", "dataCadastro");
				cursoService.cadastrarCurso(cursoAtual);
				return ResponseEntity.ok(cursoAtual);
			}

			return ResponseEntity.notFound().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{cursoId}")
	public ResponseEntity<?> excluir(@PathVariable Long cursoId) {
		try {
			
			// Primeiro localiza o cursoi
			Optional<Curso> curso = cursoService.localizarPorId(cursoId);
			
			// Remove vínculo entre estudantes e curso:
			// Para cada estudante, pegue este curso e remova.
			// Depois salve as alterações no BD para cada estudante.
			for (Estudante estudante : curso.get().getEstudantes()) {
				estudante.getCursos().remove(curso.get());
				estudanteService.cadastrarEstudante(estudante);
			}
			
			// Do lado de cursos, limpe todos os estudantes.
			curso.get().getEstudantes().clear();
			
			// Salve as alterações.
			cursoService.cadastrarCurso(curso.get());
			
			// Chame o método de excluir
			cursoService.excluir(cursoId);
			
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
