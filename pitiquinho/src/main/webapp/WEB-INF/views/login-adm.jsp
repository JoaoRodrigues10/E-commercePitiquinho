<!DOCTYPE html>
<html lang="pt-BR" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pitiquinho - Login</title>
    <link rel="stylesheet" href="/css/adm-login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="login-container shadow">

    <h2 class="project-title">🧸 Pitiquinho</h2>
    <p class="subtitle">Bem-vindo! Faça login para continuar</p>

    <form th:action="@{/login}" method="post">
        <input type="hidden" name="redirect" th:value="${redirect}" />

        <div class="input-container">
            <label for="email">E-mail</label>
            <input type="text" id="email" name="email" placeholder="Digite seu e-mail" required>
        </div>

        <div class="input-container">
            <label for="password">Senha</label>
            <input type="password" id="password" name="password" placeholder="Digite sua senha" required>
        </div>

        <button type="submit" class="btn-login">Entrar</button>

        <p th:if="${param.error}" class="error-msg">Credenciais inválidas. Tente novamente.</p>
    </form>

    <a href="/usuario/cadastro" class="btn-create">Criar Conta</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
