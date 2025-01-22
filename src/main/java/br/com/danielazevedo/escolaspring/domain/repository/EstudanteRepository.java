package br.com.danielazevedo.escolaspring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danielazevedo.escolaspring.domain.model.Estudante;
import java.util.List;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    List<Estudante> findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(String nome, String email);
}
