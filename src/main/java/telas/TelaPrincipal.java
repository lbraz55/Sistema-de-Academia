package telas;

import java.beans.PropertyVetoException;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Janela principal exibida após o login. Hospeda as telas internas
 * (JInternalFrame) do sistema em um JDesktopPane e dá acesso a elas pelo menu.
 *
 * Esta classe não fazia parte do projeto original: ela foi escrita na
 * reorganização do repositório, porque a TelaPrincipal era referenciada pelo
 * login mas nunca chegou a ser implementada.
 */
public class TelaPrincipal extends JFrame {

    private final JDesktopPane desktop = new JDesktopPane();

    private CadastroCliente cadastroCliente;
    private AreaAluno areaAluno;
    private Aulas aulas;

    public TelaPrincipal() {
        super("Sistema de Academia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(desktop);
        setJMenuBar(criarMenu());
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private JMenuBar criarMenu() {
        JMenuItem itemCadastro = new JMenuItem("Cadastro de Clientes");
        itemCadastro.addActionListener(e -> {
            if (cadastroCliente == null || cadastroCliente.isClosed()) {
                cadastroCliente = new CadastroCliente();
            }
            abrir(cadastroCliente);
        });

        JMenuItem itemAluno = new JMenuItem("Área do Aluno");
        itemAluno.addActionListener(e -> {
            if (areaAluno == null || areaAluno.isClosed()) {
                areaAluno = new AreaAluno();
            }
            abrir(areaAluno);
        });

        JMenuItem itemAulas = new JMenuItem("Aulas");
        itemAulas.addActionListener(e -> {
            if (aulas == null || aulas.isClosed()) {
                aulas = new Aulas();
            }
            abrir(aulas);
        });

        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));

        JMenu menuTelas = new JMenu("Telas");
        menuTelas.add(itemCadastro);
        menuTelas.add(itemAluno);
        menuTelas.add(itemAulas);
        menuTelas.addSeparator();
        menuTelas.add(itemSair);

        JMenuBar barra = new JMenuBar();
        barra.add(menuTelas);
        return barra;
    }

    /**
     * Mostra a tela interna no desktop. Se ela já estiver aberta, apenas a traz
     * para frente, evitando janelas duplicadas.
     */
    private void abrir(JInternalFrame tela) {
        if (tela.getParent() == null) {
            // As telas originais não definem esses atributos (padrão false no
            // NetBeans), o que impediria o usuário de fechá-las.
            tela.setClosable(true);
            tela.setIconifiable(true);
            desktop.add(tela);
        }
        tela.setVisible(true);
        try {
            tela.setSelected(true);
        } catch (PropertyVetoException ignored) {
            // Seleção vetada: a tela continua visível, só não recebe o foco.
        }
        tela.toFront();
    }
}
