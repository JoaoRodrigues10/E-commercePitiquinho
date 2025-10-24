package br.loja.pitiquinho.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.loja.pitiquinho.model.Endereco;
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

        registrarUsuario("Cliente 1", "12345678901", "cliente1@example.com", "senha123", "Cliente",
                "01/01/1990", "Masculino", "12345-678", "Rua Exemplo 1", "100", "Apto 101",
                "Centro", "Cidade Exemplo", "SP", "11999999999");

        registrarUsuario("Cliente 2", "09876543210", "cliente2@example.com", "senha123", "Cliente",
                "02/02/1992", "Feminino", "87654-321", "Avenida Exemplo 2", "200", null,
                "Bairro Exemplo", "Cidade Exemplo 2", "RJ", "11888888888");

        registrarUsuario("Administrador", "12312312312", "admin@example.com", "admin123", "Adm",
                "01/01/1985", "Masculino", "00000-000", "Rua do Administrador", "101", "Sala 1",
                "Centro", "Admin City", "SP", "1100000000");

        registrarUsuario("Estoquista", "32132132132", "estoquista@example.com", "estoquista123", "Estoquista",
                "01/01/1990", "Feminino", "11111-111", "Avenida do Estoquista", "202", "Apto 202",
                "Bairro Estoquista", "Estoquista City", "RJ", "11777777777");
    }

        private void registrarUsuario(String nome, String cpf, String email, String senha, String grupo,
                                  String dataNascimento, String genero, String cepFaturamento,
                                  String logradouroFaturamento, String numeroFaturamento,
                                  String complementoFaturamento, String bairroFaturamento,
                                  String cidadeFaturamento, String ufFaturamento, String telefone) {

        if (usuarioRepository.findByEmail(email) == null) {
            Usuario usuario = criarUsuarioComEndereco(nome, cpf, email, senha, grupo, true,
                    dataNascimento, genero, cepFaturamento, logradouroFaturamento,
                    numeroFaturamento, complementoFaturamento, bairroFaturamento,
                    cidadeFaturamento, ufFaturamento, telefone);
            usuarioRepository.save(usuario);
        }
    }

        private Usuario criarUsuarioComEndereco(
            String nome, String cpf, String email, String senha, String grupo, boolean status,
            String dataNascimento, String genero, String cepFaturamento, String logradouroFaturamento,
            String numeroFaturamento, String complementoFaturamento, String bairroFaturamento,
            String cidadeFaturamento, String ufFaturamento, String telefone) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setGrupo(grupo);
        usuario.setStatus(status);
        usuario.setDataNascimento(dataNascimento);
        usuario.setGenero(genero);
        usuario.setTelefone(telefone); // Adicionado telefone
        usuarioRepository.save(usuario);

        Endereco endereco = new Endereco();
        endereco.setCep(cepFaturamento);
        endereco.setLogradouro(logradouroFaturamento);
        endereco.setNumero(numeroFaturamento);
        endereco.setComplemento(complementoFaturamento);
        endereco.setBairro(bairroFaturamento);
        endereco.setCidade(cidadeFaturamento);
        endereco.setUf(ufFaturamento);
        endereco.setTipoEndereco("Faturamento");

        endereco.setUsuarioId(usuario.getId());
        usuario.getEnderecosEntrega().add(endereco);

        return usuario;
    }

}
