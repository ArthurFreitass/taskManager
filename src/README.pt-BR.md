# Task Manager — Java

[🇺🇸 English Version](README.md)

Aplicação de **gerenciamento de tarefas via console desenvolvida com Java Core**, criada com foco no estudo de Programação Orientada a Objetos, arquitetura em camadas, Collections, Generics, tratamento de exceções e persistência em arquivos.

O sistema permite cadastrar usuários e gerenciar suas respectivas tarefas, incluindo criação, edição, exclusão e conclusão, além de prioridades, categorias, datas de vencimento e diferentes tipos de tarefa.

---

## 📑 Sumário

* [Sobre o Projeto](#-sobre-o-projeto)
* [Funcionalidades](#-funcionalidades)
* [Tecnologias e Conceitos](#-tecnologias-e-conceitos)
* [Arquitetura](#-arquitetura)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Modelo de Domínio](#-modelo-de-domínio)
* [Fluxo da Aplicação](#-fluxo-da-aplicação)
* [Persistência](#-persistência)
* [Formato do Arquivo](#-formato-do-arquivo)
* [Validações](#-validações)
* [Tratamento de Exceções](#-tratamento-de-exceções)
* [Como Executar](#️-como-executar)
* [Exemplo de Uso](#-exemplo-de-uso)
* [Limitações Atuais](#️-limitações-atuais)
* [Próximas Melhorias](#-próximas-melhorias)
* [Objetivos de Aprendizado](#-objetivos-de-aprendizado)

---

# 📌 Sobre o Projeto

O **Task Manager** é uma aplicação de linha de comando desenvolvida inteiramente com **Java Core**.

O sistema permite cadastrar múltiplos usuários, sendo que cada usuário possui sua própria coleção de tarefas.

Cada tarefa possui informações como:

* ID
* Título
* Descrição
* Data de vencimento
* Prioridade
* Status
* Categoria
* Tipo

Um dos principais objetivos do projeto foi evitar concentrar toda a aplicação dentro da classe `Main`, separando as responsabilidades em diferentes camadas.

A arquitetura geral segue aproximadamente o seguinte fluxo:

```text
Console UI
    ↓
Services
    ↓
Repositories
    ↓
Entidades de Domínio

Persistência em Arquivo
    ↕
Repositories / Entidades
```

O projeto atualmente não utiliza frameworks externos ou banco de dados.

---

# ✨ Funcionalidades

## 👤 Gerenciamento de Usuários

A aplicação permite:

* Listar usuários cadastrados
* Criar novos usuários
* Selecionar um usuário existente
* Trocar entre usuários
* Impedir usernames duplicados
* Localizar usuários através da camada de serviço

Cada usuário possui sua própria lista de tarefas.

A camada de serviço também possui suporte para remoção de usuários e exclusão de suas tarefas associadas.

---

## ✅ Gerenciamento de Tarefas

Após selecionar um usuário, é possível:

* Listar suas tarefas
* Criar uma nova tarefa
* Editar uma tarefa
* Excluir uma tarefa
* Marcar uma tarefa como concluída
* Voltar ao menu de usuários
* Encerrar a aplicação

---

## 📝 Informações de uma Tarefa

Cada tarefa contém:

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

---

## 🔥 Prioridades

O sistema possui três níveis de prioridade:

```java
LOW
MEDIUM
HIGH
```

Esses valores são representados pelo enum:

```java
Priority
```

---

## 📊 Status

Uma tarefa pode possuir os seguintes status:

```java
PENDING
COMPLETED
```

Toda nova tarefa começa automaticamente como:

```java
TaskStatus.PENDING
```

Quando marcada como concluída, seu status é alterado para:

```java
TaskStatus.COMPLETED
```

---

## 🔄 Tipos de Tarefa

Atualmente existem dois tipos:

```text
SimpleTask
RecurringTask
```

Ambos herdam da classe abstrata:

```java
Task
```

### `SimpleTask`

Representa uma tarefa simples.

Identificador utilizado na persistência:

```text
SIMPLE
```

### `RecurringTask`

Representa uma tarefa do tipo recorrente.

Identificador utilizado na persistência:

```text
RECURRING
```

No estado atual do projeto, `RecurringTask` representa um tipo diferente de tarefa, porém ainda não possui uma lógica automática de repetição ou agendamento.

---

## 🗂 Categorias

Cada tarefa possui uma categoria.

Uma `Category` contém:

```text
Nome
Descrição
```

Exemplo:

```text
Nome: Estudos
Descrição: Atividades relacionadas a programação e faculdade
```

---

# 🛠 Tecnologias e Conceitos

O projeto utiliza:

* Java
* Java Core
* Programação Orientada a Objetos
* Encapsulamento
* Herança
* Polimorfismo
* Abstração
* Classes abstratas
* Interfaces
* Generics
* Collections API
* `ArrayList`
* `HashMap`
* Enums
* Exceções personalizadas
* File I/O
* `BufferedReader`
* `BufferedWriter`
* `FileReader`
* `FileWriter`
* `LocalDate`
* `DateTimeFormatter`

Nenhum framework externo é necessário para executar a versão atual.

---

# 🏗 Arquitetura

O projeto utiliza uma arquitetura básica em camadas:

```text
UI
 ↓
Service
 ↓
Repository
 ↓
Domain
```

Cada camada possui uma responsabilidade diferente dentro da aplicação.

---

## 🖥 UI Layer

Pacote:

```text
ui
```

Classe principal:

```text
ConsoleUI
```

A interface de console é responsável por:

* Exibir menus
* Receber entradas do usuário
* Converter os valores digitados
* Exibir usuários e tarefas
* Exibir mensagens de sucesso
* Exibir mensagens de erro
* Chamar os serviços da aplicação

Fluxo básico:

```text
Usuário
   ↓
ConsoleUI
   ↓
TaskService / UserService
```

A interface não é responsável pela persistência dos dados.

---

## ⚙️ Service Layer

Pacote:

```text
service
```

Principais classes:

```text
TaskService
UserService
```

Essa camada concentra regras da aplicação e regras de negócio.

### `UserService`

Possui responsabilidades como:

* Validar usernames
* Criar usuários
* Impedir usuários duplicados
* Buscar usuários
* Remover usuários

### `TaskService`

Possui responsabilidades como:

* Adicionar tarefas
* Remover tarefas
* Concluir tarefas
* Verificar existência de usuários
* Verificar existência de tarefas
* Validar a propriedade da tarefa durante sua remoção

---

## 📦 Repository Layer

Pacote:

```text
repository
```

O projeto utiliza uma interface genérica:

```java
public interface Repository<T> {

    void addItem(T item);

    T findById(String id);

    List<T> findAll();

    void delete(String id);
}
```

As implementações atuais são:

```text
InMemoryTaskRepository
InMemoryUserRepository
```

Os dados são armazenados em memória através de `HashMap`.

### Repositório de usuários

Estrutura utilizada:

```java
Map<String, User>
```

O nome do usuário é utilizado como chave.

### Repositório de tarefas

Estrutura utilizada:

```java
Map<String, Task>
```

O ID da tarefa convertido para `String` é utilizado como chave.

---

## 🧩 Domain Layer

As principais entidades estão localizadas em:

```text
model.entities
```

Entidades:

```text
User
Task
SimpleTask
RecurringTask
Category
```

Enums:

```text
Priority
TaskStatus
```

As entidades representam os principais objetos e dados do domínio da aplicação.

---

## 💾 Persistence Layer

Pacote:

```text
persistance
```

Principais componentes:

```text
Exportable
FilePersistance
```

A interface `Exportable` define:

```java
String toFileFormat();
```

As entidades que precisam ser persistidas podem implementar esse contrato para converter seus dados para o formato utilizado no arquivo.

---

# 📁 Estrutura do Projeto

Estrutura simplificada:

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
│   │
│   ├── entities/
│   │   ├── Category.java
│   │   ├── Task.java
│   │   ├── SimpleTask.java
│   │   ├── RecurringTask.java
│   │   └── User.java
│   │
│   ├── entities/enums/
│   │   ├── Priority.java
│   │   └── TaskStatus.java
│   │
│   └── exceptions/
│       ├── DateException.java
│       ├── DomainException.java
│       ├── DuplicateUserException.java
│       ├── TaskNotFoundException.java
│       └── UserNotFoundException.java
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

---

# 🧩 Modelo de Domínio

## `Task`

`Task` é uma classe abstrata utilizada como base para os diferentes tipos de tarefa.

Seus principais atributos são:

```java
private final Integer id;
private String title;
private String description;
private LocalDate dueDate;
private Priority priority;
private TaskStatus status;
private Category category;
```

O ID é atualmente gerado através de um contador estático:

```java
private static int count = 0;
```

Quando uma tarefa é criada:

```java
this.id = ++count;
```

Seu status inicial é:

```java
TaskStatus.PENDING
```

A classe também possui validações relacionadas ao título, descrição, data, prioridade, status e categoria.

---

## `SimpleTask`

`SimpleTask` herda de:

```java
Task
```

Representa uma tarefa simples.

Seu identificador na persistência é:

```text
SIMPLE
```

---

## `RecurringTask`

`RecurringTask` também herda de:

```java
Task
```

Seu identificador na persistência é:

```text
RECURRING
```

Atualmente a classe é utilizada para representar um tipo diferente de tarefa, mas uma regra real de recorrência ainda não foi implementada.

---

## `User`

A entidade `User` possui:

```java
private String name;
private List<Task> tasksList;
```

Cada usuário mantém sua própria coleção de tarefas.

A classe fornece operações como:

```java
add(Task task)
remove(Task task)
```

---

## `Category`

A entidade `Category` possui:

```java
private String name;
private String description;
```

Cada tarefa está associada a uma categoria.

---

# 🔄 Fluxo da Aplicação

A execução começa pela classe:

```java
Main
```

O fluxo geral é:

```text
Inicia a aplicação
      ↓
Cria o Scanner
      ↓
Cria os repositories
      ↓
Cria FilePersistance
      ↓
Carrega dados anteriores
      ↓
Cria os services
      ↓
Cria ConsoleUI
      ↓
Exibe o menu
      ↓
Usuário utiliza a aplicação
      ↓
Sai da interface
      ↓
Salva os dados
      ↓
Fecha o Scanner
```

A classe `Main` funciona principalmente como ponto de configuração e inicialização das dependências.

---

# 👤 Menu de Usuários

Ao iniciar a aplicação:

```text
---- BEM-VINDO AO TaskManager ----

Escolha uma das opções:

[1] - Listar usuários existentes
[2] - Criar novo usuário
[3] - Selecionar usuário
[0] - Sair
```

Depois que um usuário é selecionado, a aplicação abre o menu de tarefas.

---

# ✅ Menu de Tarefas

```text
---- MENU DE TAREFAS ----

[1] - Listar tarefas
[2] - Adicionar tarefa
[3] - Editar tarefa
[4] - Excluir tarefa
[5] - Marcar como concluída
[6] - Voltar / Trocar usuário
[0] - Sair
```

---

# 💾 Persistência

Os dados da aplicação são armazenados em um arquivo de texto.

A persistência é realizada pela classe:

```java
FilePersistance
```

Quando a aplicação é iniciada:

```java
filePersistance.load(...);
```

Os dados existentes são carregados para os repositories em memória.

Quando a aplicação é encerrada:

```java
filePersistance.save(...);
```

Os dados atuais são escritos novamente no arquivo.

Isso permite que usuários e tarefas continuem existindo entre diferentes execuções do programa.

---

# 📄 Formato do Arquivo

A aplicação utiliza um formato próprio baseado em valores separados por ponto e vírgula.

## Usuários

Formato:

```text
USER;username
```

Exemplo:

```text
USER;Arthur
```

---

## Tarefas

Formato:

```text
TASK;id;title;description;dueDate;priority;status;category;type
```

Exemplo:

```text
TASK;1;Estudar Java;Revisar Collections;30/08/2026;HIGH;PENDING;Estudos;SIMPLE
```

Outro exemplo:

```text
TASK;2;Treinar;Treino na academia;31/08/2026;MEDIUM;COMPLETED;Saude;RECURRING
```

O formato utilizado para datas é:

```text
dd/MM/yyyy
```

---

# 🛡 Validações

O projeto possui validações diretamente nas entidades e na camada de serviços.

## Usuário

O nome não pode ser:

```text
null
vazio
```

Também não é permitido cadastrar dois usuários com o mesmo nome.

---

## Título da Tarefa

Não pode ser:

```text
null
vazio
```

---

## Descrição da Tarefa

Não pode ser:

```text
null
vazia
```

---

## Data de Vencimento

Atualmente a data não pode ser:

```text
null
anterior à data atual
```

---

## Prioridade

Não pode ser:

```text
null
```

---

## Status

Não pode ser:

```text
null
```

---

## Categoria

A categoria associada à tarefa não pode ser:

```text
null
```

A classe `Category` também possui validações para nome e descrição.

---

# ⚠️ Tratamento de Exceções

O projeto utiliza exceções personalizadas para representar erros de domínio e situações inválidas.

Atualmente existem:

```text
DomainException
DateException
DuplicateUserException
TaskNotFoundException
UserNotFoundException
```

Elas são utilizadas em situações como:

* Nome de usuário inválido
* Usuário duplicado
* Usuário não encontrado
* Tarefa não encontrada
* Dados inválidos
* Data inválida
* Tarefa já concluída
* Tipo de tarefa inválido
* Tentativa de remover uma tarefa que não pertence ao usuário
* Tarefa encontrada no arquivo sem um usuário associado

A `ConsoleUI` captura essas exceções e exibe mensagens mais amigáveis ao usuário, evitando que erros esperados encerrem imediatamente a aplicação.

---

# ▶️ Como Executar

## Requisitos

Para executar o projeto é necessário:

* Java JDK instalado
* Uma IDE compatível com Java, como:

    * IntelliJ IDEA
    * Eclipse
    * VS Code com suporte para Java

O projeto utiliza apenas recursos da biblioteca padrão do Java e atualmente não possui dependências externas.

---

## 1. Clonar o Repositório

```bash
git clone <repository-url>
```

Entre na pasta do projeto:

```bash
cd taskManager
```

---

## 2. Abrir o Projeto

Abra o projeto utilizando sua IDE.

No IntelliJ IDEA:

```text
File
→ Open
→ Selecione a pasta do projeto
```

Configure um JDK válido caso seja necessário.

---

## 3. Configurar o Caminho de Persistência

A versão atual utiliza um caminho absoluto do Windows dentro da classe `Main`.

Exemplo:

```java
"C:\\Users\\Arthur Freitas\\IdeaProjects\\taskManager\\src\\data\\file.txt"
```

Portanto, ao executar o projeto em outro computador, esse caminho deve ser alterado.

Uma melhoria futura é utilizar um caminho relativo:

```java
"src/data/file.txt"
```

ou permitir que o caminho seja configurado externamente.

---

## 4. Executar a Aplicação

Execute a classe:

```text
application.Main
```

O menu principal será exibido no console.

---

# 💻 Exemplo de Uso

Ao iniciar:

```text
---- BEM-VINDO AO TaskManager ----

Escolha uma das opções:

[1] - Listar usuários existentes
[2] - Criar novo usuário
[3] - Selecionar usuário
[0] - Sair
```

Criando um usuário:

```text
Entre com um nome de usuário: Arthur

Usuário Arthur adicionado com sucesso!
```

Selecionando o usuário:

```text
Entre com o nome de um usuário: Arthur

Usuário Arthur selecionado!
```

Criando uma tarefa:

```text
Título: Estudar Java

Descrição: Estudar Collections e Generics

Data de vencimento DD/MM/YYYY: 30/08/2026

Prioridade LOW, MEDIUM, HIGH: HIGH

Categoria:
Nome: Estudos

Descrição: Estudos relacionados a programação

Tipo simples [1] ou recorrente [2]: 1
```

A nova tarefa será criada inicialmente com:

```text
Status: PENDING
Tipo: Simples
```

Ao listar as tarefas, são exibidas informações como:

```text
ID
Título
Descrição
Prioridade
Status
Data de vencimento
Categoria
Tipo
```

---

# ⚠️ Limitações Atuais

O projeto está funcional, porém ainda possui alguns pontos conhecidos que podem ser melhorados.

## IDs Durante o Carregamento

Os IDs das tarefas são escritos no arquivo de persistência.

Entretanto, durante o carregamento, novas instâncias de `Task` são criadas através do construtor normal.

Como o ID é gerado por um contador estático:

```java
this.id = ++count;
```

o ID original salvo no arquivo não é restaurado diretamente.

Uma futura implementação deverá seguir um comportamento semelhante a:

```text
Salvar ID
    ↓
Carregar ID original
    ↓
Restaurar objeto
    ↓
Atualizar contador corretamente
```

---

## Tarefas Atrasadas

A validação atual de `Task` impede uma data anterior a:

```java
LocalDate.now()
```

Isso funciona para impedir a criação de uma nova tarefa já vencida.

Porém, uma tarefa válida pode naturalmente ficar atrasada.

Exemplo:

```text
Tarefa criada: 20/08/2026
Vencimento:    25/08/2026
Programa aberto novamente: 26/08/2026
```

Nesse caso, o carregamento pode encontrar uma data que agora está no passado.

Uma melhoria futura deverá diferenciar:

```text
Criação de uma nova tarefa
```

de:

```text
Reconstrução de uma tarefa existente
```

---

## Persistência da Descrição da Categoria

Atualmente o arquivo da tarefa armazena o:

```text
nome da categoria
```

mas não armazena sua descrição.

Dessa forma, a descrição completa da categoria não é restaurada depois que a aplicação é reiniciada.

---

## Tarefas Recorrentes

`RecurringTask` existe como um tipo separado, porém ainda não possui uma regra real de recorrência.

Uma futura implementação poderá possuir algo como:

```java
private int intervalDays;
```

ou um modelo mais completo de recorrência.

---

## Tipo do ID no Repository

Atualmente:

```java
Repository<T>
```

utiliza:

```java
String
```

como tipo de ID para todas as entidades.

Uma futura refatoração poderá utilizar:

```java
Repository<T, ID>
```

permitindo:

```java
Repository<Task, Integer>
Repository<User, String>
```

---

## Separação Entre UI e Repository

Algumas operações da interface ainda acessam repositories indiretamente através dos services.

O objetivo de uma futura refatoração é manter de forma mais rígida:

```text
UI
 ↓
Service
 ↓
Repository
```

fazendo com que a UI não precise conhecer ou receber diretamente os repositories.

---

## Testes Automatizados

A versão atual ainda não possui uma suíte completa de testes automatizados.

A implementação de testes com **JUnit 5** é uma das principais próximas etapas do projeto.

---

# 🚀 Próximas Melhorias

* [ ] Adicionar JUnit 5
* [ ] Criar testes para `UserService`
* [ ] Criar testes para `TaskService`
* [ ] Criar testes de persistência
* [ ] Restaurar corretamente os IDs salvos
* [ ] Permitir o carregamento de tarefas atrasadas
* [ ] Persistir completamente os dados de `Category`
* [ ] Implementar comportamento real de recorrência
* [ ] Refatorar para `Repository<T, ID>`
* [ ] Remover exposição dos repositories para a UI
* [ ] Mover a edição de tarefas para `TaskService`
* [ ] Melhorar a estratégia de `equals()` e `hashCode()`
* [ ] Utilizar `isBlank()` onde for apropriado
* [ ] Melhorar a hierarquia das exceções
* [ ] Adicionar Maven
* [ ] Substituir o caminho absoluto por um caminho relativo/configurável
* [ ] Criar filtros de tarefas
* [ ] Criar ordenação de tarefas
* [ ] Buscar tarefas por categoria
* [ ] Filtrar por prioridade
* [ ] Filtrar por status
* [ ] Identificar tarefas atrasadas
* [ ] Adicionar JDBC
* [ ] Integrar MySQL
* [ ] Criar repositories baseados em banco de dados
* [ ] Futuramente desenvolver uma API REST com Spring Boot

---

# 📚 Objetivos de Aprendizado

O projeto foi desenvolvido com o objetivo de fortalecer fundamentos de Java e engenharia de software.

## Programação Orientada a Objetos

Conceitos praticados:

```text
Encapsulamento
Herança
Abstração
Polimorfismo
```

---

## Recursos da Linguagem Java

```text
Enums
Interfaces
Generics
Exceptions
Collections
LocalDate
File I/O
```

---

## Estrutura e Design de Software

```text
Entidades
Repositories
Services
Separação da UI
Regras de negócio
Persistência
Dependência através de interfaces
Separação de responsabilidades
```

---

## Próximos Tópicos de Estudo

```text
JUnit 5
Generics
Collections
Streams
SOLID
Maven
JDBC
MySQL
Spring Boot
```

---

# 🎯 Objetivo Principal

O principal objetivo do projeto não é apenas desenvolver um gerenciador de tarefas funcional.

Ele também representa uma etapa na transição entre:

```text
aprender sintaxe Java
```

e:

```text
projetar aplicações completas utilizando Java
```

O projeto busca desenvolver uma melhor compreensão sobre como diferentes partes de uma aplicação se comunicam e como as responsabilidades podem ser separadas entre:

```text
Entidades
Repositories
Services
Persistência
Interface do usuário
```

A estrutura atual também permite que o projeto continue evoluindo posteriormente com testes automatizados, banco de dados, JDBC e uma possível API REST utilizando Spring Boot.

---

⭐ **Este projeto continua sendo aprimorado como parte do processo de aprendizado e evolução em Java.**
