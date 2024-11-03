
document.querySelectorAll('input[name="pagamento"]').forEach((elem) => {
    elem.addEventListener('change', function(event) {
        const cartaoDetails = document.getElementById('cartao-details');
        const isCartao = event.target.value === 'cartao';

        cartaoDetails.classList.toggle('d-none', !isCartao);

        const requiredFields = [
            'numeroCartao',
            'codigoSeguranca',
            'nomeTitular',
            'vencimento',
            'parcelas'
        ];
        requiredFields.forEach((field) => {
            const inputField = document.querySelector(`input[name="${field}"]`);
            if (isCartao) {
                inputField.setAttribute('required', '');
            } else {
                inputField.removeAttribute('required');
            }
        });
    });
});



