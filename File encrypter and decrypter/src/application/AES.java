package application;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Stream;

public class AES {

    private static final byte[] initVector = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final IvParameterSpec initVectorSpec = new IvParameterSpec(initVector);

    public AES() {
    }

    // AES Encryption method

    public String encrypt(String strToEncrypt,String mSecretKey) throws Exception{
            int sum = 0;
            for (int i = 0; i < mSecretKey.length(); i++)
            {
                sum += (mSecretKey.charAt(i));
            }
            Random r = new Random(sum);
            byte[] bytes =new byte[128];
            r.nextBytes(bytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(mSecretKey.toCharArray(), bytes, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, initVectorSpec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    public void encrypt(File inputFile, File outputFile, String mSecretKey) throws Exception{
        String content = readFileContent(inputFile.getPath());
        String encodedString = encrypt(content,mSecretKey);
        writeFileContent(outputFile, encodedString);
    }

    // AES Decryption method

    public String decrypt(String strToDecrypt, String mSecretKey) throws Exception{
            int sum = 0;
            // For every character
            for (int i = 0; i < mSecretKey.length(); i++)
            {
                sum += (mSecretKey.charAt(i));
            }
            Random r = new Random(sum);
            byte[] bytes =new byte[128];
            r.nextBytes(bytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(mSecretKey.toCharArray(), bytes, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, initVectorSpec);
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
