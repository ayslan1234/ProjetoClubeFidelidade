package view;

import exception.PontuacaoInsuficienteException;
import interfaces.Bonificavel;
import model.Beneficio;
import model.MembroBasico;
import model.MembroClube;
import model.MembroPremium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tela principal do sistema: formulário, botões de ação e área de relatório.
 * <p>
 * Os membros ficam apenas em memória, na lista {@code membros}; ao fechar o
 * programa os dados são perdidos.
 */
public class TelaClube extends JFrame implements ActionListener, WindowListener {
    private JLabel lblTitulo, lblId, lblNome, lblEmail, lblPlano, lblValor, lblBeneficio;
    private JTextField txtId, txtNome, txtEmail, txtValor;
    private JComboBox<String> cbPlano;
    private JComboBox<Beneficio> cbBeneficio;
    private JButton btnCadastrar, btnPesquisar, btnAcumular, btnResgatar;
    private JButton btnOrdenar, btnRanking, btnLimpar, btnSair;
    private JTextArea txtRelatorio;
    private JScrollPane scrollRelatorio;
    private Dimension dimLabel, dimButton;

    private List<MembroClube> membros = new ArrayList<>();

    public TelaClube() {
        dimLabel = new Dimension(130, 20);
        dimButton = new Dimension(130, 25);

        this.setSize(620, 600);
        this.setTitle("Clube de Assinaturas e Fidelidade");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // o fechamento é tratado em windowClosing()
        this.setLayout(null);
        this.addWindowListener(this);

        lblTitulo = new JLabel("Clube Fidelidade - Membros e Pontos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setLocation(25, 12);
        lblTitulo.setSize(400, 25);
        this.add(lblTitulo);

        lblId = new JLabel("ID do membro:");
        lblId.setLocation(25, 50);
        lblId.setSize(dimLabel);
        this.add(lblId);

        lblNome = new JLabel("Nome:");
        lblNome.setLocation(25, 80);
        lblNome.setSize(dimLabel);
        this.add(lblNome);

        lblEmail = new JLabel("E-mail:");
        lblEmail.setLocation(25, 110);
        lblEmail.setSize(dimLabel);
        this.add(lblEmail);

        lblPlano = new JLabel("Plano:");
        lblPlano.setLocation(25, 140);
        lblPlano.setSize(dimLabel);
        this.add(lblPlano);

        lblValor = new JLabel("Valor da compra R$:");
        lblValor.setLocation(25, 170);
        lblValor.setSize(dimLabel);
        this.add(lblValor);

        lblBeneficio = new JLabel("Benefício:");
        lblBeneficio.setLocation(25, 200);
        lblBeneficio.setSize(dimLabel);
        this.add(lblBeneficio);

        txtId = new JTextField("");
        txtId.setLocation(160, 50);
        txtId.setSize(100, 22);
        this.add(txtId);

        txtNome = new JTextField("");
        txtNome.setLocation(160, 80);
        txtNome.setSize(300, 22);
        this.add(txtNome);

        txtEmail = new JTextField("");
        txtEmail.setLocation(160, 110);
        txtEmail.setSize(300, 22);
        this.add(txtEmail);

        cbPlano = new JComboBox<>(new String[]{"Básico", "Premium"});
        cbPlano.setLocation(160, 140);
        cbPlano.setSize(150, 22);
        this.add(cbPlano);

        txtValor = new JTextField("");
        txtValor.setLocation(160, 170);
        txtValor.setSize(100, 22);
        this.add(txtValor);

        cbBeneficio = new JComboBox<>(Beneficio.values());
        cbBeneficio.setLocation(160, 200);
        cbBeneficio.setSize(300, 22);
        this.add(cbBeneficio);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setLocation(25, 240);
        btnCadastrar.setSize(dimButton);
        btnCadastrar.addActionListener(this);
        this.add(btnCadastrar);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setLocation(165, 240);
        btnPesquisar.setSize(dimButton);
        btnPesquisar.addActionListener(this);
        this.add(btnPesquisar);

        btnAcumular = new JButton("Acumular");
        btnAcumular.setLocation(305, 240);
        btnAcumular.setSize(dimButton);
        btnAcumular.addActionListener(this);
        this.add(btnAcumular);

        btnResgatar = new JButton("Resgatar");
        btnResgatar.setLocation(445, 240);
        btnResgatar.setSize(dimButton);
        btnResgatar.addActionListener(this);
        this.add(btnResgatar);

        btnOrdenar = new JButton("Ordenar por ID");
        btnOrdenar.setLocation(25, 275);
        btnOrdenar.setSize(dimButton);
        btnOrdenar.addActionListener(this);
        this.add(btnOrdenar);

        btnRanking = new JButton("Ranking");
        btnRanking.setLocation(165, 275);
        btnRanking.setSize(dimButton);
        btnRanking.addActionListener(this);
        this.add(btnRanking);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setLocation(305, 275);
        btnLimpar.setSize(dimButton);
        btnLimpar.addActionListener(this);
        this.add(btnLimpar);

        btnSair = new JButton("Sair");
        btnSair.setLocation(445, 275);
        btnSair.setSize(dimButton);
        btnSair.addActionListener(this);
        this.add(btnSair);

        txtRelatorio = new JTextArea("");
        txtRelatorio.setEditable(false);
        txtRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollRelatorio = new JScrollPane(txtRelatorio);
        scrollRelatorio.setLocation(25, 315);
        scrollRelatorio.setSize(550, 230);
        this.add(scrollRelatorio);

        carregarMembrosExemplo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCadastrar) {
            cadastrar();
        } else if (e.getSource() == btnPesquisar) {
            pesquisar();
        } else if (e.getSource() == btnAcumular) {
            acumularPontos();
        } else if (e.getSource() == btnResgatar) {
            resgatarBeneficio();
        } else if (e.getSource() == btnOrdenar) {
            ordenarPorId();
        } else if (e.getSource() == btnRanking) {
            exibirRanking();
        } else if (e.getSource() == btnLimpar) {
            limparCampos();
            txtRelatorio.setText("");
        } else if (e.getSource() == btnSair) {
            sair();
        }
    }

