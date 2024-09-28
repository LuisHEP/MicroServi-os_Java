package com.exemplo.cliente.controller;

import com.exemplo.cliente.model.Cliente;
import com.exemplo.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteRepository.findById(id)
                .map(registroExistente -> {
                    registroExistente.setNome(cliente.getNome());
                    registroExistente.setCpf(cliente.getCpf());
                    registroExistente.setEmail(cliente.getEmail());
                    registroExistente.setTelefone(cliente.getTelefone());
                    Cliente atualizado = clienteRepository.save(registroExistente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}
