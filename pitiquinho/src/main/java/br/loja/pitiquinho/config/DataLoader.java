package br.loja.pitiquinho.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.repository.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injete o PasswordEncoder

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByEmail("admin@example.com") == null) {
            criarUsuario("adm", "12345678901", "admin@example.com", "1234", "Adm", true);
        }

        if (usuarioRepository.findByEmail("estoquista@example.com") == null) {
            criarUsuario("estoque", "09876543210", "estoquista@example.com", "1234", "Estoquista", true);
        }
    }

    private Usuario criarUsuario(String nome, String cpf, String email, String senha, String grupo, boolean status) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setGrupo(grupo);
        usuario.setStatus(status);
        return usuarioRepository.save(usuario);
    }
}
