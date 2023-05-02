package br.com.danielazevedo.escolaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danielazevedo.escolaspring.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
