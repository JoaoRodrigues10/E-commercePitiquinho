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
        const imagemUrl = imagem.startsWith('http') ? imagem : '/images/' + imagem;
        modal.find('#modalProdutoImagem').attr('src', imagemUrl);
    });

    $('#modalVisualizarProduto').on('show.bs.modal', function (event) {
        const button = $(event.relatedTarget);
        const nome = button.data('nome');
        const descricao = button.data('descricao');
        const preco = button.data('preco');
        const quantidade = button.data('quantidade');
        const categoria = button.data('categoria');
        const imagens = button.data('imagens');

        const modal = $(this);
        modal.find('#visualizarProdutoNome').text(nome);
        modal.find('#visualizarProdutoDescricao').text(descricao);
        modal.find('#visualizarProdutoPreco').text(preco);
        modal.find('#visualizarProdutoQuantidade').text(quantidade);
        modal.find('#visualizarProdutoCategoria').text(categoria);

        const carouselInner = modal.find('#visualizarProdutoImagens');
        carouselInner.empty();

        if (imagens) {
            const listaImagens = imagens.split(',');

            listaImagens.forEach((imagem, index) => {
                const imagemUrl = '/images/' + imagem.trim();
                const activeClass = index === 0 ? 'active' : '';

                carouselInner.append(`
                    <div class="carousel-item ${activeClass}">
                        <img src="${imagemUrl}" class="d-block w-100" alt="Imagem do Produto">
                    </div>
                `);
            });
        } else {
            carouselInner.append(`
                <div class="carousel-item active">
                    <img src="/images/imagem.jpg" class="d-block w-100" alt="Imagem Padrão">
                </div>
            `);
        }
    });

    $('#imagens').on('change', function () {
        const files = this.files;
        const previewContainer = $('#previewImagens');
        previewContainer.empty();

        if (files) {
            Array.from(files).forEach(file => {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const img = $('<img>').attr('src', e.target.result).addClass('img-fluid rounded mt-3').css({
                        'max-height': '200px',
                        'width': 'auto',
                        'object-fit': 'cover',
                        'margin-right': '10px'
                    });
                    previewContainer.append(img);
                }
                reader.readAsDataURL(file);
            });
        }
    });

});

$(document).ready(function(){
    $('#preco').mask('R$ 0.000,00', {reverse: true});
});

function confirmarAcao() {
    return confirm('Tem certeza de que deseja alterar o status deste produto?');
}
