package tarefasdbteste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void adicionar(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (descricao, prioridade, concluida) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getDescricao());
            stmt.setString(2, tarefa.getPrioridade());
            stmt.setInt(3, tarefa.isConcluida() ? 1 : 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar tarefa: " + e.getMessage());
        }
    }

    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas ORDER BY id";
        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tarefas.add(new Tarefa(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getInt("concluida") == 1,
                        rs.getString("prioridade")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }
        return tarefas;
    }

    public void atualizarStatus(int id, boolean concluida) {
        String sql = "UPDATE tarefas SET concluida = ?, data_conclusao = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, concluida ? 1 : 0);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status: " + e.getMessage());
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover tarefa: " + e.getMessage());
        }
    }
}
