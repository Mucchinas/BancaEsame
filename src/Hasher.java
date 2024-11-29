import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public static String getHash(String plainPass) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(plainPass.getBytes(StandardCharsets.UTF_8));

        return String.format("%064x", new BigInteger(1, hash));

    }
}
