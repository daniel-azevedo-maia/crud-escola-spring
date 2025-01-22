package br.com.danielazevedo.escolaspring.domain.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome do curso é obrigatório")
	@Size(min = 2, max = 100, message = "O nome do curso deve ter entre 2 e 100 caracteres")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "A descrição do curso é obrigatória")
	@Size(max = 500, message = "A descrição do curso não pode ultrapassar 500 caracteres")
	@Column(nullable = false)
	private String descricao;

	@ManyToMany(mappedBy = "cursos", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Estudante> estudantes = new HashSet<>();

	// Métodos utilitários para manipular o relacionamento
	public void adicionarEstudante(Estudante estudante) {
		this.estudantes.add(estudante);
		estudante.getCursos().add(this);
	}

	public void removerEstudante(Estudante estudante) {
		this.estudantes.remove(estudante);
		estudante.getCursos().remove(this);
	}
}
