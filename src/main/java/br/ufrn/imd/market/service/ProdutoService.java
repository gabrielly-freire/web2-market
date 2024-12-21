package br.ufrn.imd.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ufrn.imd.market.dto.ProdutoDTO;
import br.ufrn.imd.market.mapper.ProdutoMapper;
import br.ufrn.imd.market.model.Produto;
import br.ufrn.imd.market.repository.ProdutoRepository;
import br.ufrn.imd.market.util.BusinessException;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoDTO create(ProdutoDTO dto) {
        Produto produto = produtoMapper.toEntity(dto);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(produtoSalvo);
    }

    public ProdutoDTO findById(Long id) {
        Produto produtoLocalizado = produtoRepository.findByIdAndIsAtivoTrue(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        return produtoMapper.toDTO(produtoLocalizado);
    }

    public List<ProdutoDTO> findAll() {
        List<Produto> produtosLocalizados = produtoRepository.findAllByIsAtivoTrue();
       
        return produtosLocalizados.stream()
            .map(produtoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        Produto produtoExistente = produtoRepository.findByIdAndIsAtivoTrue(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        produtoExistente.setNome(dto.nome());
        produtoExistente.setMarca(dto.marca());
        produtoExistente.setDataFabricacao(dto.dataFabricacao());
        produtoExistente.setDataValidade(dto.dataValidade());
        produtoExistente.setCategoria(dto.categoria());
        produtoExistente.setPreco(dto.preco());
        produtoExistente.setLote(dto.lote());

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);

        return produtoMapper.toDTO(produtoAtualizado);
    }

    public void delete(Long id) {
        produtoRepository.findByIdAndIsAtivoTrue(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        produtoRepository.deleteById(id);
    }

    public void deleteLogical(Long id) {
        Produto produtoExistente = produtoRepository.findByIdAndIsAtivoTrue(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        produtoExistente.setIsAtivo(false);

        produtoRepository.save(produtoExistente);
    }
}
