<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.loja.pitiquinho.model.Usuario" %>

<% Usuario usuarioLogado = (Usuario) session.getAttribute("usuario"); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/lista-telas.css">
    <link rel="stylesheet" type="text/css" href="/css/lista.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <title>Administração</title>
</head>
<body>
    <div class="container">

         <div class="button-container">

                <%
                   if (usuarioLogado != null) {
                       out.println("<p>Bem-vindo, " + usuarioLogado.getNome() + "!</p>");
                   } else {
                       out.println("<p>Nenhum usuário logado.</p>");
                   }
                %>

                <form action="/logout" method="post">
                    <button type="submit">Logout</button>
                </form>

        </div>

        <h2>Área de Administração</h2>

        <ul>
            <li><a href="/lista-produto">Listar Produto</a></li>
            <li>
                <%
                    if (usuarioLogado != null && "Adm".equals(usuarioLogado.getGrupo())) {
                %>
                <a href="/lista-usuario">Listar Usuário</a>
                <%
                    } else {
                %>
                <span class="disabled">Listar Usuário</span>
                <%
                    }
                %>
            </li>
            <li><span class="disabled">Listar Pedidos</span></li>
        </ul>
    </div>
</body>
</html>
