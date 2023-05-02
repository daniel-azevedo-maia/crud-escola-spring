package br.com.danielazevedo.escolaspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.danielazevedo.escolaspring.model.Curso;
import br.com.danielazevedo.escolaspring.repository.CursoRepository;

@Service
public class CursoService {
	
	private CursoRepository cursoRepository;

	// Ponto de injeção de dependência do EstudanteRepository
	public CursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}
	
	// Listar todos os cursos	
	
	public List<Curso> listar() {
		return cursoRepository.findAll();
	}
	
	// Localizar curso por ID
	
	public Optional<Curso> localizarPorId(Long id) {
		Optional<Curso> curso = cursoRepository.findById(id);
		return curso;
	}
	
	// Cadastrar novo curso
	
	public Curso cadastrarCurso(Curso curso) {
		return cursoRepository.save(curso);
	}
	
	// Excluir um curso
	public void excluir(Long cursoId) {
		cursoRepository.deleteById(cursoId);
	}

}