    private void cadastrar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();

            if (id <= 0) {
                mostrarAviso("O ID deve ser maior que zero.");
                return;
            }
            if (nome.isEmpty() || email.isEmpty()) {
                mostrarAviso("Preencha o nome e o e-mail do membro.");
                return;
            }

            MembroClube membro;
            if (cbPlano.getSelectedIndex() == 0) {
                membro = new MembroBasico(id, nome, email);
            } else {
                membro = new MembroPremium(id, nome, email);
            }

            if (membros.contains(membro)) {
                mostrarAviso("Já existe um membro cadastrado com o ID " + id + ".");
                return;
            }

            membros.add(membro);
            txtRelatorio.append("Adesão: " + nome + " entrou no plano " + membro.getPlano() + "\n");
            JOptionPane.showMessageDialog(
                    this,
                    "Bem-vindo(a) ao clube, " + nome + "!",
                    "Adesão realizada",
                    JOptionPane.INFORMATION_MESSAGE
            );
            limparCampos();
        } catch (NumberFormatException ex) {
            mostrarErro("ID inválido! Digite apenas números inteiros.");
        }
    }

    private void pesquisar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            MembroClube membro = buscarMembro(id);
            if (membro == null) {
                mostrarAviso("Nenhum membro encontrado com o ID " + id + ".");
                return;
            }
            txtNome.setText(membro.getNome());
            txtEmail.setText(membro.getEmail());
            cbPlano.setSelectedItem(membro.getPlano());
            txtRelatorio.append("\n" + membro.exibirDetalhes() + "\n");
        } catch (NumberFormatException ex) {
            mostrarErro("Informe um ID numérico para pesquisar.");
        }
    }

    private void acumularPontos() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            // aceita tanto 150,90 quanto 150.90
            double valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));

            MembroClube membro = buscarMembro(id);
            if (membro == null) {
                mostrarAviso("Nenhum membro encontrado com o ID " + id + ".");
                return;
            }

            int pontosAntes = membro.getPontos();
            String nivelAntes = membro.getNivel();

            if (membro.acumularPontos(valor)) {
                int ganhos = membro.getPontos() - pontosAntes;
                txtRelatorio.append(String.format("Compra de R$ %.2f -> %s ganhou %d pontos (saldo: %d)%n",
                        valor, membro.getNome(), ganhos, membro.getPontos()));
                if (!nivelAntes.equals(membro.getNivel())) {
                    txtRelatorio.append("*** " + membro.getNome() + " subiu para o nível " + membro.getNivel() + "! ***\n");
                }
                txtValor.setText("");
            } else {
                mostrarAviso("O valor da compra deve ser maior que zero.");
            }
        } catch (NumberFormatException ex) {
            mostrarErro("ID ou valor da compra inválido! Use números (ex: 150,90).");
        }
    }

    private void resgatarBeneficio() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            MembroClube membro = buscarMembro(id);
            if (membro == null) {
                mostrarAviso("Nenhum membro encontrado com o ID " + id + ".");
                return;
            }

            Beneficio beneficio = (Beneficio) cbBeneficio.getSelectedItem();
            membro.resgatarPontos(beneficio.getCustoPontos());

            txtRelatorio.append("Resgate: " + beneficio.getDescricao() + " para " + membro.getNome() +
                    " (saldo: " + membro.getPontos() + " pts)\n");
            JOptionPane.showMessageDialog(
                    this,
                    "Benefício resgatado com sucesso!\n" + beneficio.getDescricao(),
                    "Resgate realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            mostrarErro("Informe um ID numérico para fazer o resgate.");
        } catch (PontuacaoInsuficienteException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Pontuação Insuficiente",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void ordenarPorId() {
        if (membros.isEmpty()) {
            mostrarAviso("Nenhum membro cadastrado.");
            return;
        }
        Collections.sort(membros);

        txtRelatorio.setText("");
        txtRelatorio.append("========== MEMBROS ORDENADOS POR ID ==========\n");
        txtRelatorio.append(cabecalho() + "\n");
        for (MembroClube m : membros) {
            txtRelatorio.append(m + "\n");
        }
        txtRelatorio.append("Total de membros: " + membros.size() + "\n");
    }

    private void exibirRanking() {
        if (membros.isEmpty()) {
            mostrarAviso("Nenhum membro cadastrado.");
            return;
        }
        Collections.sort(membros, (m1, m2) -> Integer.compare(m2.getPontos(), m1.getPontos()));

        txtRelatorio.setText("");
        txtRelatorio.append("============ RANKING POR PONTUAÇÃO ============\n");
        txtRelatorio.append("Pos  " + cabecalho() + "\n");
        int posicao = 1;
        for (MembroClube m : membros) {
            txtRelatorio.append(String.format("%2dº  %s%n", posicao, m));
            posicao++;
        }

        int totalPontos = 0;
        for (Bonificavel b : membros) {
            totalPontos += b.getPontos();
        }
        txtRelatorio.append(String.format("Pontos em circulação: %d (R$ %.2f)%n",
                totalPontos, Bonificavel.converterEmReais(totalPontos)));
    }

    private MembroClube buscarMembro(int id) {
        for (MembroClube m : membros) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    private String cabecalho() {
        return String.format("%-5s %-20s %-8s %7s  %-6s", "ID", "Nome", "Plano", "Pontos", "Nível");
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtValor.setText("");
        cbPlano.setSelectedIndex(0);
        cbBeneficio.setSelectedIndex(0);
        txtId.requestFocus();
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro, verifique!", JOptionPane.ERROR_MESSAGE);
    }

    private void sair() {
        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair? Os dados ficam só na memória e serão perdidos.",
                "Sair do sistema",
                JOptionPane.YES_NO_OPTION
        );
        if (opcao == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Cadastra quatro membros de exemplo para o sistema não abrir vazio.
     */
    private void carregarMembrosExemplo() {
        MembroClube m1 = new MembroPremium(105, "Ana Souza", "ana@email.com");
        m1.acumularPontos(320.00);
        MembroClube m2 = new MembroBasico(101, "Bruno Lima", "bruno@email.com");
        m2.acumularPontos(180.50);
        MembroClube m3 = new MembroPremium(110, "Carla Mendes", "carla@email.com");
        m3.acumularPontos(95.00);
        MembroClube m4 = new MembroBasico(103, "Diego Alves", "diego@email.com");
        m4.acumularPontos(540.00);

        membros.add(m1);
        membros.add(m2);
        membros.add(m3);
        membros.add(m4);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        txtRelatorio.append("Sistema iniciado. " + membros.size() + " membros carregados na memória.\n");
        txtRelatorio.append("Use 'Ordenar por ID' ou 'Ranking' para listar os membros.\n");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        sair();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
