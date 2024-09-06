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
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.findByEmail("adm") == null) {

            Usuario admin = new Usuario();
            admin.setNome("adm");
            admin.setCpf("12345678901");
            admin.setEmail("admin@example.com");
            admin.setSenha(passwordEncoder.encode("1234")); 
            admin.setGrupo("Adm");
            admin.setStatus(true); 

            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByEmail("estoque") == null) {
          
            Usuario estoquista = new Usuario();
            estoquista.setNome("estoque");
            estoquista.setCpf("09876543210");
            estoquista.setEmail("estoquista@example.com");
            estoquista.setSenha(passwordEncoder.encode("1234"));
            estoquista.setGrupo("Estoquista"); 
            estoquista.setStatus(true); 

            usuarioRepository.save(estoquista);
        }
    }
}
