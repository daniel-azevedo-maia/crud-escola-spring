package br.com.danielazevedo.escolaspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.danielazevedo.escolaspring.model.Estudante;
import br.com.danielazevedo.escolaspring.repository.EstudanteRepository;

@Service
public class EstudanteService {
	
	private EstudanteRepository estudanteRepository;

	// Ponto de injeção de dependência do EstudanteRepository
	public EstudanteService(EstudanteRepository estudanteRepository) {
		this.estudanteRepository = estudanteRepository;
	}
	
	// Listar todos os estudantes	
	
	public List<Estudante> listar() {
		return estudanteRepository.findAll();
	}
	
	// Localizar estudante por ID
	
	public Optional<Estudante> localizarPorId(Long id) {
		Optional<Estudante> estudante = estudanteRepository.findById(id);
		return estudante;
	}
	
	// Cadastrar novo estudante
	
	public Estudante cadastrarEstudante(Estudante estudante) {
		return estudanteRepository.save(estudante);
	}
	
	// Excluir um estudante
	public void excluir(Long estudanteId) {
		estudanteRepository.deleteById(estudanteId);
	}

}
