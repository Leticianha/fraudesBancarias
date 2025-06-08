package detectorfraude.util;

public class FuzzyMatchingUtil {

    /**
     * Calcula a similaridade entre duas strings com base na distância de Levenshtein.
     * Retorna um valor entre 0.0 (completamente diferente) e 1.0 (idêntico).
     */
    public static double calcularSimilaridade(String s1, String s2) {
        s1 = s1.toLowerCase().trim();
        s2 = s2.toLowerCase().trim();

        int distancia = calcularDistanciaLevenshtein(s1, s2);
        int tamanhoMax = Math.max(s1.length(), s2.length());

        if (tamanhoMax == 0) return 1.0;

        return 1.0 - ((double) distancia / tamanhoMax);
    }

    /**
     * Calcula a distância de Levenshtein entre duas strings.
     */
    public static int calcularDistanciaLevenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= s2.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int custo = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(
                        dp[i - 1][j] + 1,      // Remoção
                        dp[i][j - 1] + 1),     // Inserção
                        dp[i - 1][j - 1] + custo); // Substituição
            }
        }

        return dp[s1.length()][s2.length()];
    }
}
