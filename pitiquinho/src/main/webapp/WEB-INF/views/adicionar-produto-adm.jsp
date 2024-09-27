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
    <link href="https://cdn.jsdelivr.net/npm/inputmask@5.0.6/dist/inputmask.min.css" rel="stylesheet">
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

        <form action="/adicionar-produto" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome do Produto</label>
                <input type="text" class="form-control" id="nome" name="nome" required maxlength="200">
            </div>

            <div class="mb-3">
                <label for="avaliacao" class="form-label">Avaliação</label>
                <select class="form-control" id="avaliacao" name="avaliacao" required>
                    <option value="1.0">1.0</option>
                    <option value="1.5">1.5</option>
                    <option value="2.0">2.0</option>
                    <option value="2.5">2.5</option>
                    <option value="3.0">3.0</option>
                    <option value="3.5">3.5</option>
                    <option value="4.0">4.0</option>
                    <option value="4.5">4.5</option>
                    <option value="5.0">5.0</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="descricao" class="form-label">Descrição Detalhada</label>
                <textarea class="form-control" id="descricao" name="descricao" rows="5" maxlength="2000"></textarea>
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
                <label for="imagem" class="form-label">Imagem do Produto</label>
                <input type="file" class="form-control" id="imagem" name="imagem" accept="image/*" required>
            </div>



            <button type="submit" class="btn btn-primary">Adicionar Produto</button>
        </form>

        <a href="/lista-produto" class="btn btn-secondary mt-3">Voltar</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/inputmask@5.0.6/dist/inputmask.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', (event) => {

            Inputmask('currency', {
                rightAlign: false,
                prefix: 'R$ ',
                groupSeparator: '.',
                radixPoint: ',',
                autoGroup: true,
                removeMaskOnSubmit: true // Remove a máscara antes do envio
            }).mask('#preco');

            document.querySelector('form').addEventListener('submit', function(event) {
                var precoInput = document.getElementById('preco');
                var precoValue = precoInput.value;

                precoValue = precoValue.replace('R$ ', '').replace(/\./g, '').replace(',', '.');


                precoInput.value = precoValue;
            });
        });
    </script>

</body>
</html>
