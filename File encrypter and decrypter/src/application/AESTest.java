package application;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESTest {
    AES aes = new AES();
    
    // Testing the encryption method with a given input string and a secret key.
    @org.junit.jupiter.api.Test
    void encrypt() throws Exception {
        String encrypt = aes.encrypt("Hello World#324", "mb##$2");
        assertEquals("eTL/TFuLw0zCQPpWZaycTA==",encrypt);
    }

    // Testing the encryption method with given plain text file.
    @org.junit.jupiter.api.Test
    void testEncrypt() throws Exception {
        File inputFile = new File("aes.txt");
        File outPutFile = new File("encrypt-aes.txt");
        aes.encrypt(inputFile,outPutFile,"mb##$2");
    }

    // Testing the decryption method with a given encrypted input and a secret key.
    @org.junit.jupiter.api.Test
    void decrypt() throws Exception {
        String decrypt = aes.decrypt("eTL/TFuLw0zCQPpWZaycTA==", "mb##$2");
        assertEquals(decrypt, "Hello World#324");
    }

    // Testing the decryption method with given encrypted file.
    @org.junit.jupiter.api.Test
    void testDecrypt() throws Exception {
        File inputFile = new File("encrypt-aes.txt");
        File outPutFile = new File("decrypt-aes.txt");
        aes.decrypt(inputFile,outPutFile,"mb##$2");

    }

    // Testing writeFileContent method by writing a string to the output file.
    @Test
    void writeFileContent()
    {
        File outPutFile = new File("inputfile.txt");
        aes.writeFileContent(outPutFile, "Hello World#324");
    }

    // Testing readFileContent method by reading a string from the input file.
    @Test
    void readFileContent()
    {
        File inputFile = new File("inputfile.txt");
        String s = aes.readFileContent(inputFile.getPath());
        assertEquals("Hello World#324",s);
    }
}