package br.ufrn.imd.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ufrn.imd.market.dto.ClienteDTO;
import br.ufrn.imd.market.mapper.ClienteMapper;
import br.ufrn.imd.market.model.Cliente;
import br.ufrn.imd.market.repository.ClienteRepository;
import br.ufrn.imd.market.util.BusinessException;

@Service
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteDTO create(ClienteDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteSalvo);
    }

    public ClienteDTO findById(Long id) {
        Cliente clienteLocalizado = clienteRepository.findByIdAndIsAtivoTrue(id)
                .orElseThrow(() -> new BusinessException("Cliente com esse id não encontrado", HttpStatus.NOT_FOUND));

        return clienteMapper.toDTO(clienteLocalizado);
    }

    public ClienteDTO findByCpf(String cpf) {
        Cliente clienteLocalizado = clienteRepository.findByCpfAndIsAtivoTrue(cpf)
                .orElseThrow(() -> new BusinessException("Cliente com esse cpf não encontrado", HttpStatus.NOT_FOUND));

        return clienteMapper.toDTO(clienteLocalizado);
    }

    public List<ClienteDTO> findAll() {
        List<Cliente> clientesLocalizados = clienteRepository.findAllByIsAtivoTrue();

        return clientesLocalizados.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente clienteExistente = clienteRepository.findByIdAndIsAtivoTrue(id)
        .orElseThrow(() -> new BusinessException("Cliente não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        clienteExistente.setNome(dto.nome());
        clienteExistente.setCpf(dto.cpf());
        clienteExistente.setGenero(dto.genero());
        clienteExistente.setDataNascimento(dto.dataNascimento());

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

        return clienteMapper.toDTO(clienteAtualizado);
    }

    public void delete(Long id) {
        clienteRepository.findByIdAndIsAtivoTrue(id)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado com ID: " + id, HttpStatus.NOT_FOUND));

        clienteRepository.deleteById(id);
    }

    public void deleteLogical(Long id) {
        Cliente cliente = clienteRepository.findByIdAndIsAtivoTrue(id)
        .orElseThrow(() -> new BusinessException("Cliente não encontrado com ID: " + id, HttpStatus.NOT_FOUND));
        
        cliente.setIsAtivo(false);
        clienteRepository.save(cliente);
    }
}
