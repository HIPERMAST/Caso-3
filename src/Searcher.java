import java.util.ArrayList;
import java.util.Arrays;

public class Searcher extends Thread{
    private Decypher decypher;
    private ArrayList<String> result;
    private int start;
    private static boolean found = false;
    private int adder;

    public Searcher(String name, Decypher decypher, ArrayList<String> result, int start, int adder) {
        super(name);
        this.decypher = decypher;
        this.result = result;
        this.start = start;
        this.adder = adder;
    }

    @Override
    public void run() {
        int i = start;
        while (i < 7 && result.get(1) != "1" && !found) {
            char[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            result = combinationsWithRepetition(arr, i+1, decypher, result);
            i+=adder;
        }
        if (result.get(1) == "1") {
            found = true;
        }
    }

    private static ArrayList<String> combinationsWithRepetition(char[] arr, int len, Decypher decypher, ArrayList<String> result) {
        int[] indices = new int[len];
        Arrays.fill(indices, 0);
        while (indices[0] < arr.length && result.get(1) != "1" && !found) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(arr[indices[i]]);
            }
            result.set(0, sb.toString());
            result = decypher.getString(result);

            int k = len - 1;
            while (k >= 0 && indices[k] == arr.length - 1 && result.get(1) != "1" && !found) {
                k--;
            }
            if (k < 0) {
                break;
            }
            indices[k]++;
            for (int i = k + 1; i < len && result.get(1) != "1" && !found; i++) {
                indices[i] = 0;
            }
        }

        return result;
    }
}