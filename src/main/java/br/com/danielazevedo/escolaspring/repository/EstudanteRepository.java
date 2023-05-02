package br.com.danielazevedo.escolaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danielazevedo.escolaspring.model.Estudante;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

}
