package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> buscarEnderecosPorUsuarioId(Long usuarioId) {
        return enderecoRepository.findByUsuarioId(usuarioId);
    }

    public List<Endereco> buscarEnderecosPorUsuarioIdEntrega(Long usuarioId) {
        return enderecoRepository.findByUsuarioIdAndTipoEndereco(usuarioId, "Entrega");
    }

    public Endereco salvarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public void excluirEndereco(Long enderecoId) {
        try {
            enderecoRepository.deleteById(enderecoId);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Endereço com ID " + enderecoId + " não encontrado para exclusão.");
        }
    }

    public Endereco findById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereço com ID " + id + " não encontrado."));
    }

    public Endereco buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereço com ID " + id + " não encontrado."));
    }

}
