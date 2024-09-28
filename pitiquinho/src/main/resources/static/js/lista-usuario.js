$(document).ready(function(){
    $('#modalCpf').inputmask('999.999.999-99');

    var modal = document.getElementById('staticBackdrop');
    modal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var userId = button.getAttribute('data-id');
        var userNome = button.getAttribute('data-nome');
        var userEmail = button.getAttribute('data-email');
        var userCpf = button.getAttribute('data-cpf');
        var userGrupo = button.getAttribute('data-grupo');

        var modalId2 = modal.querySelector('#modalId2');
        var modalId = modal.querySelector('#modalId');
        var modalNome = modal.querySelector('#modalNome');
        var modalEmail2 = modal.querySelector('#modalEmail2');
        var modalEmail = modal.querySelector('#modalEmail');
        var modalCpf = modal.querySelector('#modalCpf');
        var modalGrupo = modal.querySelector('#modalGrupo');

        modalId2.value = userId;
        modalId.value = userId;
        modalNome.value = userNome;
        modalEmail2.value = userEmail;
        modalEmail.value = userEmail;
        modalCpf.value = userCpf;
        modalGrupo.value = userGrupo;
    });
});

function confirmarAlteracao() {
    return confirm("Tem certeza que deseja salvar as alterações?");
}