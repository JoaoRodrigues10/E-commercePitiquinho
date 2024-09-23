$(document).ready(function () {
    $('#modalProduto').on('show.bs.modal', function (event) {
        const button = $(event.relatedTarget);
        const id = button.data('id');
        const nome = button.data('nome');
        const descricao = button.data('descricao');
        const preco = button.data('preco');
        const quantidade = button.data('quantidade');
        const categoria = button.data('categoria');
        const imagem = button.data('imagem');

        const modal = $(this);
        modal.find('#modalProdutoId').val(id);
        modal.find('#modalProdutoId2').val(id);
        modal.find('#modalProdutoNome').val(nome);
        modal.find('#modalProdutoDescricao').val(descricao);
        modal.find('#modalProdutoPreco').val(preco);
        modal.find('#modalProdutoQuantidade').val(quantidade);
        modal.find('#modalProdutoCategoria').val(categoria);
        modal.find('#modalProdutoImagem').attr('src', imagem);
    });

    $('#modalVisualizarProduto').on('show.bs.modal', function (event) {
        const button = $(event.relatedTarget);
        const nome = button.data('nome');
        const descricao = button.data('descricao');
        const preco = button.data('preco');
        const quantidade = button.data('quantidade');
        const categoria = button.data('categoria');
        const imagem = button.data('imagem');

        const modal = $(this);
        modal.find('#visualizarProdutoNome').text(nome);
        modal.find('#visualizarProdutoDescricao').text(descricao);
        modal.find('#visualizarProdutoPreco').text(preco);
        modal.find('#visualizarProdutoQuantidade').text(quantidade);
        modal.find('#visualizarProdutoCategoria').text(categoria);
        modal.find('#visualizarProdutoImagem').attr('src', imagem);
    });

    function confirmarAcao() {
        return confirm('Tem certeza de que deseja alterar o status deste produto?');
    }

    $('#imagem').on('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#previewImagem').attr('src', e.target.result).removeClass('d-none');
            }
            reader.readAsDataURL(file);
        }
    });
});