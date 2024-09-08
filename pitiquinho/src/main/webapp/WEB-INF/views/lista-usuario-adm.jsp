<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="br.loja.pitiquinho.model.Usuario" %>
<%@ page session="true" %>


<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Usuário</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ListarUsuario.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h1>Listar Usuário</h1>
        <div class="search-container">
            <input type="text" placeholder="Digite aqui...">
            <button>Pesquisar</button>
        </div>

        <table class="table">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Nome</th>
                <th scope="col">E-mail</th>
                <th scope="col">Status</th>
                <th scope="col">Grupo</th>
                <th scope="col">Alterar</th>
              </tr>
            </thead>
            <tbody>
                <c:forEach var="usuario" items="${usuarios}">
                    <tr>
                        <th scope="row">${usuario.getId()}</th>
                        <td>${usuario.getNome()}</td>
                        <td>${usuario.getEmail()}</td>
                        <td>${usuario.getStatus()}</td>
                        <td>${usuario.getGrupo()}</td>
                        <td>
                            <button type="button" class="btn btn-primary"
                                data-bs-toggle="modal"
                                data-bs-target="#staticBackdrop"
                                data-id="${usuario.getId()}"
                                data-nome="${usuario.getNome()}"
                                data-email="${usuario.getEmail()}"
                                data-status="${usuario.getStatus()}"
                                data-grupo="${usuario.getGrupo()}">
                                Alterar
                            </button>
                        </td>
                    </tr>
                </c:forEach>
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
                        <form id="modalForm">
                            <div class="mb-3">
                                <label for="modalId" class="form-label">ID</label>
                                <input type="text" class="form-control" id="modalId" disabled>
                            </div>
                            <div class="mb-3">
                                <label for="modalNome" class="form-label">Nome</label>
                                <input type="text" class="form-control" id="modalNome">
                            </div>
                            <div class="mb-3">
                                <label for="modalEmail" class="form-label">E-mail</label>
                                <input type="email" class="form-control" id="modalEmail">
                            </div>
                            <div class="mb-3">
                                <label for="modalStatus" class="form-label">Status</label>
                                <input type="text" class="form-control" id="modalStatus">
                            </div>
                            <div class="mb-3">
                                <label for="modalGrupo" class="form-label">Grupo</label>
                                <input type="text" class="form-control" id="modalGrupo">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary">Ok</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var modal = document.getElementById('staticBackdrop');
            modal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget; // Button that triggered the modal
                var userId = button.getAttribute('data-id');
                var userNome = button.getAttribute('data-nome');
                var userEmail = button.getAttribute('data-email');
                var userStatus = button.getAttribute('data-status');
                var userGrupo = button.getAttribute('data-grupo');

                document.querySelector('#modalId').value = userId;
                document.querySelector('#modalNome').value = userNome;
                document.querySelector('#modalEmail').value = userEmail;
                document.querySelector('#modalStatus').value = userStatus;
                document.querySelector('#modalGrupo').value = userGrupo;
            });
        });
    </script>
</body>
</html>
