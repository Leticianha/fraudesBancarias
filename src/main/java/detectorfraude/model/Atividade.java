package detectorfraude.model;

public class Atividade {
    private String code;
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text + " (CNAE: " + code + ")";
    }
}
