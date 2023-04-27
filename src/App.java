import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner mode = new Scanner(System.in);

        HashMap<String, String> parameters = readFile();
        String hashAlgorithm = parameters.get("HashAlgorithm");
        String code = parameters.get("Code");
        String sal = parameters.get("SAL");
        int threadNumber = Integer.parseInt(parameters.get("ThreadNumber"));
        Decypher references = new Decypher(hashAlgorithm, code, sal);

        if (threadNumber == 1) {
            saveStringMode1(hashAlgorithm, code, sal);
        } else if (threadNumber == 2) {
            saveStringMode2(hashAlgorithm, code, sal);
        }

        mode.close();
    }


    public static void saveStringMode1(String hashAlgorithm, String code, String sal) {
        
        String originalString = Decypher.findString();
        
        String fileRoot = "./References/string.txt";

        try {
            File archivo = new File(fileRoot);
            FileWriter writer = new FileWriter(archivo);
            writer.write(originalString);
            writer.close();
            System.out.println("Cadena guardada exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar el archivo: " + e.getMessage());
        }
    }

    public static void saveStringMode2(String hashAlgorithm, String code, String sal) {
        
        String originalString = Decypher.findString();
        
        String fileRoot = "./References/string.txt";

        try {
            File archivo = new File(fileRoot);
            FileWriter writer = new FileWriter(archivo);
            writer.write(originalString);
            writer.close();
            System.out.println("Cadena guardada exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar el archivo: " + e.getMessage());
        }
    }

    

    public static HashMap<String, String> readFile() {

        HashMap<String, String> parameters = new HashMap<>();

        System.out.println("Inserte el nombre del archivo (sin el .txt): ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();

        try {
            Scanner scanner = new Scanner(new File("./files/" + fileName + ".txt"));

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                if (linea.startsWith("H=")) {
                    parameters.put("HashAlgorithm", linea.substring(2));
                } else if (linea.startsWith("C=")) {
                    parameters.put("Code", linea.substring(2));
                } else if (linea.startsWith("S=")) {
                    parameters.put("SAL", linea.substring(2));
                } else if (linea.startsWith("ThreadNumber=")) {
                    parameters.put("ThreadNumber", linea.substring(13));
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        sc.close();

        return parameters;

    }

}
