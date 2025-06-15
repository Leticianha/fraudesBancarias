package detectorfraude.service;

import detectorfraude.util.FuzzyMatchingUtil;

/**
 * Serviço responsável por verificar se dois nomes de empresas ou entidades
 * são semelhantes, com base em uma métrica de similaridade textual (Levenshtein).
 */
public class SimilaridadeNomeService {

    // Limiar de similaridade. Acima desse valor, os nomes são considerados semelhantes.
    private static final double LIMIAR_SIMILARIDADE = 0.75;

    /**
     * Verifica se dois nomes são considerados semelhantes com base no limiar
     * definido.
     *
     * @param nome1 Primeiro nome (ex: nome no débito automático)
     * @param nome2 Segundo nome (ex: nome oficial da empresa consultado via
     * CNPJ)
     * @return true se os nomes forem suficientemente semelhantes, false caso
     * contrário
     */
    public boolean nomesSaoSimilares(String nome1, String nome2) {
        double similaridade = FuzzyMatchingUtil.calcularSimilaridade(nome1, nome2);
        return similaridade >= LIMIAR_SIMILARIDADE;
    }

    /**
     * Retorna o índice numérico de similaridade entre dois nomes, variando de
     * 0.0 a 1.0.
     *
     * @param nome1 Primeiro nome
     * @param nome2 Segundo nome
     * @return Índice de similaridade entre as duas strings
     */
    public double obterIndiceSimilaridade(String nome1, String nome2) {
        return FuzzyMatchingUtil.calcularSimilaridade(nome1, nome2);
    }
}
