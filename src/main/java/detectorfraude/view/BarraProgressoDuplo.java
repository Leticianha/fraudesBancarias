package detectorfraude.view;

import javax.swing.*;
import java.awt.*;

public class BarraProgressoDuplo extends JComponent {
    private int percentualDenunciados = 0;
    private int percentualBloqueados = 0;

    public void setValores(int denunciados, int bloqueados) {
        int soma = denunciados + bloqueados;
        if (soma == 0) {
            this.percentualDenunciados = 0;
            this.percentualBloqueados = 0;
        } else {
            this.percentualDenunciados = (denunciados * 100) / soma;
            this.percentualBloqueados = (bloqueados * 100) / soma;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int largura = getWidth();
        int altura = getHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, largura, altura);

        int larguraDenunciados = (largura * percentualDenunciados) / 100;
        int larguraBloqueados = (largura * percentualBloqueados) / 100;

        g.setColor(new Color(112, 47, 138)); // roxo
        g.fillRect(0, 0, larguraDenunciados, altura);

        g.setColor(Color.RED);
        g.fillRect(larguraDenunciados, 0, larguraBloqueados, altura);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, largura - 1, altura - 1);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 20);
    }
}
