package br.com.danielazevedo.escolaspring.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CursoDTO {

    private Long id;

    @NotBlank(message = "O nome do curso é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do curso deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição do curso é obrigatória")
    @Size(max = 500, message = "A descrição do curso não pode ultrapassar 500 caracteres")
    private String descricao;

    private Set<String> estudantesEmails;
}
