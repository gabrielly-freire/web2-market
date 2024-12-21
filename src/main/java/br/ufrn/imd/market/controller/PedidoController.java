package br.ufrn.imd.market.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.market.dto.PedidoRequestDTO;
import br.ufrn.imd.market.dto.PedidoResponseDTO;
import br.ufrn.imd.market.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pedidos")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Cria um pedido", description = "Cria um pedido com base nos dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@Valid @RequestBody PedidoRequestDTO dto){
        PedidoResponseDTO createdPedido = pedidoService.create(dto);
        return ResponseEntity.status(201).body(createdPedido);
    }

    @Operation(summary = "Busca um pedido pelo ID", description = "Busca um pedido com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "400", description = "Erro de validação"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Long id){
        PedidoResponseDTO pedido = pedidoService.findByIdAndIsAtivoTrue(id);
        return ResponseEntity.ok(pedido);
    }

    @Operation(summary = "Busca todos os pedidos", description = "Busca todos os pedidos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll(){
        List<PedidoResponseDTO> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Atualiza um pedido", description = "Atualiza um pedido com base nos dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO updatedPedido = pedidoService.update(id, dto);
        return ResponseEntity.ok(updatedPedido);
    }

    @Operation(summary = "Deleta um pedido", description = "Deleta um pedido com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta logicamente um pedido", description = "Deleta logicamente um pedido com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
        pedidoService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um produto ao pedido", description = "Adiciona um produto ao pedido com base nos IDs fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto adicionado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido ou produto não encontrado")
    })
    @PutMapping("/{idPedido}/add/{idProduto}")
    public ResponseEntity<PedidoResponseDTO> addProduto(@PathVariable Long idPedido, @PathVariable Long idProduto) {
        PedidoResponseDTO pedidoDTO = pedidoService.adicionarProduto(idPedido, idProduto);
        return ResponseEntity.ok(pedidoDTO);
    }

    @Operation(summary = "Remove um produto do pedido", description = "Remove um produto do pedido com base nos IDs fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido ou produto não encontrado")
    })
    @PutMapping("/{idPedido}/remove/{idProduto}")
    public ResponseEntity<PedidoResponseDTO> removeProduto(@PathVariable Long idPedido, @PathVariable Long idProduto) {
        PedidoResponseDTO pedidoDTO = pedidoService.removerProduto(idPedido, idProduto);
        return ResponseEntity.ok(pedidoDTO);
    }
}
