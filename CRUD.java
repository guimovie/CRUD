import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.ListSelectionModel;

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
        JPanel painel = new JPanel(new GridLayout(0, 2, 8, 8));

        JTextField campoNome = new JTextField();
        JTextField campoCategoria = new JTextField();
        JTextField campoJogadores = new JTextField();
        JComboBox<String> campoTipo =
            new JComboBox<>(new String[] {"Tabuleiro", "Cartas"});

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(new JLabel("Categoria:"));
        painel.add(campoCategoria);
        painel.add(new JLabel("Número de jogadores:"));
        painel.add(campoJogadores);
        painel.add(new JLabel("Tipo:"));
        painel.add(campoTipo);

        int resultado = JOptionPane.showConfirmDialog(
            null,
            painel,
            "Adicionar Jogo",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText().trim();
            String categoria = campoCategoria.getText().trim();
            String numJogadores = campoJogadores.getText().trim();
            String tipoJogo = (String) campoTipo.getSelectedItem();

            if (nome.isEmpty() || categoria.isEmpty() || numJogadores.isEmpty() || tipoJogo == null) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                return;
            }

            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.append(nome).append(",").append(categoria).append(",").append(numJogadores).append(",").append(tipoJogo).append("\n");
                JOptionPane.showMessageDialog(null, "Jogo catalogado com sucesso!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao gravar no arquivo!");
                e.printStackTrace();
            }
        }
    }

    private static void mostrarCatalogo() {
        List<String> registros = lerRegistros();
        DefaultTableModel modelo = criarModeloTabela(registros, null);
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum jogo encontrado.");
            return;
        }

        JTable tabela = new JTable(modelo);
        tabela.setRowSelectionAllowed(false);
        tabela.setCellSelectionEnabled(false);

        JOptionPane.showMessageDialog(
            null,
            new JScrollPane(tabela),
            "Catálogo de jogos",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    private static DefaultTableModel criarModeloTabela(
        List<String> registros,
        List<Integer> indicesRegistros
    ) {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[] {"Nome", "Categoria", "Nº de jogadores", "Tipo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        for (int i = 0; i < registros.size(); i++) {
            String[] dados = registros.get(i).split(",", -1);
            if (dados.length == 4) {
                modelo.addRow(dados);
                if (indicesRegistros != null) {
                    indicesRegistros.add(i);
                }
            }
        }
        return modelo;
    }

    private static int selecionarJogo(List<String> registros, String titulo) {
        List<Integer> indicesRegistros = new ArrayList<>();
        DefaultTableModel modelo = criarModeloTabela(registros, indicesRegistros);
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum jogo encontrado.");
            return -1;
        }

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(true);

        int resultado = JOptionPane.showConfirmDialog(
            null,
            new JScrollPane(tabela),
            titulo,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        if (resultado != JOptionPane.OK_OPTION) {
            return -1;
        }

        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um jogo na tabela.");
            return -1;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);
        return indicesRegistros.get(linhaModelo);
    }

    private static void atualizarJogo() {
        List<String> registros = lerRegistros();
        if (registros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum jogo encontrado.");
            return;
        }

        int indiceRegistro = selecionarJogo(registros, "Selecione o jogo para atualizar");
        if (indiceRegistro == -1) {
            return;
        }

        String[] dados = registros.get(indiceRegistro).split(",", -1);
        JPanel painel = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField campoNome = new JTextField(dados[0]);
        JTextField campoCategoria = new JTextField(dados[1]);
        JTextField campoJogadores = new JTextField(dados[2]);
        JComboBox<String> campoTipo =
            new JComboBox<>(new String[] {"Tabuleiro", "Cartas"});
        campoTipo.setSelectedItem(dados[3]);

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(new JLabel("Categoria:"));
        painel.add(campoCategoria);
        painel.add(new JLabel("Número de jogadores:"));
        painel.add(campoJogadores);
        painel.add(new JLabel("Tipo:"));
        painel.add(campoTipo);

        int resultado = JOptionPane.showConfirmDialog(
            null,
            painel,
            "Editar jogo",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        String novoNome = campoNome.getText().trim();
        String novaCategoria = campoCategoria.getText().trim();
        String novosJogadores = campoJogadores.getText().trim();
        String novoTipo = (String) campoTipo.getSelectedItem();
        if (novoNome.isEmpty() || novaCategoria.isEmpty()
                || novosJogadores.isEmpty() || novoTipo == null) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
            return;
        }

        registros.set(
            indiceRegistro,
            novoNome + "," + novaCategoria + "," + novosJogadores + "," + novoTipo
        );
        if (escreverRegistros(registros)) {
            JOptionPane.showMessageDialog(null, "Jogo atualizado com sucesso!");
        }
    }

    private static void removerJogo() {
        List<String> registros = lerRegistros();
        if (registros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum jogo encontrado.");
            return;
        }

        int indiceRegistro = selecionarJogo(registros, "Selecione o jogo para remover");
        if (indiceRegistro == -1) {
            return;
        }

        String[] dados = registros.get(indiceRegistro).split(",", -1);
        int confirmacao = JOptionPane.showConfirmDialog(
            null,
            "Tem certeza de que deseja remover \"" + dados[0] + "\"?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            registros.remove(indiceRegistro);
            if (escreverRegistros(registros)) {
                JOptionPane.showMessageDialog(null, "Jogo removido com sucesso!");
            }
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
