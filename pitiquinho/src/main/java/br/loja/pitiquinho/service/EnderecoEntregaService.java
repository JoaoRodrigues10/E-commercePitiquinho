package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.EnderecoEntrega;
import br.loja.pitiquinho.repository.EnderecoEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoEntregaService {

    @Autowired
    private EnderecoEntregaRepository enderecoEntregaRepository;

    public List<EnderecoEntrega> buscarEnderecosPorUsuarioId(Long usuarioId) {
        return enderecoEntregaRepository.findByUsuarioId(usuarioId);
    }

    public EnderecoEntrega salvarEndereco(EnderecoEntrega enderecoEntrega) {
        return enderecoEntregaRepository.save(enderecoEntrega);
    }

    public void excluirEndereco(Long enderecoId) {
        enderecoEntregaRepository.deleteById(enderecoId);
    }
}
