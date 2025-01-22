package br.com.danielazevedo.escolaspring.application.dto.mapper;

import br.com.danielazevedo.escolaspring.application.dto.EstudanteDTO;
import br.com.danielazevedo.escolaspring.domain.model.Curso;
import br.com.danielazevedo.escolaspring.domain.model.Estudante;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EstudanteMapper {

    public EstudanteDTO toDto(Estudante estudante) {
        EstudanteDTO dto = new EstudanteDTO();
        dto.setId(estudante.getId());
        dto.setNome(estudante.getNome());
        dto.setEmail(estudante.getEmail());
        dto.setCpf(estudante.getCpf()); // Certifique-se de mapear o CPF
        dto.setCursosNomes(estudante.getCursos().stream()
                .map(Curso::getNome).collect(Collectors.toSet()));
        return dto;
    }

    public Estudante toEntity(EstudanteDTO dto) {
        Estudante estudante = new Estudante();
        estudante.setNome(dto.getNome());
        estudante.setEmail(dto.getEmail());
        estudante.setCpf(dto.getCpf()); // Certifique-se de atribuir o CPF
        return estudante;
    }
}