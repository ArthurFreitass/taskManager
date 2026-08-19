package ui;

import model.entities.Task;
import model.entities.User;
import service.TaskService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner sc;

    private final TaskService taskService;
    private final UserService userService;

    public ConsoleUI(Scanner sc, TaskService taskService, UserService userService) {
        this.sc = sc;
        this.taskService = taskService;
        this.userService = userService;
    }

    public void showMenu() {

        int option;
        User currentUser = null;

        do {

            System.out.println("\n---- BEM-VINDO AO TaskManager ----\n");
            System.out.println("Escolha uma das opções:\n");
            System.out.println("[1] - Listar usuários existentes:");
            System.out.println("[2] - Criar novo usuário:");
            System.out.println("[3] - Selecionar usuário:");
            System.out.println("[0] - Sair");

            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    listUsers();
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    currentUser = selectUser();

                    break;
            }

        } while (option != 0);
    }

    private void listUsers() {
        System.out.println("\nOpção escolhida: [1] Listar usuários:\n");
        List<User> users = userService.getUserRepository().findAll();
        for (User u : users) {
            System.out.println(u);
        }
    }

    private void listTasks(User u) {
        System.out.println("\nOpção escolhida: [1] Listar tarefas do usuário\n");
        for (Task task : u.getTasksList()) {
            System.out.println(task);
        }
    }

    private void createUser() {
        System.out.println("\nOpção escolhida: [2] Criar um usuário:\n");
        System.out.print("Entre com um nome de usuário: ");
        String name = sc.nextLine();
        userService.addUser(name);
        System.out.println("Usuário : " + name + " adicionado com sucesso!\n");
    }

    private User selectUser() {
        System.out.println("\nOpção escolhida: [3] Selecionar um usuário:\n");
        System.out.println("Entre com o nome de um usuário: ");
        String name = sc.nextLine();
        return userService.findUser(name);
    }

    private void taskMenu(User user) {
        int option;
        System.out.println("Você entrou como " + user.getName());

        /*        do {
            System.out.println("Escolha uma das opções:\n");
            System.out.println("[1] - Listar tarefas:");
            System.out.println("[2] - Adicionar tarefa:");
            System.out.println("[3] - Excluir tarefa:");
            System.out.println("[3] - Marcar como concluída:");
            System.out.println("[3] - Voltar/Trocar de usuário:");
            System.out.println("[0] - Sair");

            switch (option) {
                case 1:
                    listTasks(user);
                    break;
                case 2:
                    break;
            }

        } while ();*/
    }

    private  void addTask() {
        System.out.println("\nOpção escolhida: [2] Selecionar um usuário:\n");

    }
}
