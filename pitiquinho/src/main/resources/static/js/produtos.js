$(document).ready(function () {
    function atualizarQuantidadeCarrinho() {
        let carrinho = localStorage.getItem('carrinho');
        let quantidadeTotal = 0;

        if (carrinho) {
            carrinho = JSON.parse(carrinho);
            quantidadeTotal = carrinho.reduce((total, item) => total + item.quantidade, 0);
        }

        $('#quantidadeCarrinho').text(quantidadeTotal);
    }

    atualizarQuantidadeCarrinho();
});
