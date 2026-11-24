import javax.swing.JOptionPane;

public class CRUD {
    public static void main (String[] args){
        String [] opcoes = {"Create", "Remove", "Update", "Delete"};
        boolean continuar = true;

        while (continuar) {
            int escolha = JOptionPane.showOptionDialog(
                null,
                "Biblioteca de Jogos",
                "Bem vindo a Luderia",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );

            switch (escolha) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Create");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Remove");
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
}
