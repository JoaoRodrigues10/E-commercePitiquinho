$(document).ready(function () {

    // Função para atualizar quantidade no carrinho (ex.: badge no header)
    function atualizarQuantidadeCarrinho() {
        let carrinho = localStorage.getItem('carrinho');
        let quantidade = 0;

        if (carrinho) {
            carrinho = JSON.parse(carrinho);
            quantidade = carrinho.reduce((acc, item) => acc + item.quantidade, 0);
        }

        $('#quantidadeCarrinho').text(quantidade);
    }

    // Incrementar / Decrementar quantidade
    $('#increaseQuantity').click(function () {
        let qty = parseInt($('#quantidade').val());
        let max = parseInt($('#quantidadeProduto').text());
        if (qty < max) $('#quantidade').val(qty + 1);
    });

    $('#decreaseQuantity').click(function () {
        let qty = parseInt($('#quantidade').val());
        if (qty > 1) $('#quantidade').val(qty - 1);
    });

    // Adicionar produto ao carrinho
    $('#comprarProduto').click(function () {
        const produto = {
            id: $('#IdProduto').text().trim(),
            nome: $('#nomeProduto').text().trim(),
            preco: parseFloat($('#precoProduto').text().replace('R$ ', '').replace(',', '.').trim()),
            quantidade: parseInt($('#quantidade').val()),
            categoria: $('#categoriaProduto').text().trim(),
            avaliacao: $('#avaliacaoProduto').text().trim(),
            imagem: $('#imagemProduto').attr('src')
        };

        let carrinho = localStorage.getItem('carrinho');
        carrinho = carrinho ? JSON.parse(carrinho) : [];

        const produtoExistente = carrinho.find(item => item.id === produto.id);
        if (produtoExistente) {
            produtoExistente.quantidade += produto.quantidade;
        } else {
            carrinho.push(produto);
        }

        localStorage.setItem('carrinho', JSON.stringify(carrinho));
        $('#confirmacaoModal').modal('show');
        atualizarQuantidadeCarrinho();
    });

    // Botões do modal
    $('#irParaCarrinho').click(function () {
        window.location.href = '/carrinho';
    });

    $('#continuarCompra').click(function () {
        $('#confirmacaoModal').modal('hide');
    });

    // Atualiza badge ao carregar a página
    atualizarQuantidadeCarrinho();
});
