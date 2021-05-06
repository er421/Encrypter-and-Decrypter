package application;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

public class TripleDES {

    public TripleDES() {
            }

    // 3DES Encryption method

    public String encrypt(String strToEncrypt, String mSecretKey) throws Exception{
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digestOfPassword = md.digest(mSecretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            KeySpec spec = new DESedeKeySpec(keyBytes);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "DESede");

            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    public void encrypt(File inputFile, File outputFile, String mSecretKey) throws Exception {
        String content = readFileContent(inputFile.getPath());
        String encodedString = encrypt(content,mSecretKey);
        writeFileContent(outputFile, encodedString);
    }

    // 3DES Decryption method

    public String decrypt(String strToDecrypt, String mSecretKey) throws Exception {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(mSecretKey.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        KeySpec spec = new DESedeKeySpec(keyBytes);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "DESede");

        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }

    public void decrypt(File inputFile, File outputFile, String mSecretKey) throws Exception{
        String encodedContent = readFileContent(inputFile.getPath());
        String content = decrypt(encodedContent,mSecretKey);
        writeFileContent(outputFile, content);
    }

    // Read from text file

    public String readFileContent(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString().trim();
    }

    // Write to text file

    public void writeFileContent(File outputFile, String content) {
        try {
            FileWriter fw = new FileWriter(outputFile);
            fw.write(content);
            fw.close();
        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }

}
