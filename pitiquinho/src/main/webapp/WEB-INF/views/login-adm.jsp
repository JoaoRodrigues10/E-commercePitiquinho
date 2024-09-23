<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/css/adm-login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <div class="login-container">
        <h3>Login BackOffice</h3>
        <form action="/adm/login-adm" method="post">
            <div class="input-container">
                <label for="email">E-mail:</label>
                <input type="text" id="email" name="email" required>
            </div>
            <div class="input-container">
                <label for="password">Senha:</label>
                <input type="password" id="password" name="password" required>
            </div>
            
            <div class="button-container">
                <button type="submit">OK</button>
            </div>

            <div>
                <% if (request.getParameter("error") != null) { %>
                    <p style="color: red;">Credenciais inválidas. Tente novamente.</p>
                <% } %>             
            </div>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
