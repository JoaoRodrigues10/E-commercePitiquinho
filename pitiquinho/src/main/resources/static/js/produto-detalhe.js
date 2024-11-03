$(document).ready(function () {
    let imagens = $('#carrosselImagensProduto').data('imagens');
    const carouselInner = $('#visualizarProdutoImagens');
    carouselInner.empty();

    if (imagens) {
        const listaImagens = imagens.split(',');

        listaImagens.forEach((imagem, index) => {
            const imagemUrl = imagem.trim().startsWith('http') ? imagem.trim() : '/images/' + imagem.trim();
            const activeClass = index === 0 ? 'active' : '';

            carouselInner.append(`
                <div class="carousel-item ${activeClass}">
                    <img src="${imagemUrl}" class="d-block" alt="Imagem do Produto">
                </div>
            `);
        });
    } else {
        carouselInner.append(`
            <div class="carousel-item active">
                <img src="/images/imagem.jpg" class="d-block" alt="Imagem Padrão">
            </div>
        `);
    }

    function atualizarQuantidadeCarrinho() {
        let carrinho = localStorage.getItem('carrinho');
        let quantidade = 0;

        if (carrinho) {
            carrinho = JSON.parse(carrinho);
            quantidade = carrinho.reduce((acc, item) => acc + item.quantidade, 0);
        }

        $('#quantidadeCarrinho').text(quantidade);
    }

    $('#comprarProduto').click(function () {
        const produto = {
            id: $('#IdProduto').text().trim() || '',
            nome: $('#nomeProduto').text().trim() || '',
            preco: parseFloat($('#precoProduto').text().replace('R$ ', '').replace(',', '.').trim()) || 0,
            quantidade: 1,
            categoria: $('#categoriaProduto').text().trim() || '',
            avaliacao: $('#avaliacaoProduto').text().trim() || '',
            imagem: imagens ? `images/${imagens.split(',')[0].trim()}` : 'images/imagem.jpg',
            usuario: $('#IdUsuario').text().trim() || ''
        };

        let carrinho = localStorage.getItem('carrinho');
        if (carrinho) {
            carrinho = JSON.parse(carrinho);
        } else {
            carrinho = [];
        }

        const produtoExistente = carrinho.find(item => item.id === produto.id);
        if (produtoExistente) {
            produtoExistente.quantidade += 1;
        } else {
            carrinho.push(produto);
        }

        localStorage.setItem('carrinho', JSON.stringify(carrinho));
        $('#confirmacaoModal').modal('show');
    });

    $('#irParaCarrinho').click(function () {
        window.location.href = '/carrinho';
        atualizarQuantidadeCarrinho();
    });

    $('#continuarCompra').click(function () {
        $('#confirmacaoModal').modal('hide');
        atualizarQuantidadeCarrinho();
    });

    atualizarQuantidadeCarrinho();
});
