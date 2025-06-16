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

        int larguraDenunciados = largura * percentualDenunciados / 100;
        int larguraBloqueados = largura * percentualBloqueados / 100;

        // Barra roxa - Denunciados
        g.setColor(new Color(112, 47, 138)); // roxo
        g.fillRect(0, 0, larguraDenunciados, altura);

        // Barra vermelha - Bloqueados
        g.setColor(Color.RED);
        g.fillRect(larguraDenunciados, 0, larguraBloqueados, altura);

        // Contorno da barra
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, largura - 1, altura - 1);

        // Escrever as porcentagens dentro das barras
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();

        // Texto da porcentagem de denunciados
        String textoDenunciados = percentualDenunciados + "%";
        int textoDenunciadosLargura = fm.stringWidth(textoDenunciados);
        int textoDenunciadosX = larguraDenunciados / 2 - textoDenunciadosLargura / 2;
        int textoDenunciadosY = altura / 2 + fm.getAscent() / 2 - 2;

        if (larguraDenunciados > textoDenunciadosLargura + 4) { // Só desenha se couber
            g.drawString(textoDenunciados, textoDenunciadosX, textoDenunciadosY);
        }

        // Texto da porcentagem de bloqueados
        String textoBloqueados = percentualBloqueados + "%";
        int textoBloqueadosLargura = fm.stringWidth(textoBloqueados);
        int textoBloqueadosX = larguraDenunciados + (larguraBloqueados / 2) - textoBloqueadosLargura / 2;
        int textoBloqueadosY = altura / 2 + fm.getAscent() / 2 - 2;

        if (larguraBloqueados > textoBloqueadosLargura + 4) { // Só desenha se couber
            g.drawString(textoBloqueados, textoBloqueadosX, textoBloqueadosY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 20);
    }
}
