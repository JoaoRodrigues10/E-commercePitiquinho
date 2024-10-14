package br.loja.pitiquinho.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.loja.pitiquinho.model.Cliente;
import br.loja.pitiquinho.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void salvarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public boolean emailExistente(String email) {
        return clienteRepository.existsByEmail(email);
    }

    public boolean cpfExistente(String cpf) {
        return clienteRepository.existsByCpf(cpf);
    }
}
