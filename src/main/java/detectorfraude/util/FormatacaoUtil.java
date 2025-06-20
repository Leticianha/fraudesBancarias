package detectorfraude.util;

public class FormatacaoUtil {

    public static String formatarCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");
        
        if (cnpj.length() != 14) {
            return cnpj; 
        }

        return cnpj.substring(0, 2) + "." +
               cnpj.substring(2, 5) + "." +
               cnpj.substring(5, 8) + "/" +
               cnpj.substring(8, 12) + "-" +
               cnpj.substring(12, 14);
    }
}