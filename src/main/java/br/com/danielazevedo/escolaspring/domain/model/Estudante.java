package br.com.danielazevedo.escolaspring.domain.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estudante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	@Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "O email é obrigatório")
	@Email(message = "O email deve ser válido")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "O CPF é obrigatório")
	@Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres")
	@Column(nullable = false, unique = true)
	private String cpf;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "estudante_curso",
			joinColumns = @JoinColumn(name = "estudante_id"),
			inverseJoinColumns = @JoinColumn(name = "curso_id")
	)
	private Set<Curso> cursos = new HashSet<>();

	// Métodos utilitários para manipular o relacionamento
	public void adicionarCurso(Curso curso) {
		this.cursos.add(curso);
		curso.getEstudantes().add(this);
	}

	public void removerCurso(Curso curso) {
		this.cursos.remove(curso);
		curso.getEstudantes().remove(this);
	}
}