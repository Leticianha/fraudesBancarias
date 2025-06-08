package detectorfraude.service;

public class ValidacaoCNPJService {

    public boolean validarCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14) return false;
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        String cnpjBase = cnpj.substring(0, 12);
        int primeiroDigito = calcularDigitoVerificador(cnpjBase, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        cnpjBase += primeiroDigito;
        int segundoDigito = calcularDigitoVerificador(cnpjBase, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return cnpj.equals(cnpjBase + segundoDigito);
    }

    public static int calcularDigitoVerificador(String cnpjBase, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(cnpjBase.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}