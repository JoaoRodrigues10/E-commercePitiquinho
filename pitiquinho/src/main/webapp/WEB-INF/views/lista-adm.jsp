<%@ page session="true" %>
<%@ page import="br.loja.pitiquinho.model.Usuario" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/lista-telas.css">
    <title>Administração</title>
</head>
<body>
    <div class="container">
        <h2>Área de Administração</h2>

        <%
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario != null) {
                out.println("<p>Bem-vindo, " + usuario.getNome() + "!</p>");
            } else {
                out.println("<p>Nenhum usuário logado.</p>");
            }
        %>

        <ul>
            <li><a href="/listar-produto">Listar Produto</a></li>
            <li><a href="/listar-usuario">Listar Usuário</a></li>
            <li><span class="disabled">Listar Pedidos</span></li>
        </ul>
    </div>

</body>
</html>
