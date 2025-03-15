package biblioteca.modelo;

import java.util.*;

public class Emprestimo {
    private Livro livro;
    private Membro membro;
    private Date dataEmprestimo;

    public Emprestimo(Livro livro, Membro membro, Date dataEmprestimo) {
        this.livro = livro;
        this.membro = membro;
        this.dataEmprestimo = dataEmprestimo;
    }

    public Livro getLivro() {
        return livro;
    }
    public Membro getMembro() {
        return membro;
    }
    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "livro=" + livro +
                ", membro=" + membro +
                ", dataEmprestimo=" + dataEmprestimo +
                '}';
    }
}
