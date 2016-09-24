package br.com.eventoseartigos.sefd.annotation;

/**
 * Created by gilmar on 17/09/16.
 */
public interface Transacao {
    // Executa a transacao em uma thread separada
    public void executar() throws Exception;
    // Atualizar a view sincronizadda com a thead de interface
    public void atualizarView();
}
