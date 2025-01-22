package br.com.danielazevedo.escolaspring.application.service;

import java.util.List;
import java.util.Optional;

import br.com.danielazevedo.escolaspring.application.dto.EstudanteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.danielazevedo.escolaspring.domain.model.Estudante;
import br.com.danielazevedo.escolaspring.domain.repository.EstudanteRepository;

@Service
@RequiredArgsConstructor
public class EstudanteService {

	private final EstudanteRepository estudanteRepository;

	public List<Estudante> listar() {
		return estudanteRepository.findAll();
	}

	public Optional<Estudante> localizarPorId(Long id) {
		return estudanteRepository.findById(id);
	}

	public List<Estudante> buscarPorNomeOuEmail(String nome, String email) {
		return estudanteRepository.findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(nome, email);
	}

	public Estudante salvar(Estudante estudante) {
		return estudanteRepository.save(estudante);
	}

	public Optional<Estudante> atualizar(Long id, EstudanteDTO estudanteDTO) {
		return estudanteRepository.findById(id).map(estudante -> {
			estudante.setNome(estudanteDTO.getNome());
			estudante.setEmail(estudanteDTO.getEmail());
			return estudanteRepository.save(estudante);
		});
	}

	public void excluir(Long id) {
		estudanteRepository.deleteById(id);
	}
}
