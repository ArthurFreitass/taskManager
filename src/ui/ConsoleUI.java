package ui;

import model.entities.Category;
import model.entities.RecurringTask;
import model.entities.SimpleTask;
import model.entities.Task;
import model.entities.User;
import model.entities.enums.Priority;
import model.exceptions.DateException;
import model.exceptions.DomainException;
import model.exceptions.DuplicateUserException;
import model.exceptions.TaskNotFoundException;
import model.exceptions.UserNotFoundException;
import service.TaskService;
import service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner sc;
    private final TaskService taskService;
    private final UserService userService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsoleUI(Scanner sc, TaskService taskService, UserService userService) {
        this.sc = sc;
        this.taskService = taskService;
        this.userService = userService;
    }

    public void showMenu() {
        int option = -1;

        do {
            System.out.println("\n---- BEM-VINDO AO TaskManager ----\n");
            System.out.println("Escolha uma das opções:\n");
            System.out.println("[1] - Listar usuários existentes");
            System.out.println("[2] - Criar novo usuário");
            System.out.println("[3] - Selecionar usuário");
            System.out.println("[0] - Sair");

            try {
                option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1:
                        listUsers();
                        break;
                    case 2:
                        createUser();
                        break;
                    case 3:
                        User currentUser = selectUser();
                        boolean exitApplication = taskMenu(currentUser);
                        if (exitApplication) {
                            option = 0;
                        }
                        break;
                    case 0:
                        System.out.println("\nSaindo do TaskManager...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (DuplicateUserException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (UserNotFoundException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (DomainException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Digite uma opção numérica válida!");
            }
        } while (option != 0);
    }

    private void listUsers() {
        System.out.println("\nOpção escolhida: [1] Listar usuários:\n");
        List<User> users = userService.getUserRepository().findAll();

        if (users == null || users.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }

    private void createUser() {
        System.out.println("\nOpção escolhida: [2] Criar usuário:\n");
        System.out.print("Entre com um nome de usuário: ");
        String name = sc.nextLine();

        userService.addUser(name);
        System.out.println("Usuário " + name + " adicionado com sucesso!");
    }

    private User selectUser() {
        System.out.println("\nOpção escolhida: [3] Selecionar usuário:\n");
        System.out.print("Entre com o nome de um usuário: ");
        String name = sc.nextLine();

        User user = userService.findUser(name);
        System.out.println("Usuário " + user.getName() + " selecionado!");
        return user;
    }

    private boolean taskMenu(User user) {
        checkUser(user);
        int option = -1;

        System.out.println("\nVocê entrou como " + user.getName());

        do {
            System.out.println("\n---- MENU DE TAREFAS ----\n");
            System.out.println("[1] - Listar tarefas");
            System.out.println("[2] - Adicionar tarefa");
            System.out.println("[3] - Editar tarefa");
            System.out.println("[4] - Excluir tarefa");
            System.out.println("[5] - Marcar como concluída");
            System.out.println("[6] - Voltar / Trocar usuário");
            System.out.println("[0] - Sair");

            try {
                option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1:
                        listTasks(user);
                        break;
                    case 2:
                        addTask(user);
                        break;
                    case 3:
                        editTask(user);
                        break;
                    case 4:
                        removeTask(user);
                        break;
                    case 5:
                        completeTask(user);
                        break;
                    case 6:
                        System.out.println("\nVoltando ao menu de usuários...");
                        return false;
                    case 0:
                        return true;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (TaskNotFoundException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (UserNotFoundException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (DateException e) {
                System.out.println("Erro na data: " + e.getMessage());
            } catch (DomainException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato DD/MM/YYYY.");
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido!");
            } catch (IllegalArgumentException e) {
                System.out.println("Valor informado inválido!");
            }
        } while (option != 0);

        return true;
    }

    private void listTasks(User user) {
        checkUser(user);
        System.out.println("\nTarefas de " + user.getName() + ":\n");

        if (user.getTasksList().isEmpty()) {
            System.out.println("Este usuário não possui tarefas.");
            return;
        }

        for (Task task : user.getTasksList()) {
            System.out.println("-----------------------------");
            System.out.println("ID: " + task.getId());
            System.out.println("Título: " + task.getTitle());
            System.out.println("Descrição: " + task.getDescription());
            System.out.println("Prioridade: " + task.getPriority());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Vencimento: " + task.getDueDate().format(formatter));
            System.out.println("Categoria: " + task.getCategory().getName());
            System.out.println("Tipo: " + task.getTypeDescription());
            System.out.println("-----------------------------");
        }
    }

    private void addTask(User user) {
        checkUser(user);
        System.out.println("\nOpção escolhida: [2] Adicionar tarefa:\n");
        System.out.println("ENTRE COM OS DADOS DA TAREFA");
        System.out.println("----------------------------");

        System.out.print("Título: ");
        String title = sc.nextLine();

        System.out.print("Descrição: ");
        String description = sc.nextLine();

        System.out.print("Data de vencimento DD/MM/YYYY: ");
        LocalDate dueDate = LocalDate.parse(sc.nextLine(), formatter);

        System.out.print("Prioridade LOW, MEDIUM, HIGH: ");
        Priority priority = Priority.valueOf(sc.nextLine().toUpperCase());

        System.out.println("\nCategoria:");
        System.out.print("Nome: ");
        String categoryName = sc.nextLine();

        System.out.print("Descrição: ");
        String categoryDescription = sc.nextLine();
        Category category = new Category(categoryName, categoryDescription);

        System.out.println("\nTipo simples [1] ou recorrente [2]: ");
        int responseType = Integer.parseInt(sc.nextLine());

        Task task;
        if (responseType == 1) {
            task = new SimpleTask(title, description, dueDate, priority, category);
        } else if (responseType == 2) {
            task = new RecurringTask(title, description, dueDate, priority, category);
        } else {
            throw new DomainException("Escolha inválida!");
        }

        taskService.addTask(userService.getUserRepository(), user, task);
        System.out.println("\nTarefa adicionada com sucesso!");
    }

    private void editTask(User user) {
        checkUser(user);
        System.out.println("\n---- EDITAR TAREFA ----\n");
        System.out.print("Entre com o ID da tarefa: ");
        int id = Integer.parseInt(sc.nextLine());

        Task task = findTask(user, id);

        System.out.print("Novo título: ");
        String title = sc.nextLine();

        System.out.print("Nova descrição: ");
        String description = sc.nextLine();

        System.out.print("Nova data DD/MM/YYYY: ");
        LocalDate dueDate = LocalDate.parse(sc.nextLine(), formatter);

        System.out.print("Nova prioridade LOW, MEDIUM, HIGH: ");
        Priority priority = Priority.valueOf(sc.nextLine().toUpperCase());

        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);

        System.out.println("\nTarefa editada com sucesso!");
    }

    private void removeTask(User user) {
        checkUser(user);
        System.out.println("\n---- EXCLUIR TAREFA ----\n");
        System.out.print("Entre com o ID da tarefa: ");
        int id = Integer.parseInt(sc.nextLine());

        Task task = findTask(user, id);
        taskService.removeTask(userService.getUserRepository(), user.getName(), task);

        System.out.println("\nTarefa excluída com sucesso!");
    }

    private void completeTask(User user) {
        checkUser(user);
        System.out.println("\n---- CONCLUIR TAREFA ----\n");
        System.out.print("Entre com o ID da tarefa: ");
        int id = Integer.parseInt(sc.nextLine());

        findTask(user, id);
        taskService.completeTask(userService.getUserRepository(), user.getName(), id);

        System.out.println("\nTarefa marcada como concluída!");
    }

    private Task findTask(User user, int id) {
        for (Task task : user.getTasksList()) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        throw new TaskNotFoundException();
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}