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
        if (usuarioRepository.findByEmail("admin@example.com") == null) {
            criarUsuario(
                    "adm",
                    "12345678901",
                    "admin@example.com",
                    "1234",
                    "Adm",
                    true,
                    "01/01/1980",
                    "Masculino",
                    "12345-678",
                    "Rua Exemplo",
                    "123",
                    "Apto 101",
                    "Centro",
                    "São Paulo",
                    "SP",
                    "11987654321" // Telefone
            );
        }

        if (usuarioRepository.findByEmail("estoquista@example.com") == null) {
            criarUsuario(
                    "estoque",
                    "09876543210",
                    "estoquista@example.com",
                    "1234",
                    "Estoquista",
                    true,
                    "02/02/1990",
                    "Feminino",
                    "87654-321",
                    "Avenida Exemplo",
                    "456",
                    null,
                    "Bairro Exemplo",
                    "Rio de Janeiro",
                    "RJ",
                    "21987654321" // Telefone
            );
        }
    }

    private Usuario criarUsuario(
            String nome, String cpf, String email, String senha, String grupo, boolean status,
            String dataNascimento, String genero, String cepFaturamento, String logradouroFaturamento,
            String numeroFaturamento, String complementoFaturamento, String bairroFaturamento,
            String cidadeFaturamento, String ufFaturamento, String telefone) { // Adicionando telefone

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setGrupo(grupo);
        usuario.setStatus(status);
        usuario.setDataNascimento(dataNascimento);
        usuario.setGenero(genero);
        usuario.setCepFaturamento(cepFaturamento);
        usuario.setLogradouroFaturamento(logradouroFaturamento);
        usuario.setNumeroFaturamento(numeroFaturamento);
        usuario.setComplementoFaturamento(complementoFaturamento);
        usuario.setBairroFaturamento(bairroFaturamento);
        usuario.setCidadeFaturamento(cidadeFaturamento);
        usuario.setUfFaturamento(ufFaturamento);
        usuario.setTelefone(telefone); // Definindo telefone

        return usuarioRepository.save(usuario);
    }
}
