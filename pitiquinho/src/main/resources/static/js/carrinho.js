
console.log(JSON.parse(localStorage.getItem('carrinho')));

function carregarCarrinho() {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    const carrinhoBody = document.getElementById('carrinho-body');
    carrinhoBody.innerHTML = '';

    if (carrinho.length === 0) {
        carrinhoBody.innerHTML = '<tr><td colspan="6" class="text-center">Carrinho vazio.</td></tr>';
        document.getElementById('total').textContent = 'R$ 0,00';
        document.getElementById('total-final').textContent = 'R$ 0,00';
        document.getElementById('valor-frete').textContent = 'R$ 0,00';
        return;
    }

    let total = 0;

    carrinho.forEach((item) => {
        const preco = parseFloat(item.preco);
        const quantidade = item.quantidade;
        const subtotal = preco * quantidade;
        total += subtotal;

        carrinhoBody.innerHTML += `
            <tr id="item-${item.id}">
                <td>
                    <img src="${item.imagem}" class="mr-3" alt="${item.nome}" style="width: 64px;">
                </td>
                <td>
                    <h5 class="mt-0">${item.nome}</h5>
                </td>
                <td>
                    <p>${item.id}</p>
                </td>
                <td>
                    <button type="button" class="btn btn-secondary btn-sm" onclick="alterarQuantidade('${item.id}', -1)">-</button>
                    <span id="quantidade-${item.id}">${quantidade}</span>
                    <button type="button" class="btn btn-secondary btn-sm" onclick="alterarQuantidade('${item.id}', 1)">+</button>
                </td>
                <td>R$ ${preco.toFixed(2).replace('.', ',')}</td>
                <td>R$ ${subtotal.toFixed(2).replace('.', ',')}</td>
                <td>
                    <button type="button" class="btn btn-danger btn-sm" onclick="removerItem('${item.id}')">Remover</button>
                </td>
            </tr>
        `;
    });

    document.getElementById('total').textContent = total.toFixed(2).replace('.', ',') + ' R$';
    atualizarTotal();
}

function adicionarProduto(produto) {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    const produtoExistente = carrinho.find(item => item.id === produto.id);

    if (produtoExistente) {
        produtoExistente.quantidade++;
    } else {
        carrinho.push({
            id: produto.id,
            nome: produto.nome,
            imagem: produto.imagem,
            preco: produto.preco,
            quantidade: 1,
        });
    }

    localStorage.setItem('carrinho', JSON.stringify(carrinho));
    carregarCarrinho();
}

function alterarQuantidade(produtoId, delta) {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    const produto = carrinho.find(item => item.id === produtoId);

    if (produto) {
        produto.quantidade += delta;
        if (produto.quantidade < 1) produto.quantidade = 1;


        document.getElementById(`quantidade-${produtoId}`).textContent = produto.quantidade;

        localStorage.setItem('carrinho', JSON.stringify(carrinho));


        carregarCarrinho();
    }
}


function removerItem(produtoId) {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    const novoCarrinho = carrinho.filter(item => item.id !== produtoId);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
    carregarCarrinho();
}


function buscarFrete() {
    const cep = document.getElementById('cep').value;

    if (cep.length === 8) {
        fetch(`https://viacep.com.br/ws/${cep}/json/`)
            .then(response => response.json())
            .then(data => {
                if (!data.erro) {
                    document.getElementById('endereco').innerHTML = `
                        <strong>Endereço:</strong> ${data.logradouro}, ${data.bairro}, ${data.localidade} - ${data.uf}
                    `;
                    document.getElementById('frete-opcoes').style.display = 'block';
                } else {
                    alert('CEP inválido. Tente novamente.');
                }
            })
            .catch(error => {
                console.error('Erro ao buscar o CEP:', error);
                alert('Ocorreu um erro ao buscar o CEP. Tente novamente.');
            });
    } else {
        alert('Por favor, insira um CEP válido com 8 dígitos.');
    }
}

function atualizarTotal() {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    let total = 0;

    carrinho.forEach(item => {
        total += item.preco * item.quantidade;
    });

    const frete = parseFloat(document.getElementById('opcao-frete').value) || 0;
    const valorFrete = frete ? frete.toFixed(2).replace('.', ',') + ' R$' : 'R$ 0,00';

    document.getElementById('valor-frete').textContent = valorFrete;

    const totalFinal = total + frete;
    document.getElementById('total-final').textContent = totalFinal.toFixed(2).replace('.', ',') + ' R$';
    document.getElementById('total').textContent = total.toFixed(2).replace('.', ',') + ' R$';
}

function verificarFrete() {
    const opcaoFrete = document.getElementById('opcao-frete').value;
    if (opcaoFrete === "0") {
        alert("Por favor, escolha uma opção de frete antes de finalizar a compra.");
        return false;
    }
    return true; 
}


function coletarItensCarrinho() {
    let itensCarrinho = [];
    $('#carrinho-body tr').each(function () {

        let idProduto = $(this).find('td:nth-child(3)').text().trim();
        let quantidade = parseInt($(this).find(`span[id^="quantidade-"]`).text().trim());
        itensCarrinho.push({ id: idProduto, quantidade: quantidade });
    });
    return itensCarrinho;
}

function finalizarCompra(event) {
    event.preventDefault();

    let itensCarrinho = coletarItensCarrinho();

    console.log(itensCarrinho);

    $.ajax({
        type: "POST",
        url: "/finalizar/checkout-item",
        contentType: "application/json",
        data: JSON.stringify(itensCarrinho),
        success: function (response) {
            window.location.href = "/finalizar/checkout";
        },
        error: function (xhr, status, error) {
            alert("Erro ao finalizar a compra: " + error);
        }
    });
}





window.onload = carregarCarrinho;
