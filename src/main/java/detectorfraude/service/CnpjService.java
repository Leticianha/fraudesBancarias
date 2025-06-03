package detectorfraude.service;

import com.google.gson.Gson;
import detectorfraude.model.ApiResponse;
import detectorfraude.model.Empresa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class CnpjService {

    private static final String BASE_URL = "https://www.receitaws.com.br/v1/cnpj/";
    private ValidacaoCNPJService validador;

    // Construtor
    public CnpjService() {
        this.validador = new ValidacaoCNPJService();
    }

    public Empresa consultarCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");

        // Valida o CNPJ antes de consultar a API
        if (!validador.validarCNPJ(cnpj)) {
            System.out.println("CNPJ inválido! Por favor, verifique e tente novamente.");
            return null;
        }

        try {
            String url = BASE_URL + cnpj;

            URI uri = URI.create(url);
            URL urlObj = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro na requisição. Código: " + conn.getResponseCode());
                return null;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder jsonSB = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    jsonSB.append(output);
                }

                // Aqui usamos o modelo APIResponse
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(jsonSB.toString(), ApiResponse.class);

                Empresa empresa = new Empresa();
                empresa.setRazaoSocial(apiResponse.getNome());
                empresa.setCnpj(apiResponse.getCnpj());
                empresa.setSituacaoCadastral(apiResponse.getSituacao());

                return empresa;
            } finally {
                conn.disconnect();
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar CNPJ: " + e.getMessage());
            return null;
        }

    }
}
