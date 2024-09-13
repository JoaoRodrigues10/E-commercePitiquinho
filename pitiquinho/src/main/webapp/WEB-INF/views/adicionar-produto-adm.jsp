<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.loja.pitiquinho.model.Produto" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adicionar Produto</title>
    <link rel="stylesheet" type="text/css" href="/css/adm-lista-produto.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h1>Adicionar Novo Produto</h1>

        <%
            String errorMessage = (String) request.getAttribute("error");
            if (errorMessage != null) {
        %>
        <div class="alert alert-danger" role="alert">
            <%= errorMessage %>
        </div>
        <% } %>

        <form action="/adm/adicionar-produto" method="post">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome do Produto</label>
                <input type="text" class="form-control" id="nome" name="nome" required maxlength="100">
            </div>

            <div class="mb-3">
                <label for="descricao" class="form-label">Descrição</label>
                <textarea class="form-control" id="descricao" name="descricao" rows="3" maxlength="500"></textarea>
            </div>

            <div class="mb-3">
                <label for="preco" class="form-label">Preço</label>
                <input type="text" class="form-control" id="preco" name="preco" required>
            </div>

            <div class="mb-3">
                <label for="quantidadeEmEstoque" class="form-label">Quantidade em Estoque</label>
                <input type="number" class="form-control" id="quantidadeEmEstoque" name="quantidadeEmEstoque" required>
            </div>

            <div class="mb-3">
                <label for="categoria" class="form-label">Categoria</label>
                <input type="text" class="form-control" id="categoria" name="categoria" maxlength="50">
            </div>

            <div class="mb-3">
                <label for="ativo" class="form-label">Produto Ativo?</label>
                <select class="form-control" id="ativo" name="ativo" required>
                    <option value="true">Sim</option>
                    <option value="false">Não</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Adicionar Produto</button>
        </form>

        <a href="/adm/lista-produto" class="btn btn-secondary mt-3">Voltar</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function(){
            $('#preco').inputmask('currency', { rightAlign: false, prefix: 'R$ ', groupSeparator: '.', radixPoint: ',', autoGroup: true });
        });
    </script>
</body>
</html>
