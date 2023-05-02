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
import br.com.danielazevedo.escolaspring.repository.EstudanteRepository;
import br.com.danielazevedo.escolaspring.service.CursoService;
import br.com.danielazevedo.escolaspring.service.EstudanteService;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

	private EstudanteRepository estudanteRepository;

	private EstudanteService estudanteService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private CursoController cursoController;

	// Ponto de injeção de dependência do EstudanteRepository
	public EstudanteController(EstudanteRepository estudanteRepository, EstudanteService estudanteService) {
		this.estudanteRepository = estudanteRepository;
		this.estudanteService = estudanteService;
	}

	// Listar todos os estudantes

	@GetMapping("/listarTodos")
	public List<Estudante> listarTodos() {
		return estudanteService.listar();
	}

	// Localizar estudante por ID

	@GetMapping("/{estudanteId}")
	public ResponseEntity<Estudante> localizarPorId(@PathVariable Long estudanteId) {
		Optional<Estudante> estudante = estudanteService.localizarPorId(estudanteId);
		if (estudante.isPresent()) {
			return ResponseEntity.ok(estudante.get());
		}

		return ResponseEntity.notFound().build();
	}

	// Cadastrar novo estudante

	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarEstudante(@RequestBody Estudante estudante) {
		try {
			estudanteService.cadastrarEstudante(estudante);
			return ResponseEntity.status(HttpStatus.CREATED).body(estudante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Atualizar dados do estudante

	@PutMapping("/{estudanteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long estudanteId, @RequestBody Estudante estudante) {
		try {

			Estudante estudanteAtual = estudanteRepository.findById(estudanteId).orElse(null);

			if (estudanteAtual != null) {
				BeanUtils.copyProperties(estudante, estudanteAtual, "id", "dataCadastro");
				estudanteService.cadastrarEstudante(estudanteAtual);
				return ResponseEntity.ok(estudanteAtual);
			}

			return ResponseEntity.notFound().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{estudanteId}")
	public ResponseEntity<?> excluir(@PathVariable Long estudanteId) {
		try {
			estudanteService.excluir(estudanteId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@PutMapping("/{estudanteId}/adicionarCurso/{cursoId}")
	public ResponseEntity<?> adicionarEstudanteEmCurso(@PathVariable Long estudanteId, @PathVariable Long cursoId) {
		try {
			Optional<Estudante> estudante = estudanteService.localizarPorId(estudanteId);
			Optional<Curso> curso = cursoService.localizarPorId(cursoId);

			if (!estudante.isPresent() || !curso.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			estudante.get().getCursos().add(curso.get());
			curso.get().getEstudantes().add(estudante.get());

			estudanteService.cadastrarEstudante(estudante.get());
			cursoService.cadastrarCurso(curso.get());

			return ResponseEntity.ok().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{estudanteId}/removerCurso/{cursoId}")
	public ResponseEntity<?> removerEstudanteDeCurso(@PathVariable Long estudanteId, @PathVariable Long cursoId) {
		try {
			Optional<Estudante> estudante = estudanteService.localizarPorId(estudanteId);
			Optional<Curso> curso = cursoService.localizarPorId(cursoId);

			if (!estudante.isPresent() || !curso.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			estudante.get().getCursos().remove(curso.get());
			curso.get().getEstudantes().remove(estudante.get());
			
			estudanteService.cadastrarEstudante(estudante.get());
			cursoService.cadastrarCurso(curso.get());

			return ResponseEntity.ok().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
