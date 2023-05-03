import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
public class Decypher {

    private String hashAlgorithm;
    private String code;
    private String sal;

    public Decypher(String hashAlgorithm, String code, String sal) {
        this.hashAlgorithm = hashAlgorithm;
        this.code = code;
        this.sal = sal;
    }



    public ArrayList<String> getString(ArrayList<String> result) {

        String originalString = result.get(0);

        String stringSal = originalString + sal;

        String hashedString = "";

        try {
            MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
            byte[] encodedString = stringSal.getBytes(StandardCharsets.UTF_8);
            byte[] hash = digest.digest(encodedString);
            hashedString = bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Ocurrió un error al decifrar la cadena: " + e.getMessage());
        }

        result.set(0, originalString);

        if (hashedString.equals(code.toLowerCase())) {
            System.out.println("La cadena es: " + originalString);
            result.set(1, "1");
            return result;
        }

        return result;
    }

    public String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
