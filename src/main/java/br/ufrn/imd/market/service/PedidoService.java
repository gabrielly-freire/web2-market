package br.ufrn.imd.market.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ufrn.imd.market.dto.PedidoRequestDTO;
import br.ufrn.imd.market.dto.PedidoResponseDTO;
import br.ufrn.imd.market.mapper.PedidoMapper;
import br.ufrn.imd.market.model.Cliente;
import br.ufrn.imd.market.model.Pedido;
import br.ufrn.imd.market.model.Produto;
import br.ufrn.imd.market.repository.ClienteRepository;
import br.ufrn.imd.market.repository.PedidoRepository;
import br.ufrn.imd.market.repository.ProdutoRepository;
import br.ufrn.imd.market.util.BusinessException;

@Service
public class PedidoService {

        private final PedidoRepository pedidoRepository;
        private final PedidoMapper pedidoMapper;
        private final ProdutoRepository produtoRepository;
        private final ClienteRepository clienteRepository;

        public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper,
                        ProdutoRepository produtoRepository, ClienteRepository clienteRepository) {
                this.pedidoRepository = pedidoRepository;
                this.pedidoMapper = pedidoMapper;
                this.produtoRepository = produtoRepository;
                this.clienteRepository = clienteRepository;
        }

        public PedidoResponseDTO create(PedidoRequestDTO dto) {
                Cliente cliente = clienteRepository.findByIdAndIsAtivoTrue(dto.clienteId())
                                .orElseThrow(() -> new BusinessException(
                                                "Cliente não encontrado com ID: " + dto.clienteId(),
                                                HttpStatus.NOT_FOUND));

                List<Produto> produtosEncontrados = produtoRepository.findAllByIdInAndIsAtivoTrue(dto.produtosId());

                Set<Long> produtosIdSet = new HashSet<>(dto.produtosId());

                List<Long> produtosNaoEncontrados = produtosIdSet.stream()
                                .filter(id -> produtosEncontrados.stream()
                                                .noneMatch(produto -> produto.getId().equals(id)))
                                .collect(Collectors.toList());

                if (!produtosNaoEncontrados.isEmpty()) {
                        throw new BusinessException(
                                        "Os seguintes itens não foram encontrados: " + produtosNaoEncontrados,
                                        HttpStatus.NOT_FOUND);
                }

                Pedido pedido = pedidoMapper.toEntity(dto);
                pedido.setCliente(cliente);
                pedido.setProdutos(produtosEncontrados);

                Pedido pedidoSalvo = pedidoRepository.save(pedido);
                return pedidoMapper.toDTO(pedidoSalvo);
        }

        public PedidoResponseDTO findByIdAndIsAtivoTrue(Long id) {
                Pedido pedido = pedidoRepository.findByIdAndIsAtivoTrue(id)
                                .orElseThrow(() -> new BusinessException("Pedido não encontrado com ID: " + id,
                                                HttpStatus.NOT_FOUND));
                return pedidoMapper.toDTO(pedido);
        }

        public List<PedidoResponseDTO> findAll() {
                List<Pedido> pedidos = pedidoRepository.findAllByIsAtivoTrue();
                return pedidos.stream()
                                .map(pedidoMapper::toDTO)
                                .toList();
        }

        public PedidoResponseDTO update(Long id, PedidoRequestDTO dto) {
                Pedido pedido = pedidoRepository.findByIdAndIsAtivoTrue(id)
                                .orElseThrow(() -> new BusinessException("Pedido não encontrado com ID: " + id,
                                                HttpStatus.NOT_FOUND));

                Cliente cliente = clienteRepository.findByIdAndIsAtivoTrue(dto.clienteId())
                                .orElseThrow(() -> new BusinessException(
                                                "Cliente não encontrado com ID: " + dto.clienteId(),
                                                HttpStatus.NOT_FOUND));

                List<Produto> produtosEncontrados = produtoRepository.findAllByIdInAndIsAtivoTrue(dto.produtosId());

                Set<Long> produtosIdSet = new HashSet<>(dto.produtosId());
                List<Long> produtosNaoEncontrados = produtosIdSet.stream()
                                .filter(idProduto -> produtosEncontrados.stream()
                                                .noneMatch(produto -> produto.getId().equals(idProduto)))
                                .collect(Collectors.toList());

                if (!produtosNaoEncontrados.isEmpty()) {
                        throw new BusinessException(
                                        "Os seguintes itens não foram encontrados: " + produtosNaoEncontrados,
                                        HttpStatus.NOT_FOUND);
                }

                pedido.setCodigo(dto.codigo());
                pedido.setProdutos(produtosEncontrados);
                pedido.setCliente(cliente);

                Pedido pedidoSalvo = pedidoRepository.save(pedido);

                return pedidoMapper.toDTO(pedidoSalvo);
        }

        public void delete(Long id) {
                pedidoRepository.findByIdAndIsAtivoTrue(id)
                                .orElseThrow(() -> new BusinessException("Pedido não encontrado com ID: " + id,
                                                HttpStatus.NOT_FOUND));

                pedidoRepository.deleteById(id);
        }

        public void deleteLogical(Long id) {
                Pedido pedido = pedidoRepository.findByIdAndIsAtivoTrue(id)
                                .orElseThrow(() -> new BusinessException("Pedido não encontrado com ID: " + id,
                                                HttpStatus.NOT_FOUND));

                pedido.setIsAtivo(false);
                pedidoRepository.save(pedido);
        }

        public PedidoResponseDTO adicionarProduto(Long idPedido, Long idProduto) {
                Pedido pedido = pedidoRepository.findByIdAndIsAtivoTrue(idPedido)
                                .orElseThrow(
                                                () -> new BusinessException("Pedido não encontrado com ID: " + idPedido,
                                                                HttpStatus.NOT_FOUND));

                Produto produto = produtoRepository.findByIdAndIsAtivoTrue(idProduto)
                                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + idProduto,
                                                HttpStatus.NOT_FOUND));

                if (pedido.getProdutos().contains(produto)) {
                        throw new BusinessException("Produto já adicionado ao pedido", HttpStatus.BAD_REQUEST);
                }

                pedido.getProdutos().add(produto);

                Pedido pedidoSalvo = pedidoRepository.save(pedido);

                return pedidoMapper.toDTO(pedidoSalvo);
        }

        public PedidoResponseDTO removerProduto(Long idPedido, Long idProduto) {
                Pedido pedido = pedidoRepository.findByIdAndIsAtivoTrue(idPedido)
                                .orElseThrow(
                                                () -> new BusinessException("Pedido não encontrado com ID: " + idPedido,
                                                                HttpStatus.NOT_FOUND));

                Produto produto = produtoRepository.findByIdAndIsAtivoTrue(idProduto)
                                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + idProduto,
                                                HttpStatus.NOT_FOUND));

                if (!pedido.getProdutos().contains(produto)) {
                        throw new BusinessException("Produto não encontrado no pedido", HttpStatus.NOT_FOUND);
                }

                pedido.getProdutos().remove(produto);

                Pedido pedidoSalvo = pedidoRepository.save(pedido);

                return pedidoMapper.toDTO(pedidoSalvo);
        }

}
