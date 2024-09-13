<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.loja.pitiquinho.model.Usuario" %>
<% Usuario usuarioLogado = (Usuario) session.getAttribute("usuario"); %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Usuário</title>
    <link rel="stylesheet" type="text/css" href="/css/adm-lista-usuario.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.7/jquery.inputmask.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Listar Usuário</h1>

        <%
            if (usuarioLogado != null) {
                out.println(usuarioLogado.getNome());
            } else {
                out.println("<p>Nenhum usuário logado.</p>");
            }
        %>

        <%
            String errorMessage = (String) request.getAttribute("error");
            if (errorMessage != null) {
        %>
        <div class="alert alert-danger" role="alert">
            <%= errorMessage %>
        </div>
        <% } %>

        <div class="d-flex justify-content-between mb-3">
            <a href="/adm/adicionar-usuario" class="btn btn-success">+ Adicionar Usuário</a>
            <div class="search-container">

                <form action="/adm/lista-usuario" method="get" class="d-flex mb-3">
                        <input type="text" name="nome" value="${nome}" class="form-control" placeholder="Digite aqui...">
                        <button type="submit" class="btn btn-primary ms-2">Pesquisar</button>
               </form>

            </div>
        </div>

        <table class="table">
            <thead>
              <tr>
                <% boolean showIdColumn = false; %>
                <%
                    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
                    if (usuarios != null) {
                        for (Usuario usuario : usuarios) {
                            if (usuario.getStatus().equals("Desativado")) {
                                showIdColumn = true;
                                break;
                            }
                        }
                    }
                %>
                <% if (showIdColumn) { %>
                <th scope="col">ID</th>
                <% } %>
                <th scope="col">Nome</th>
                <th scope="col">E-mail</th>
                <th scope="col">CPF</th>
                <th scope="col">Status</th>
                <th scope="col">Grupo</th>
                <th scope="col">Alterar</th>
                <th scope="col">Ações</th>
              </tr>
            </thead>
            <tbody>
                <%
                    if (usuarios != null) {
                        for (Usuario usuario : usuarios) {
                %>
                <tr>
                    <% if (showIdColumn) { %>
                    <th scope="row"><%= usuario.getId() %></th>
                    <% } %>
                    <td><%= usuario.getNome() %></td>
                    <td><%= usuario.getEmail() %></td>
                    <td><%= usuario.getCpf() %></td>
                   <td>
                       <%= usuario.getStatus() ? "Ativado" : "Desativado" %>
                   </td>
                    <td><%= usuario.getGrupo() %></td>
                    <td>
                        <button type="button" class="btn btn-primary"
                            data-bs-toggle="modal"
                            data-bs-target="#staticBackdrop"
                            data-id="<%= usuario.getId() %>"
                            data-nome="<%= usuario.getNome() %>"
                            data-email="<%= usuario.getEmail() %>"
                            data-cpf="<%= usuario.getCpf() %>"
                            data-grupo="<%= usuario.getGrupo() %>">
                            Alterar
                        </button>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/adm/alterar-usuario/desativar" method="post" class="d-inline">
                            <input type="hidden" name="id" value="<%= usuario.getId() %>">
                            <input type="hidden" name="currentStatus" value="<%= usuario.getStatus() %>">
                            <button type="submit" class="btn btn-warning">
                                <%= usuario.getStatus() ? "Desativar" : "Ativar" %>
                            </button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="<%= showIdColumn ? 8 : 7 %>">Nenhum usuário encontrado.</td>
                </tr>
                <% } %>
            </tbody>
        </table>

        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Alterar Usuário</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                           <form id="modalForm" action="/adm/alterar-usuario" method="post">
                               <div class="mb-3">
                                   <label for="modalId" class="form-label">ID</label>
                                   <input type="text" class="form-control" id="modalId2" name="id2" disabled>
                                   <input type="hidden" class="form-control" id="modalId" name="id">
                               </div>

                                <div class="mb-3">
                                    <label for="modalNome" class="form-label">Nome</label>
                                    <input type="text" class="form-control" id="modalNome" name="nome" maxlength="100" required>
                                </div>

                                <div class="mb-3">
                                    <label for="modalEmail" class="form-label">E-mail</label>
                                    <input type="email" class="form-control" id="modalEmail2" name="email" disabled>
                                    <input type="hidden" class="form-control" id="modalEmail" name="email">
                                </div>
                                <div class="mb-3">
                                    <label for="modalCpf" class="form-label">CPF</label>
                                    <input type="text" class="form-control" id="modalCpf" name="cpf" required>
                                </div>

                                  <div class="mb-3">
                                        <label for="modalGrupo" class="form-label">Grupo</label>
                                        <input type="text" class="form-control" id="modalGrupo" name="grupo" maxlength="20">
                                  </div>

                                <div class="mb-3">
                                    <label for="modalSenha" class="form-label">Nova Senha</label>
                                    <input type="password" class="form-control" id="modalSenha" name="senha" minlength="8">
                                </div>

                                <div class="mb-3">
                                    <label for="modalConfirmarSenha" class="form-label">Confirmar Senha</label>
                                    <input type="password" class="form-control" id="modalConfirmarSenha" name="confirmarSenha" minlength="8">
                                </div>
                                <button type="submit" class="btn btn-primary">Salvar</button>
                            </form>


                            <script>
                                function confirmarAlteracao() {
                                    return confirm("Tem certeza que deseja salvar as alterações?");
                                }
                            </script>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function(){
            $('#modalCpf').inputmask('999.999.999-99');

            // Modal para edição de usuário
            var modal = document.getElementById('staticBackdrop');
            modal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget;
                var userId = button.getAttribute('data-id');
                var userNome = button.getAttribute('data-nome');
                var userEmail = button.getAttribute('data-email');
                var userCpf = button.getAttribute('data-cpf');
                var userGrupo = button.getAttribute('data-grupo');

                var modalId2 = modal.querySelector('#modalId2');
                var modalId = modal.querySelector('#modalId');
                var modalNome = modal.querySelector('#modalNome');
                var modalEmail2 = modal.querySelector('#modalEmail2');
                var modalEmail = modal.querySelector('#modalEmail');
                var modalCpf = modal.querySelector('#modalCpf');
                var modalGrupo = modal.querySelector('#modalGrupo');

                modalId2.value = userId;
                modalId.value = userId;
                modalNome.value = userNome;
                modalEmail2.value = userEmail;
                modalEmail.value = userEmail;
                modalCpf.value = userCpf;
                modalGrupo.value = userGrupo;
            });
        });
    </script>


</body>
</html>
