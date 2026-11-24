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
                    mostrarCatalogo();
                    break;
                case 2:
                    atualizarJogo();
                    break;
                case 3:
                    removerJogo();
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
        if (nome == null || nome.isEmpty()) return;
        String categoria = JOptionPane.showInputDialog("Categoria do jogo:");
        if (categoria == null || categoria.isEmpty()) return;
        String numJogadores = JOptionPane.showInputDialog("Quantidadade de jogadores:");
        if (numJogadores == null || numJogadores.isEmpty()) return;

        String[] tiposDeJogo = {"Tabuleiro", "Cartas"};

        Object tipoSelecionado = JOptionPane.showInputDialog(
            null,
            "Tipo de jogo:",
            null,
            JOptionPane.QUESTION_MESSAGE,
            null,
            tiposDeJogo,
            tiposDeJogo[0]
        );

        String tipoJogo = tipoSelecionado != null ? tipoSelecionado.toString() : null;
        if (tipoJogo == null) return;

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(nome).append(",").append(categoria).append(",").append(numJogadores).append(",").append(tipoJogo).append("\n");
            JOptionPane.showMessageDialog(null, "Jogo catalogado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gravar no arquivo!");
            e.printStackTrace();
        }
    }

    private static void mostrarCatalogo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder registros = new StringBuilder("Registros:\n");
            String linha;
            while ((linha = reader.readLine()) != null) {
                registros.append(linha).append("\n");
            }
            JOptionPane.showMessageDialog(null, registros.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Erro ao ler o arquivo.");
            e.printStackTrace();
        }
    }

    private static void atualizarJogo() {
        List<String> registros = lerRegistros();
        if (registros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum jogo encontrado.");
            return;
        }

        String nomeBusca = JOptionPane.showInputDialog(
            "Digite o nome do jogo a ser atualizado:"
        );
        if (nomeBusca == null || nomeBusca.isEmpty()) {
            return;
        }

        for (int i = 0; i < registros.size(); i++) {
            String[] dados = registros.get(i).split(",", -1);
            if (dados.length == 4 && dados[0].equalsIgnoreCase(nomeBusca)) {
                String novoNome = JOptionPane.showInputDialog("Nome do jogo:", dados[0]);
                if (novoNome == null || novoNome.isEmpty()) return;

                String novaCategoria = JOptionPane.showInputDialog("Categoria do jogo:", dados[1]);
                if (novaCategoria == null || novaCategoria.isEmpty()) return;

                String novosJogadores = JOptionPane.showInputDialog(
                    "Quantidade de jogadores:", dados[2]
                );
                if (novosJogadores == null || novosJogadores.isEmpty()) return;

                String[] tiposDeJogo = {"Tabuleiro", "Cartas"};
                Object tipoSelecionado = JOptionPane.showInputDialog(
                    null,
                    "Tipo de jogo:",
                    "Escolha o tipo",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tiposDeJogo,
                    dados[3]
                );
                if (tipoSelecionado == null) return;

                registros.set(
                    i,
                    novoNome + "," + novaCategoria + "," + novosJogadores + ","
                        + tipoSelecionado
                );
                if (escreverRegistros(registros)) {
                    JOptionPane.showMessageDialog(null, "Jogo atualizado com sucesso!");
                }
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Jogo não encontrado.");
    }

    private static void removerJogo() {
        List<String> registros = lerRegistros();
        if (registros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum jogo encontrado.");
            return;
        }

        String nomeBusca = JOptionPane.showInputDialog(
            "Digite o nome do jogo a ser removido:"
        );
        if (nomeBusca == null || nomeBusca.isEmpty()) {
            return;
        }

        boolean encontrado = registros.removeIf(registro -> {
            String[] dados = registro.split(",", -1);
            return dados.length == 4 && dados[0].equalsIgnoreCase(nomeBusca);
        });

        if (encontrado) {
            if (escreverRegistros(registros)) {
                JOptionPane.showMessageDialog(null, "Jogo removido com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Jogo não encontrado.");
        }
    }

    private static List<String> lerRegistros() {
        List<String> registros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                registros.add(linha);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo.");
            e.printStackTrace();
        }
        return registros;
    }

    private static boolean escreverRegistros(List<String> registros) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String registro : registros) {
                writer.append(registro).append(System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao escrever no arquivo.");
            e.printStackTrace();
            return false;
        }
    }
}
