package application;

import persistance.FilePersistance;

public class Main {
    public static void main(String[] args) {

        FilePersistance filePersistance = new FilePersistance();
        String text = filePersistance.readFile("C:\\Users\\Arthur Freitas\\IdeaProjects\\taskManager\\src\\data\\file.txt");

        System.out.println(text);
    }
}
