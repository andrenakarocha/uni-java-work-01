# 📚 Sistema Integrado de Biblioteca e Restaurante

Este projeto consiste em dois sistemas de gestão independentes para auxiliar na administração de bibliotecas e restaurantes.

## 🌟 Funcionalidades

### 📖 Sistema de Biblioteca
- 📕 Gerenciamento completo de acervo (cadastro, consulta e remoção de livros)
- 👥 Cadastro e gestão de membros
- 📋 Controle de empréstimos e devoluções
- 💾 Persistência de dados em arquivos de texto
- 🖥️ Interface interativa via terminal

### 🍽️ Sistema de Restaurante
- 🍕 Gerenciamento de cardápio (adicionar/remover/consultar pratos)
- 🧾 Criação e visualização de pedidos
- 💰 Cálculo automático de valores totais
- 💾 Persistência de dados em arquivos de texto
- 🖥️ Interface interativa via terminal

## 🚀 Como Executar

### Requisitos
- ☕ Java JDK 8 ou superior
- 🛠️ Maven (opcional, para compilação)

### Compilando o projeto
```bash
# Usando Maven
mvn clean compile

# Usando javac diretamente
javac -d target/classes src/main/java/biblioteca/*.java src/main/java/biblioteca/modelo/*.java src/main/java/restaurante/*.java src/main/java/restaurante/modelo/*.java
```

### Executando os sistemas

#### Sistema de Biblioteca
```bash
# Usando Maven
mvn exec:java -Dexec.mainClass="biblioteca.SistemaBiblioteca"

# Usando java diretamente
java -cp target/classes biblioteca.SistemaBiblioteca
```

#### Sistema de Restaurante
```bash
# Usando Maven
mvn exec:java -Dexec.mainClass="restaurante.SistemaRestaurante"

# Usando java diretamente
java -cp target/classes restaurante.SistemaRestaurante
```

## 📋 Guia de Uso

### Sistema de Biblioteca

Ao iniciar o sistema, você verá um menu com as seguintes opções:

1. **Visualizar Livros** - Mostra todos os livros cadastrados
2. **Adicionar Livro** - Cadastra um novo livro no sistema
3. **Remover Livro** - Remove um livro do sistema
4. **Visualizar Membros** - Lista todos os membros cadastrados
5. **Registrar Novo Membro** - Cadastra um novo membro
6. **Visualizar Empréstimos** - Mostra os empréstimos ativos
7. **Realizar Empréstimo** - Registra um novo empréstimo
8. **Devolver Livro** - Processa a devolução de um livro
9. **Salvar Dados** - Salva todos os dados em arquivo
10. **Carregar Dados** - Carrega dados de um arquivo existente
0. **Sair** - Encerra o sistema

### Sistema de Restaurante

Ao iniciar o sistema, você verá um menu com as seguintes opções:

1. **Visualizar Cardápio** - Mostra todos os pratos disponíveis
2. **Criar Novo Pedido** - Inicia um novo pedido para um cliente
3. **Visualizar Pedidos** - Lista todos os pedidos registrados
4. **Adicionar Prato ao Cardápio** - Cadastra um novo prato
5. **Remover Prato do Cardápio** - Remove um prato existente
6. **Salvar Dados** - Salva todos os dados em arquivos
7. **Carregar Dados** - Carrega dados de arquivos existentes
0. **Sair** - Encerra o sistema

## 📁 Estrutura do Projeto

```
src/
├── main/
│   └── java/
│       ├── biblioteca/               # Package do sistema de biblioteca
│       │   ├── Biblioteca.java       # Classe principal da biblioteca
│       │   ├── SistemaBiblioteca.java # Interface de terminal
│       │   └── modelo/               # Modelos de dados da biblioteca
│       │       ├── Emprestimo.java   # Representa um empréstimo
│       │       ├── Livro.java        # Representa um livro
│       │       └── Membro.java       # Representa um membro
│       │
│       └── restaurante/              # Package do sistema de restaurante
│           ├── Cardapio.java         # Gerencia o cardápio do restaurante
│           ├── SistemaRestaurante.java # Interface de terminal
│           └── modelo/               # Modelos de dados do restaurante
│               ├── Pedido.java       # Representa um pedido
│               └── Prato.java        # Representa um prato
│
└── test/                             # Testes unitários
    └── java/
        └── restaurante/              # Testes do sistema de restaurante
            ├── CardapioTest.java     # Testes do cardápio
            ├── PedidoTest.java       # Testes de pedidos
            └── PratoTest.java        # Testes de pratos
```

## 🔍 Recursos Adicionais

- 🛡️ Os sistemas incluem validações para evitar operações inválidas
- 📊 Fácil visualização de dados através de relatórios no terminal
- 🔄 Persistência de dados para manter informações entre sessões

## ⚙️ Tecnologias Utilizadas

- Java 8+
- Entrada/Saída de arquivos Java (java.io)
- Coleções Java (java.util)
- JUnit para testes unitários

## 📝 Observações

- Os arquivos de dados são salvos no formato de texto, tornando fácil a inspeção manual se necessário
- O sistema já vem com alguns dados de exemplo para facilitar os testes iniciais
- Para um melhor funcionamento, execute o sistema em um terminal que suporte caracteres UTF-8

---

📄 Projeto desenvolvido para fins educacionais como parte de um trabalho acadêmico.