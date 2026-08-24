# Task Manager — Java

[🇺🇸 English Version](README.md)

Uma aplicação de **gerenciamento de tarefas via console desenvolvida com Java Core**.

O projeto foi criado para praticar o desenvolvimento de uma aplicação Java completa utilizando programação orientada a objetos, arquitetura em camadas, collections, tratamento de exceções, regras de negócio e persistência em arquivos.

---

## 📌 Sobre a Aplicação

O Task Manager permite que vários usuários gerenciem suas próprias tarefas através de uma interface no console.

Os usuários podem:

* Criar e selecionar usuários
* Criar tarefas
* Listar tarefas
* Editar tarefas
* Excluir tarefas
* Marcar tarefas como concluídas
* Organizar tarefas por prioridade e categoria
* Criar tarefas simples ou recorrentes
* Salvar e restaurar os dados entre as execuções

Cada tarefa possui informações como:

```text
ID
Título
Descrição
Data de vencimento
Prioridade
Status
Categoria
Tipo
```

Atualmente, a aplicação funciona inteiramente com **Java Core**, sem frameworks ou banco de dados.

---

## 📁 O Que Existe no Projeto

O projeto é dividido em diferentes pacotes de acordo com suas responsabilidades:

```text
src/
│
├── application/
│   └── Main.java
│
├── data/
│   └── file.txt
│
├── model/
│   ├── entities/
│   ├── entities/enums/
│   └── exceptions/
│
├── persistance/
│   ├── Exportable.java
│   └── FilePersistance.java
│
├── repository/
│   ├── Repository.java
│   ├── InMemoryTaskRepository.java
│   └── InMemoryUserRepository.java
│
├── service/
│   ├── TaskService.java
│   └── UserService.java
│
└── ui/
    └── ConsoleUI.java
```

Principais componentes:

* **User** — representa um usuário e suas tarefas
* **Task** — classe abstrata base para as tarefas
* **SimpleTask** — representa uma tarefa comum
* **RecurringTask** — representa um tipo de tarefa recorrente
* **Category** — organiza as tarefas
* **Priority** — define a prioridade da tarefa
* **TaskStatus** — define se uma tarefa está pendente ou concluída
* **Repositories** — armazenam usuários e tarefas em memória
* **Services** — concentram regras da aplicação e de negócio
* **ConsoleUI** — controla a interação com o usuário
* **FilePersistance** — salva e recupera os dados da aplicação

---

## 🏗 Arquitetura

O projeto segue uma **arquitetura básica em camadas**:

```text
Console UI
    ↓
Services
    ↓
Repositories
    ↓
Domain Entities
```

A persistência interage com o domínio e os repositórios para salvar e restaurar os dados:

```text
File Persistence
      ↕
Repositories / Entities
```

### Camada de UI

Responsável por:

* Exibir os menus
* Ler as entradas do usuário
* Mostrar resultados e erros
* Chamar os serviços

### Camada de Service

Responsável pelas regras da aplicação, incluindo:

* Criação e remoção de usuários
* Validação de usuários duplicados
* Criação e remoção de tarefas
* Conclusão de tarefas
* Validação de usuários e tarefas
* Regras de pertencimento das tarefas

### Camada de Repository

Responsável pelo armazenamento das entidades em memória.

O projeto utiliza uma interface genérica:

```java
public interface Repository<T> {

    void addItem(T item);

    T findById(String id);

    List<T> findAll();

    void delete(String id);
}
```

As implementações atuais utilizam `HashMap`:

```text
InMemoryUserRepository
InMemoryTaskRepository
```

### Camada de Domínio

Contém as principais entidades, enums e validações de domínio.

Exemplos:

```text
User
Task
SimpleTask
RecurringTask
Category
Priority
TaskStatus
```

---

## 🎯 Contexto do Projeto

Este projeto foi desenvolvido como uma aplicação prática de Java Core após o estudo dos principais fundamentos da linguagem.

Seu objetivo principal é sair de exercícios isolados e praticar como diferentes partes de uma aplicação real se comunicam.

Entre os conceitos aplicados estão:

* Programação orientada a objetos
* Encapsulamento
* Herança
* Abstração
* Polimorfismo
* Interfaces
* Generics
* Collections
* `ArrayList`
* `HashMap`
* Enums
* Exceções customizadas
* `LocalDate`
* Manipulação de arquivos
* Regras de negócio
* Repository Pattern
* Camada de Service
* Separação de responsabilidades

O projeto evita frameworks propositalmente para permitir uma compreensão melhor da estrutura da aplicação e dos fundamentos de Java antes de avançar para tecnologias como JDBC e Spring Boot.

---

## ▶️ Como Executar

### 1. Clone o repositório

```bash
git clone <repository-url>
```

Entre na pasta do projeto:

```bash
cd taskManager
```

### 2. Abra o projeto

Abra o projeto utilizando uma IDE Java, como:

* IntelliJ IDEA
* Eclipse
* VS Code com suporte para Java

### 3. Configure o JDK

Certifique-se de que o projeto está utilizando um Java JDK válido.

### 4. Configure o caminho do arquivo de persistência

O projeto utiliza atualmente um arquivo para armazenar os dados da aplicação.

Caso o `Main.java` possua um caminho absoluto como:

```java
"C:\\Users\\...\\taskManager\\src\\data\\file.txt"
```

altere-o de acordo com o seu ambiente.

### 5. Execute a aplicação

Execute:

```text
application.Main
```

O menu será exibido no console e a aplicação estará pronta para uso.

---

## ⚙️ Requisitos

Para executar o projeto é necessário:

* Java JDK instalado
* Uma IDE compatível com Java ou ambiente de linha de comando
* Git, caso queira clonar o repositório

Nenhuma biblioteca externa, framework ou servidor de banco de dados é necessário atualmente.

O projeto utiliza apenas a biblioteca padrão do Java.

---

## 🧠 Notas Interessantes

O projeto não armazena as tarefas apenas dentro de cada `User`.

As tarefas também são administradas através de um repositório próprio, permitindo praticar a separação entre objetos de domínio, acesso aos dados e lógica da aplicação.

O projeto também implementa diferentes tipos de tarefas através de herança:

```text
           Task
          /    \
 SimpleTask   RecurringTask
```

A classe `RecurringTask` já existe como um tipo específico dentro do domínio, embora o comportamento automático de recorrência ainda não tenha sido implementado.

Os dados da aplicação são persistidos utilizando um formato próprio em arquivo de texto em vez de um banco de dados. Essa decisão foi intencional para praticar manipulação de arquivos em Java antes da introdução de JDBC ou tecnologias ORM.

---

## 🔧 Pequenas Correções Futuras

Algumas melhorias estão planejadas para versões futuras:

* [ ] Substituir o caminho absoluto de persistência por um caminho relativo ou configurável
* [ ] Restaurar corretamente os IDs das tarefas durante o carregamento
* [ ] Melhorar o tratamento de tarefas vencidas durante o carregamento do arquivo
* [ ] Persistir todas as informações da categoria
* [ ] Melhorar o comportamento das tarefas recorrentes
* [ ] Melhorar a separação entre as camadas UI, Service e Repository
* [ ] Refatorar a interface Repository para aceitar diferentes tipos de ID
* [ ] Adicionar testes automatizados com JUnit 5

Versões futuras também poderão explorar Maven, JDBC, MySQL e Spring Boot.

---

⭐ Desenvolvido como um projeto prático para fortalecer os fundamentos de **Java Core e arquitetura de software**.
