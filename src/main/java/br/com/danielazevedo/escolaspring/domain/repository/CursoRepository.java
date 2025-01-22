package br.com.danielazevedo.escolaspring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danielazevedo.escolaspring.domain.model.Curso;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByNomeContainingIgnoreCase(String nome);
}
