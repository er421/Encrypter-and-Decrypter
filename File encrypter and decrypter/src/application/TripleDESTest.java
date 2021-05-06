package application;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TripleDESTest {
    TripleDES tripleDES = new TripleDES();
    
    // Testing the encryption method with a given input string and a secret key.
    @org.junit.jupiter.api.Test
    void encrypt() throws Exception {
        String encrypt = tripleDES.encrypt("Hello World#324", "mb##$2");
        assertEquals("PoFzDLC56wLzyZ3QzPzDyg==",encrypt);
    }

    // Testing the encryption method with given plain text file.
    @org.junit.jupiter.api.Test
    void testEncrypt() throws Exception {
        File inputFile = new File("tripledes.txt");
        File outPutFile = new File("encrypt-tripledes.txt");
        tripleDES.encrypt(inputFile,outPutFile,"mb##$2");
    }

    // Testing the decryption method with a given encrypted input and a secret key.
    @org.junit.jupiter.api.Test
    void decrypt() throws Exception {
        TripleDES tripleDES = new TripleDES();
        String decrypt = tripleDES.decrypt("PoFzDLC56wLzyZ3QzPzDyg==", "mb##$2");
        assertEquals(decrypt, "Hello World#324");
    }

    // Testing the decryption method with given encrypted file.
    @org.junit.jupiter.api.Test
    void testDecrypt() throws Exception {
        File inputFile = new File("encrypt-tripledes.txt");
        File outPutFile = new File("decrypt-tripledes.txt");
        tripleDES.decrypt(inputFile,outPutFile,"mb##$2");

    }
    
    // Testing writeFileContent method by writing a string to the output file.
    @Test
    void writeFileContent()
    {
        File outPutFile = new File("inputfile.txt");
        tripleDES.writeFileContent(outPutFile, "Hello World#324");
    }

    // Testing readFileContent method by reading a string from the input file.
    @Test
    void readFileContent()
    {
        File inputFile = new File("inputfile.txt");
        String s = tripleDES.readFileContent(inputFile.getPath());
        assertEquals("Hello World#324",s);
    }
}