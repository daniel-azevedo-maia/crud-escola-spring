package br.com.danielazevedo.escolaspring.application.dto.mapper;

import br.com.danielazevedo.escolaspring.application.dto.CursoDTO;
import br.com.danielazevedo.escolaspring.domain.model.Curso;
import br.com.danielazevedo.escolaspring.domain.model.Estudante;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CursoMapper {

    public CursoDTO toDto(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setNome(curso.getNome());
        dto.setDescricao(curso.getDescricao());
        // Mapear emails dos estudantes
        dto.setEstudantesEmails(curso.getEstudantes() != null
                ? curso.getEstudantes().stream()
                .map(Estudante::getEmail)
                .collect(Collectors.toSet())
                : null);
        return dto;
    }

    public Curso toEntity(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        curso.setDescricao(dto.getDescricao());
        return curso;
    }
}
