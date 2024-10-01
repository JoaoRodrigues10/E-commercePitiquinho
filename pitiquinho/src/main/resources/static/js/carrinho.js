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

    carrinho.forEach((item, index) => {
        const preco = parseFloat(item.preco);
        const quantidade = item.quantidade;
        const subtotal = preco * quantidade;
        total += subtotal;

        carrinhoBody.innerHTML += `
            <tr id="item-${index + 1}">
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
                    <button type="button" class="btn btn-secondary btn-sm" onclick="alterarQuantidade(${index + 1}, -1)">-</button>
                    <span id="quantidade-${index + 1}">${quantidade}</span>
                    <button type="button" class="btn btn-secondary btn-sm" onclick="alterarQuantidade(${index + 1}, 1)">+</button>
                </td>
                <td>R$ ${preco.toFixed(2).replace('.', ',')}</td>
                <td>R$ ${subtotal.toFixed(2).replace('.', ',')}</td>
                <td>
                    <button type="button" class="btn btn-danger btn-sm" onclick="removerItem(${index + 1})">Remover</button>
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
    const quantidadeElement = document.getElementById(`quantidade-${produtoId}`);
    let quantidade = parseInt(quantidadeElement.textContent) + delta;
    if (quantidade < 1) quantidade = 1;
    quantidadeElement.textContent = quantidade;

    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    carrinho[produtoId - 1].quantidade = quantidade;
    localStorage.setItem('carrinho', JSON.stringify(carrinho));

    carregarCarrinho();
}

function removerItem(produtoId) {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    carrinho.splice(produtoId - 1, 1);
    localStorage.setItem('carrinho', JSON.stringify(carrinho));

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

function validarCheckout() {
    const opcaoFrete = document.getElementById('opcao-frete').value;

    if (opcaoFrete === '0') {
        alert('Por favor, selecione uma opção de frete.');
        return false;
    }

    const usuarioLogado = /*[[${usuario}]]*/ null; // Aqui pegamos o usuário da model

    if (!usuarioLogado) {
        alert('Você precisa estar logado para finalizar a compra. Redirecionando para a página de login.');
        window.location.href = '/login';
        return false;
    }

    return true;
}

window.onload = carregarCarrinho;
