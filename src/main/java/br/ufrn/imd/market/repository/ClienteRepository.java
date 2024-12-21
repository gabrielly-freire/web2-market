package br.ufrn.imd.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.market.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByIdAndIsAtivoTrue(Long id);

    Optional<Cliente> findByCpfAndIsAtivoTrue(String cpf);

    List<Cliente> findAllByIsAtivoTrue();

    Boolean existsByCpf(String cpf);
    
}
