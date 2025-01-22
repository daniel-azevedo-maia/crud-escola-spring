package br.com.danielazevedo.escolaspring.application.service;

import java.util.List;
import java.util.Optional;

import br.com.danielazevedo.escolaspring.application.dto.CursoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.danielazevedo.escolaspring.domain.model.Curso;
import br.com.danielazevedo.escolaspring.domain.repository.CursoRepository;

@Service
@RequiredArgsConstructor
public class CursoService {

	private final CursoRepository cursoRepository;

	public List<Curso> listar() {
		return cursoRepository.findAll();
	}

	public Optional<Curso> localizarPorId(Long id) {
		return cursoRepository.findById(id);
	}

	public List<Curso> buscarPorNome(String nome) {
		return cursoRepository.findByNomeContainingIgnoreCase(nome);
	}

	public Curso salvar(Curso curso) {
		return cursoRepository.save(curso);
	}

	public Optional<Curso> atualizar(Long id, CursoDTO cursoDTO) {
		return cursoRepository.findById(id).map(curso -> {
			curso.setNome(cursoDTO.getNome());
			curso.setDescricao(cursoDTO.getDescricao());
			return cursoRepository.save(curso);
		});
	}

	public void excluir(Long id) {
		cursoRepository.deleteById(id);
	}
}
