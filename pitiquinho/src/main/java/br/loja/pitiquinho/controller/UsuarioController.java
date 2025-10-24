package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.repository.UsuarioRepository;
import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.util.util;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private util util;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ================= CADASTRO CLIENTE =================
    @GetMapping("/cadastro")
    public String abrirCadastro(Model model) {
        model.addAttribute("novoUsuario", new Usuario());
        return "cadastro-usuario";
    }



    @PostMapping("/cadastro")
    public String cadastrarUsuario(@Valid @ModelAttribute("novoUsuario") Usuario usuario,
                                   BindingResult result, Model model, HttpSession session) {

        System.out.println("=== Entrou no POST /cadastro ===");
        System.out.println("Objeto usuario recebido: " + usuario);

        // validação extra caso o objeto ainda venha nulo
        if (usuario == null) {
            System.out.println("Objeto usuario é nulo!");
            model.addAttribute("erro", "Dados do usuário não foram enviados.");
            return "cadastro-usuario";
        }

        System.out.println("Email do usuário: " + usuario.getEmail());
        System.out.println("Nome do usuário: " + usuario.getNome());
        System.out.println("CPF do usuário: " + usuario.getCpf());

        // validações padrão
        if (result.hasErrors() || !validarCadastro(usuario, result)) {
            System.out.println("Erros de validação encontrados:");
            result.getAllErrors().forEach(erro -> System.out.println(erro.toString()));
            return "cadastro-usuario";
        }

        // hash da senha
        System.out.println("Senha antes do hash: " + usuario.getSenha());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        System.out.println("Senha após hash: " + usuario.getSenha());

        // define grupo padrão
        usuario.setGrupo("Cliente");

        // salva no banco
        usuarioRepository.save(usuario);
        System.out.println("Usuário salvo no banco com ID: " + usuario.getId());

        // remove senha antes de colocar na sessão
        usuario.setSenha(null);
        session.setAttribute("usuario", usuario);
        System.out.println("Usuário colocado na sessão: " + session.getAttribute("usuario"));

        // redireciona para cadastro de endereço
        return "redirect:/";
    }




    // ================= EDIÇÃO USUÁRIO =================
    @GetMapping("/editar")
    public String mostrarFormularioEdicao(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        // Busca o usuário pelo ID da sessão, garantindo que ele existe no banco
        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                .orElse(usuarioLogado);

        model.addAttribute("usuario", usuario);
        return "editar-usuario";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario,
                                BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors() || !validarEdicao(usuario, id, result)) {
            model.addAttribute("usuario", usuario);
            return "editar-usuario";
        }

        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow();
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setCpf(usuario.getCpf().replaceAll("[^\\d]", ""));
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setGrupo(usuarioExistente.getGrupo());

        if (!usuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        usuarioRepository.save(usuarioExistente);
        session.setAttribute("usuario", usuarioExistente);

        return "redirect:/usuario/editar/" + id;
    }

    // ================= LISTAGEM USUÁRIOS =================
    @GetMapping("/lista")
    public String listarUsuarios(@RequestParam(value = "nome", defaultValue = "") String nome,
                                 Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        if (usuarioLogado == null) return "redirect:/login";

        List<Usuario> usuarios = nome.isEmpty() ?
                usuarioService.listarTodosUsuarios() :
                usuarioService.buscarUsuariosPorNome(nome);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuario", usuarioLogado);
        model.addAttribute("nome", nome);
        boolean algumDesativado = usuarios.stream().anyMatch(u -> "Desativado".equals(u.getStatus()));
        model.addAttribute("algumDesativado", algumDesativado);

        return "lista-usuario";
    }

    // ================= ADICIONAR USUÁRIO =================
    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionarUsuario(@RequestParam(value = "error", required = false) String error,
                                                    @RequestParam(value = "success", required = false) String success,
                                                    Model model) {
        if (error != null) model.addAttribute("errorMessage", error);
        if (success != null) model.addAttribute("successMessage", success);
        model.addAttribute("usuario", new Usuario());
        return "adicionar-usuario";
    }

    @PostMapping("/adicionar")
    public String adicionarUsuario(@RequestParam String nome, @RequestParam String email,
                                   @RequestParam String cpf, @RequestParam String grupo,
                                   @RequestParam String senha, @RequestParam String confirmarSenha,
                                   RedirectAttributes redirectAttributes) {

        if (usuarioRepository.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "O e-mail já está em uso");
            return "redirect:/usuario/adicionar";
        }
        if (!senha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("errorMessage", "As senhas não correspondem");
            return "redirect:/usuario/adicionar";
        }
        if (!util.validarCPF(cpf)) {
            redirectAttributes.addFlashAttribute("errorMessage", "CPF inválido");
            return "redirect:/usuario/adicionar";
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setCpf(cpf.replaceAll("[^\\d]", ""));
        usuario.setGrupo(grupo);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setStatus(true);

        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("success", "Usuário adicionado com sucesso");
        return "redirect:/usuario/lista";
    }

    // ================= DESATIVAR/ATIVAR =================
    @PostMapping("/status")
    public String alterarStatus(@RequestParam Long id, @RequestParam boolean currentStatus,
                                RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setStatus(!currentStatus);
        usuarioRepository.save(usuario);

        String msg = currentStatus ? "Usuário desativado com sucesso" : "Usuário ativado com sucesso";
        redirectAttributes.addFlashAttribute("success", msg);

        return "redirect:/usuario/lista";
    }

    // ================= VALIDAÇÕES =================
    private boolean validarCadastro(Usuario usuario, BindingResult result) {

        System.out.println("teste");
        System.out.println(usuario.getEmail());
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            result.rejectValue("email", "error.usuario", "Email já cadastrado");
            return false;
        }
        if (usuarioRepository.findByCpf(usuario.getCpf()) != null) {
            result.rejectValue("cpf", "error.usuario", "CPF já cadastrado");
            return false;
        }
        if (!util.validarCPF(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");
            return false;
        }
        return true;
    }

    private boolean validarEdicao(Usuario usuario, Long id, BindingResult result) {
        Usuario existenteCpf = usuarioRepository.findByCpf(usuario.getCpf());
        if (existenteCpf != null && !existenteCpf.getId().equals(id)) {
            result.rejectValue("cpf", "error.usuario", "CPF já cadastrado");
            return false;
        }
        if (!util.validarCPF(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");
            return false;
        }
        return true;
    }
}
