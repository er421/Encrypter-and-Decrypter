package application;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BlowfishTest {
    Blowfish blowfish = new Blowfish();

    // Testing the encryption method with a given input string and a secret key.
    @org.junit.jupiter.api.Test
    void encrypt() throws Exception {
        String encrypt = blowfish.encrypt("Hello World#324", "mb##$2");
        assertEquals("pkR72UJ8biI+CcIt9qaHmA==",encrypt);
    }

    // Testing the encryption method with given plain text file.
    @org.junit.jupiter.api.Test
    void testEncrypt() throws Exception {
        File inputFile = new File("blowfish.txt");
        File outPutFile = new File("encrypt-blowfish.txt");
        blowfish.encrypt(inputFile,outPutFile,"mb##$2");
    }

    // Testing the decryption method with a given encrypted input and a secret key.
    @org.junit.jupiter.api.Test
    void decrypt() throws Exception {
        Blowfish blowfish = new Blowfish();
        String decrypt = blowfish.decrypt("pkR72UJ8biI+CcIt9qaHmA==", "mb##$2");
        assertEquals(decrypt, "Hello World#324");
    }

    // Testing the decryption method with given encrypted file.
    @org.junit.jupiter.api.Test
    void testDecrypt() throws Exception {
        File inputFile = new File("encrypt-blowfish.txt");
        File outPutFile = new File("decrypt-blowfish.txt");
        blowfish.decrypt(inputFile,outPutFile,"mb##$2");

    }

    // Testing writeFileContent method by writing a string to the output file.
    @Test
    void writeFileContent()
    {
        File outPutFile = new File("inputfile.txt");
        blowfish.writeFileContent(outPutFile, "Hello World#324");
    }

    // Testing readFileContent method by reading a string from the input file.
    @Test
    void readFileContent()
    {
        File inputFile = new File("inputfile.txt");
        String s = blowfish.readFileContent(inputFile.getPath());
        assertEquals("Hello World#324",s);
    }
}