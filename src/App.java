import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;



public class App {

    public static void main(String[] args) {

        Scanner mode = new Scanner(System.in);

        HashMap<String, String> parameters = readFile();
        String hashAlgorithm = parameters.get("HashAlgorithm");
        String code = parameters.get("Code");
        String sal = parameters.get("SAL");
        int threadNumber = Integer.parseInt(parameters.get("ThreadNumber"));

        saveString(hashAlgorithm, code, sal, threadNumber);

        mode.close();
    }


    public static void saveString(String hashAlgorithm, String code, String sal, int threadNumber) {

        Decypher decypher = new Decypher(hashAlgorithm, code, sal);
        
        String originalString = findString(decypher, threadNumber);
        
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


    public static String findString( Decypher decypher,int threadNumber) {

        String originalString = "";
        ArrayList<String> result = new ArrayList<String>();

        result.add(originalString);
        result.add("0");

        if (threadNumber == 1){
            int i = 0;

            while (i < 7 && result.get(1) != "1") {

                char[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
                result = combinationsWithRepetition(arr, i+1, decypher, result);

                i++;
            }
            if (result.get(1) == "1") {
                return originalString = result.get(0);
            }

        }
        else{
            // if (Thread.getId() == 1){
            //     int i = 0;

            //     while (i < 7 && result.get(1) != "1") {
    
            //         char[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            //         result = combinationsWithRepetition(arr, i+1, decypher, result);
    
            //         i+=2;
            //     }
            //     if (result.get(1) == "1") {
            //         return originalString = result.get(0);
            //     }
            // }
            // else {
            //     int i = 1;

            //     while (i < 7 && result.get(1) != "1") {
    
            //         char[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
            //         result = combinationsWithRepetition(arr, i+1, decypher, result);
    
            //         i+=2;
            //     }
            //     if (result.get(1) == "1") {
            //         return originalString = result.get(0);
            //     }
            // }
        }
        

        return ("No se encontro la cadena.");
    }

    public static ArrayList<String> combinationsWithRepetition(char[] arr, int len, Decypher decypher, ArrayList<String> result) {
        int[] indices = new int[len];
        Arrays.fill(indices, 0);
        while (indices[0] < arr.length && result.get(1) != "1") {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(arr[indices[i]]);
            }
            result.set(0, sb.toString());
            result = decypher.getString(result);

            int k = len - 1;
            while (k >= 0 && indices[k] == arr.length - 1 && result.get(1) != "1") {
                k--;
            }
            if (k < 0) {
                break;
            }
            indices[k]++;
            for (int i = k + 1; i < len && result.get(1) != "1"; i++) {
                indices[i] = 0;
            }
        }

        return result;
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
