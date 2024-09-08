package br.loja.pitiquinho.util;

import org.springframework.stereotype.Component;

@Component
public class util {

    public boolean validarCPF(String cpf2) {
        String cpf = cpf2.replaceAll("\\D", "");


        if (cpf.length() != 11 || cpf.chars().allMatch(c -> c == cpf.charAt(0))) {
            return false;
        }

        int soma = 0;
        int resto;

        for (int i = 1; i <= 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (11 - i);
        }
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        if (resto != Character.getNumericValue(cpf.charAt(9))) return false;

        soma = 0;


        for (int i = 1; i <= 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (12 - i);
        }
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        return resto == Character.getNumericValue(cpf.charAt(10));
    }

    public boolean senhaConfirmada(String senha, String confirmarSenha) {
        return senha != null && senha.equals(confirmarSenha);
    }

}
