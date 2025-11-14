package tarefasdbteste;

public class Tarefa {
    private int id;
    private String descricao;
    private boolean concluida;
    private String prioridade;

    public Tarefa(String descricao, String prioridade) {
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.concluida = false;
    }

    public Tarefa(int id, String descricao, boolean concluida, String prioridade) {
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
        this.prioridade = prioridade;
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public boolean isConcluida() { return concluida; }
    public String getPrioridade() { return prioridade; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) %s",
                id,
                descricao,
                prioridade,
                concluida ? "✅" : "⏳");
    }
}
