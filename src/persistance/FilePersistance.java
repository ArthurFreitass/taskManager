package persistance;

import model.exceptions.DomainException;

import java.io.*;

public class FilePersistance {

    public FilePersistance() {
    }

    public void writeFile(String path, String msg) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

        }
    }

    public String readFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            String content = "";

            while ((line = br.readLine()) != null) {
                content += line + "\n";
            }
            return content.trim();
        } catch (IOException e) {
            throw new DomainException("Error no data in File");
        }
    }
}
