package biblioteca.modelo;

public class Membro {
    private String nome;
    private int id;
    private String email;

    public Membro(String nome, int id, String email) {
        this.nome = nome;
        this.id = id;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

//    Esse trecho de código é importante porque sobrescreve o método toString() da classe Object,
//    que é a classe base de todas as classes em Java. O método toString() é chamado implicitamente
//    quando você tenta imprimir o objeto ou quando você precisa de uma representação em string do objeto,
//    como ao salvar dados em um arquivo ou ao fazer log de informações.
//    Sem sobrescrever o método toString(), a chamada para System.out.println(membro) resultaria na impressão do
//    nome da classe seguido pelo hash code do objeto, algo como membro123. Ao sobrescrever toString(),
//    você obtém uma representação legível e mais informativa do objeto.
    @Override
    public String toString() {
        return "Membro{" +
                "nome='" + nome + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
