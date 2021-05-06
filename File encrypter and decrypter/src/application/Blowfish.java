package application;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

public class Blowfish {

    private static final byte[] initVector = {0, 0, 0, 0, 0, 0, 0, 0};
    private static final IvParameterSpec initVectorSpec = new IvParameterSpec(initVector);

    public Blowfish() {

    }
    // Blowfish Encryption method

    public String encrypt(String strToEncrypt, String mSecretKey) throws Exception{
            SecretKeySpec secretKey = new SecretKeySpec(mSecretKey.getBytes(), "Blowfish");

            Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,initVectorSpec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    public void encrypt(File inputFile, File outputFile, String mSecretKey) throws Exception {
        String content = readFileContent(inputFile.getPath());
        String encodedString = encrypt(content,mSecretKey);
        writeFileContent(outputFile, encodedString);
    }

    // Blowfish Decryption method

    public String decrypt(String strToDecrypt, String mSecretKey) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(mSecretKey.getBytes(), "Blowfish");

            Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,initVectorSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }

    public void decrypt(File inputFile, File outputFile, String mSecretKey) throws Exception {
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
