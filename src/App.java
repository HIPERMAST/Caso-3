import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



public class App {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Scanner mode = new Scanner(System.in);

        HashMap<String, String> parameters = readFile();
        String hashAlgorithm = parameters.get("HashAlgorithm");
        String code = parameters.get("Code");
        String sal = parameters.get("SAL");
        int threadNumber = Integer.parseInt(parameters.get("ThreadNumber"));

        Decypher decypher = new Decypher(hashAlgorithm, code, sal);
        
        findString(decypher, threadNumber);

        mode.close();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tiempo total de ejecución: " + totalTime + " milisegundos");
    }


    public static void saveString(String originalString, int threadNumber) {
        
        String fileRoot = "./strings/"+ threadNumber +".txt";

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


    public static void findString( Decypher decypher,int threadNumber) {

        String originalString = "";
        ArrayList<String> result1 = new ArrayList<String>();
        ArrayList<String> result2 = new ArrayList<String>();
        
        result1.add(originalString);
        result1.add("0");
        result2.add(originalString);
        result2.add("0");


        if (threadNumber == 1){
            // Crear un hilo
            Thread t1 = new Searcher("Thread 1", decypher, result1, 0, threadNumber);

            // Iniciar el hilo
            t1.start();

            // Verificar si el resultado fue encontrado por el hilo 1
            try {
                t1.join();
            } catch (InterruptedException e) {
                System.out.println("Ocurrió un error al esperar el hilo: " + e.getMessage());
            }

            if (result1.get(1) == "1") {
                originalString = result1.get(0);
                saveString(originalString, threadNumber);
            }

        }
        else if (threadNumber == 2) {
            // Crear dos hilos
            Thread t1 = new Searcher("Thread 1", decypher, result1, 0, threadNumber);
            Thread t2 = new Searcher("Thread 2", decypher, result2, 1, threadNumber);
        
            // Iniciar los hilos
            t1.start();
            t2.start();

            // Esperar a que los hilos terminen
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                System.out.println("Ocurrió un error al esperar los hilo: " + e.getMessage());
            }            
        
            // Verificar si el resultado fue encontrado por el hilo 1
            if (result1.get(1) == "1") {
                originalString = result1.get(0);
                saveString(originalString, threadNumber);
            }

            // Verificar si el resultado fue encontrado por el hilo 2
            else if (result2.get(1) == "1") {
                originalString = result2.get(0);
                saveString(originalString, threadNumber);
            }
            else
                saveString("No se encontro la cadena", threadNumber);
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
