package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> buscarEnderecosPorUsuarioId(Long usuarioId) {
        return enderecoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public void desmarcarEnderecoPadraoDoUsuario(Long usuarioId, Long enderecoAtualId) {
        enderecoRepository.findByUsuarioId(usuarioId).forEach(e -> {
            if (e.isPadrao() && !e.getId().equals(enderecoAtualId)) {
                e.setPadrao(false);
                enderecoRepository.save(e);
            }
        });
    }

    @Transactional
    public void definirPadrao(Long enderecoId, Long usuarioId) {
        // Desmarca endereço padrão atual
        desmarcarEnderecoPadraoDoUsuario(usuarioId, enderecoId);

        // Define o novo endereço como padrão
        Endereco endereco = buscarEnderecoPorId(enderecoId);
        endereco.setPadrao(true);
        enderecoRepository.save(endereco);
    }

    @Transactional
    public Endereco salvarEndereco(Endereco endereco) {
        // Se o endereço for marcado como padrão, desmarca os outros endereços do mesmo usuário
        if (endereco.isPadrao()) {
            desmarcarEnderecoPadraoDoUsuario(endereco.getUsuarioId(), endereco.getId());
        }
        return enderecoRepository.save(endereco);
    }


    public void excluirEndereco(Long enderecoId) {
        if (!enderecoRepository.existsById(enderecoId)) {
            throw new NoSuchElementException("Endereço com ID " + enderecoId + " não encontrado para exclusão.");
        }
        enderecoRepository.deleteById(enderecoId);
    }

    public Endereco buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereço com ID " + id + " não encontrado."));
    }

    public Endereco buscarEnderecoPadrao(Long usuarioId) {
        Endereco endereco = enderecoRepository.findFirstByUsuarioIdAndPadraoTrue(usuarioId);
        return endereco;
    }


}

