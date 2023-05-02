package br.com.danielazevedo.escolaspring.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estudante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String rg;
	
	@Column(nullable = false)
	private String cpf;
	
	@Column(nullable = false)
	@Embedded
	private Endereco endereco;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private int idade;
	@Column(nullable = false, columnDefinition = "timestamp")
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "estudante_curso",
	joinColumns = @JoinColumn(name = "estudante_id"),
	inverseJoinColumns = @JoinColumn(name = "curso_id"))
	private Set<Curso> cursos = new HashSet<>();

	
}
