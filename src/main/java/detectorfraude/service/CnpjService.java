package detectorfraude.service;

import com.google.gson.Gson;
import detectorfraude.model.APIResponse;
import detectorfraude.model.Empresa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class CnpjService {

    private static final String BASE_URL = "https://www.receitaws.com.br/v1/cnpj/";
    private ValidacaoCNPJService validador;

    public CnpjService() {
        this.validador = new ValidacaoCNPJService();
    }

    public Empresa consultarCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (!validador.validarCNPJ(cnpj)) {
            System.out.println("❌ CNPJ inválido.");
            return null;
        }

        try {
            String url = BASE_URL + cnpj;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(7000);
            conn.setReadTimeout(7000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();

            if (status == 429) {
                System.out.println("⚠️ Limite de requisições excedido. Tente mais tarde.");
                return null;
            }

            if (status != 200) {
                System.out.println("Erro na requisição. Código: " + status);
                return null;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder jsonSB = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    jsonSB.append(output);
                }

                APIResponse apiResponse = new Gson().fromJson(jsonSB.toString(), APIResponse.class);

                Empresa empresa = new Empresa();
                empresa.setRazaoSocial(apiResponse.getNome());
                empresa.setCnpj(apiResponse.getCnpj());
                empresa.setSituacaoCadastral(apiResponse.getSituacao());

                return empresa;
            } finally {
                conn.disconnect();
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao consultar CNPJ: " + e.getMessage());
            return null;
        }
    }
}