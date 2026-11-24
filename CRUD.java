import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class CRUD {
    private static final String filePath = "board_games.csv";

    public static void main (String[] args){
        String [] opcoes = {"Adicionar", "Catálogo", "Atualizar", "Deletar"};
        boolean continuar = true;

        while (continuar) {
            int escolha = JOptionPane.showOptionDialog(
                null,
                "Biblioteca de Jogos",
                "Luderia Fenda do Bikini",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );

            switch (escolha) {
                case 0:
                    adicionarJogo();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Read");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Update");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Delete");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Saindo...");
                    continuar = false;
                    break;
            }
        }
    }

    private static void adicionarJogo() {
        String nome = JOptionPane.showInputDialog("Nome do Jogo:");
        String categoria = JOptionPane.showInputDialog("Categoria do jogo:");
        String numJogadores = JOptionPane.showInputDialog("Quantidadade de jogadores:");
        String tipoJogo = JOptionPane.showInputDialog("Tipo de jogo (tabuleiro ou cartas:");

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(nome).append(",").append(categoria).append(",").append(numJogadores).append(",").append(tipoJogo).append("\n");
            JOptionPane.showMessageDialog(null, "Jogo catalogado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gravar no arquivo!");
            e.printStackTrace();
        }
    }
}
