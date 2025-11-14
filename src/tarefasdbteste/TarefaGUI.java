package tarefasdbteste;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TarefaGUI extends JFrame {
    private DefaultListModel<String> listaModel = new DefaultListModel<>();
    private JList<String> listaTarefas = new JList<>(listaModel);
    private JTextField campoDescricao = new JTextField(20);
    private JComboBox<String> comboPrioridade = new JComboBox<>(new String[]{"baixa", "média", "alta"});
    private TarefaDAO dao = new TarefaDAO();

    public TarefaGUI() {
        setTitle("Gerenciador de Tarefas - SQLite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Descrição:"));
        painelTopo.add(campoDescricao);
        painelTopo.add(new JLabel("Prioridade:"));
        painelTopo.add(comboPrioridade);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> adicionarTarefa());
        painelTopo.add(btnAdicionar);
        add(painelTopo, BorderLayout.NORTH);
        
        listaTarefas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(listaTarefas);
        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnConcluir = new JButton("Concluir");
        JButton btnRemover = new JButton("Remover");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        btnConcluir.addActionListener(e -> concluirTarefa());
        btnRemover.addActionListener(e -> removerTarefa());
        btnAtualizar.addActionListener(e -> carregarTarefas());

        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarTarefas();
        setVisible(true);
    }

    private void adicionarTarefa() {
        String descricao = campoDescricao.getText().trim();
        String prioridade = (String) comboPrioridade.getSelectedItem();

        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite uma descrição para a tarefa!");
            return;
        }

        dao.adicionar(new Tarefa(descricao, prioridade));
        campoDescricao.setText("");
        carregarTarefas();
    }

    private void concluirTarefa() {
        int index = listaTarefas.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para concluir!");
            return;
        }

        String item = listaModel.get(index);
        int id = Integer.parseInt(item.substring(1, item.indexOf(']')));
        dao.atualizarStatus(id, true);
        carregarTarefas();
    }

    private void removerTarefa() {
        int index = listaTarefas.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para remover!");
            return;
        }

        String item = listaModel.get(index);
        int id = Integer.parseInt(item.substring(1, item.indexOf(']')));
        dao.remover(id);
        carregarTarefas();
    }

    private void carregarTarefas() {
        listaModel.clear();
        List<Tarefa> tarefas = dao.listar();
        for (Tarefa t : tarefas) {
            listaModel.addElement(t.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TarefaGUI::new);
    }
}
