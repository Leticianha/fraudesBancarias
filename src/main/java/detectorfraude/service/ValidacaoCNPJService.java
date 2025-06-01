package detectorfraude.service;

public class ValidacaoCNPJService {

    // Método principal para validar CNPJ
    public boolean validarCNPJ(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^\\d]", "");

        // Verifica se tem 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (exemplo: 00000000000000)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Obtém os 12 primeiros dígitos
        String cnpjBase = cnpj.substring(0, 12);

        // Calcula o primeiro dígito verificador
        int primeiroDigito = calcularDigitoVerificador(cnpjBase, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        // Calcula o segundo dígito verificador
        cnpjBase += primeiroDigito; // adiciona o primeiro dígito
        int segundoDigito = calcularDigitoVerificador(cnpjBase, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        // Monta o CNPJ completo com os dígitos calculados
        String cnpjCalculado = cnpjBase + segundoDigito;

        // Compara o CNPJ calculado com o informado
        return cnpj.equals(cnpjCalculado);
    }

    // Método auxiliar para calcular os dígitos verificadores
    private int calcularDigitoVerificador(String cnpjBase, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(cnpjBase.charAt(i)) * pesos[i];
        }

        int resto = soma % 11;

        return (resto < 2) ? 0 : 11 - resto;
    }
}
