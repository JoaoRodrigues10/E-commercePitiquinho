<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.loja.pitiquinho.model.Usuario" %>

<%@ page import="java.util.List" %>
<%@ page import="br.loja.pitiquinho.model.Produto" %>
<% Produto produtoSelecionado = (Produto) session.getAttribute("produto"); %>

<% Usuario usuarioLogado = (Usuario) session.getAttribute("usuario"); %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Produtos</title>
    <link rel="stylesheet" type="text/css" href="/css/lista-produto-adm.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="btn-group">
            <a href="/adm/home" class="btn btn-primary mr-2">Voltar</a>
        </div>
        <h1>Listar Produtos</h1>

        <div class="d-flex justify-content-between mb-3">
            <a href="/adm/adicionar-produto" class="btn btn-success">+ Adicionar Produto</a>
            <div class="search-container">
                <form action="/adm/lista-produto" method="get" class="d-flex">
                    <input type="text" name="nome" class="form-control" placeholder="Buscar produto..." value="<%= request.getParameter("nome") != null ? request.getParameter("nome") : "" %>">
                    <button type="submit" class="btn btn-primary ms-2">Pesquisar</button>
                </form>
            </div>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Preço</th>
                    <th scope="col">Quantidade</th>
                    <th scope="col">Categoria</th>
                    <th scope="col">Status</th>
                    <th scope="col">Alterar</th>
                    <th scope="col">Visualizar</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Produto> produtos = (List<Produto>) request.getAttribute("produtos");
                    if (produtos != null) {
                        for (Produto produto : produtos) {
                %>
                <tr>
                    <th scope="row"><%= produto.getId() %></th>
                    <td><%= produto.getNome() %></td>
                    <td>R$ <%= produto.getPreco() %></td>
                    <td><%= produto.getQuantidadeEmEstoque() %></td>
                    <td><%= produto.getCategoria() %></td>
                    <td><%= produto.getAtivo() ? "Ativado" : "Desativado" %></td>

                    <td>
                        <button type="button" class="btn btn-primary"
                            data-bs-toggle="modal"
                            data-bs-target="#modalProduto"
                            data-id="<%= produto.getId() %>"
                            data-nome="<%= produto.getNome() %>"
                            data-descricao="<%= produto.getDescricao() %>"
                            data-preco="<%= produto.getPreco() %>"
                            data-quantidade="<%= produto.getQuantidadeEmEstoque() %>"
                            data-categoria="<%= produto.getCategoria() %>">
                            Alterar
                        </button>
                    </td>

                    <td>
                        <button type="button" class="btn btn-primary"
                                                     data-bs-toggle="modal"
                                                     data-bs-target="#modalVisualizarProduto"
                                                     data-id="<%= produto.getId() %>"
                                                     data-nome="<%= produto.getNome() %>"
                                                     data-descricao="<%= produto.getDescricao() %>"
                                                     data-preco="<%= produto.getPreco() %>"
                                                     data-quantidade="<%= produto.getQuantidadeEmEstoque() %>"
                                                     data-categoria="<%= produto.getCategoria() %>"
                                                     data-imagem="<%= produto.getImagem() %>"
                                                     >
                                                     Visualizar
                        </button>


                    </td>

                    <td>

                       <form action="${pageContext.request.contextPath}/adm/alterar-produto/desativar" method="post" class="d-inline" onsubmit="return confirmarAcao();">
                           <input type="hidden" name="id" value="<%= produto.getId() %>">
                           <input type="hidden" name="currentStatus" value="<%= produto.getAtivo() %>">
                           <button type="submit" class="btn btn-warning">
                               <%= produto.getAtivo() ? "Desativar" : "Ativar" %>
                           </button>
                       </form>

                       <script>
                           function confirmarAcao() {
                               var status = <%= produto.getAtivo() %> ? 'desativar' : 'ativar';
                               return confirm("Você tem certeza que deseja " + status + " este produto?");
                           }
                       </script>


                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6">Nenhum produto encontrado.</td>
                </tr>
                <% } %>
            </tbody>
        </table>

           <nav aria-label="Navegação de páginas">
              <ul class="pagination">
                  <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                      <a class="page-link" href="?page=${currentPage - 1}&size=10">Anterior</a>
                  </li>
                  <c:forEach begin="0" end="${totalPages - 1}" var="i">
                      <li class="page-item ${i == currentPage ? 'active' : ''}">
                          <a class="page-link" href="?page=${i}&size=10">${i + 1}</a>
                      </li>
                  </c:forEach>
                  <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
                      <a class="page-link" href="?page=${currentPage + 1}&size=10">Próximo</a>
                  </li>
              </ul>
          </nav>



        <div class="modal fade" id="modalProduto" tabindex="-1" aria-labelledby="modalProdutoLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modalProdutoLabel">Alterar Produto</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="modalProdutoForm" action="/adm/alterar-produto" method="post">

                             <div class="mb-3">
                                   <label for="modalProdutoId" class="form-label">ID</label>
                                   <input type="text" class="form-control" id="modalProdutoId2" name="id2" disabled>
                                   <input type="hidden" class="form-control" id="modalProdutoId" name="id">
                             </div>

                            <div class="mb-3">
                                <label for="modalProdutoNome" class="form-label">Nome</label>
                                <input type="text" class="form-control" id="modalProdutoNome" name="nome" maxlength="100" required>
                            </div>

                            <div class="mb-3">
                                <label for="modalProdutoDescricao" class="form-label">Descrição</label>
                                <textarea class="form-control" id="modalProdutoDescricao" name="descricao" rows="3" maxlength="255" required></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="modalProdutoPreco" class="form-label">Preço</label>
                                <input type="text" class="form-control" id="modalProdutoPreco" name="preco" required>
                            </div>

                            <div class="mb-3">
                                <label for="modalProdutoQuantidade" class="form-label">Quantidade</label>
                                <input type="number" class="form-control" id="modalProdutoQuantidade" name="quantidade" required>
                            </div>

                            <div class="mb-3">
                                <label for="modalProdutoCategoria" class="form-label">Categoria</label>
                                <input type="text" class="form-control" id="modalProdutoCategoria" name="categoria" maxlength="50" required>
                            </div>

                            <button type="submit" class="btn btn-primary">Salvar</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal -->
    <div class="modal fade" id="modalVisualizarProduto" tabindex="-1" aria-labelledby="modalVisualizarProdutoLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg"> <!-- Modal larger size -->
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="modalVisualizarProdutoLabel">Detalhes do Produto</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">

                        <div class="col-md-6">
                            <img id="modalProdutoImagem" src="" alt="Imagem do Produto" class="img-fluid rounded" style="max-height: 400px; width: 100%; object-fit: cover;">
                        </div>

                        <div class="col-md-6">
                            <p><strong>ID:</strong> <span id="modalProdutoId"></span></p>
                            <p><strong>Nome:</strong> <span id="modalProdutoNome"></span></p>
                            <p><strong>Descrição:</strong> <span id="modalProdutoDescricao"></span></p>
                            <p><strong>Preço:</strong> R$ <span id="modalProdutoPreco"></span></p>
                            <p><strong>Quantidade:</strong> <span id="modalProdutoQuantidade"></span></p>
                            <p><strong>Categoria:</strong> <span id="modalProdutoCategoria"></span></p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>
    </div>


    <script>
        $(document).ready(function(){
            var modal = document.getElementById('modalVisualizarProduto');
            modal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget;

                var produtoId = button.getAttribute('data-id');
                var produtoNome = button.getAttribute('data-nome');
                var produtoDescricao = button.getAttribute('data-descricao');
                var produtoPreco = button.getAttribute('data-preco');
                var produtoQuantidade = button.getAttribute('data-quantidade');
                var produtoCategoria = button.getAttribute('data-categoria');
                var produtoImagem = '/images/' + button.getAttribute('data-imagem');

                var modalProdutoId = modal.querySelector('#modalProdutoId');
                var modalProdutoNome = modal.querySelector('#modalProdutoNome');
                var modalProdutoDescricao = modal.querySelector('#modalProdutoDescricao');
                var modalProdutoPreco = modal.querySelector('#modalProdutoPreco');
                var modalProdutoQuantidade = modal.querySelector('#modalProdutoQuantidade');
                var modalProdutoCategoria = modal.querySelector('#modalProdutoCategoria');
                var modalProdutoImagem = modal.querySelector('#modalProdutoImagem');

                modalProdutoId.textContent = produtoId;
                modalProdutoNome.textContent = produtoNome;
                modalProdutoDescricao.textContent = produtoDescricao;
                modalProdutoPreco.textContent = produtoPreco;
                modalProdutoQuantidade.textContent = produtoQuantidade;
                modalProdutoCategoria.textContent = produtoCategoria;
                modalProdutoImagem.src = produtoImagem ? produtoImagem : 'default-image.jpg';
            });
        });
    </script>




    <script>
        $(document).ready(function(){
            var modal = document.getElementById('modalProduto');
            modal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget;

                var produtoId = button.getAttribute('data-id');
                var produtoId2 = button.getAttribute('data-id');
                var produtoNome = button.getAttribute('data-nome');
                var produtoDescricao = button.getAttribute('data-descricao');
                var produtoPreco = button.getAttribute('data-preco');
                var produtoQuantidade = button.getAttribute('data-quantidade');
                var produtoCategoria = button.getAttribute('data-categoria');

                var modalProdutoId = modal.querySelector('#modalProdutoId');
                var modalProdutoId2 = modal.querySelector('#modalProdutoId2');
                var modalProdutoNome = modal.querySelector('#modalProdutoNome');
                var modalProdutoDescricao = modal.querySelector('#modalProdutoDescricao');
                var modalProdutoPreco = modal.querySelector('#modalProdutoPreco');
                var modalProdutoQuantidade = modal.querySelector('#modalProdutoQuantidade');
                var modalProdutoCategoria = modal.querySelector('#modalProdutoCategoria');

                modalProdutoId.value = produtoId;
                modalProdutoId2.value = produtoId2;
                modalProdutoNome.value = produtoNome;
                modalProdutoDescricao.value = produtoDescricao;
                modalProdutoPreco.value = produtoPreco;
                modalProdutoQuantidade.value = produtoQuantidade;
                modalProdutoCategoria.value = produtoCategoria;

                <% if (usuarioLogado.getGrupo().equals("Estoquista")) { %>

                    modalProdutoNome.disabled = true;
                    modalProdutoDescricao.disabled = true;
                    modalProdutoPreco.disabled = true;
                    modalProdutoCategoria.disabled = true;
                    modalProdutoQuantidade.disabled = false;
                <% } else if (usuarioLogado.getGrupo().equals("Administrador")) { %>

                    modalProdutoNome.disabled = false;
                    modalProdutoDescricao.disabled = false;
                    modalProdutoPreco.disabled = false;
                    modalProdutoCategoria.disabled = false;
                    modalProdutoQuantidade.disabled = false;
                <% } %>
            });
        });
    </script>


</body>
</html>
