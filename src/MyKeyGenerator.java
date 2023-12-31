import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.*;
import java.util.Scanner;

public class MyKeyGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Please select a key size:");
            System.out.println("1. 128-bit");
            System.out.println("2. 256-bit");
            System.out.println("3. 512-bit");
            System.out.println("4. 1024-bit");
            System.out.println("5. 2048-bit");
            System.out.println("6. 4086-bit");
            System.out.println("0. Exit");

            System.out.print("Please enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> generateAESKey(128);
                case 2 -> generateAESKey(256);
                case 3 -> generateRSAKeyPair(512);
                case 4 -> generateRSAKeyPair(1024);
                case 5 -> generateRSAKeyPair(2048);
                case 6 -> generateRSAKeyPair(4086);
                case 0 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void generateRSAKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String publicKeyString = publicKey.toString();
            String privateKeyString = privateKey.toString();

            copyToClipboard(publicKeyString);
            copyToClipboard(privateKeyString);

            System.out.println("Generated " + keySize + "-bit RSA key pair:");
            System.out.println("Public key: " + publicKeyString);
            System.out.println("Private key: " + privateKeyString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void generateAESKey(int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize);
            SecretKey secretKey = keyGenerator.generateKey();

            byte[] keyBytes = secretKey.getEncoded();
            StringBuilder sb = new StringBuilder();
            for (byte b : keyBytes) {
                sb.append(String.format("%02x", b));
            }
            String aesKeyString = sb.toString();

            copyToClipboard(aesKeyString);

            System.out.println("Generated " + keySize + "-bit AES key: " + aesKeyString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        System.out.println("Code copied to clipboard.");
    }
}
