package br.ufrn.imd.market.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.imd.market.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Optional<Produto> findByIdAndIsAtivoTrue(Long id);

    List<Produto> findAllByIsAtivoTrue();

    List<Produto> findAllByIdInAndIsAtivoTrue(List<Long> ids);
}
