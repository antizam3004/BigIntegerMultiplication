import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Main {

    /*
    *
    *
    * This application creates(generates) two large BigInteger random numbers and multiplies them.
    * Both numbers and multiplication result are stored in txt files.
    * We use Schönhage-Strassen method for fast multiply calculations
    * For my laptop(Intel i5 4210U CPU, 8GB of memory) it takes about 25 seconds to perform all the calculations.
    * Application, so as BigInteger class can take as many number of digits and it is limited only by memory in your local machine
    * In my case for 100 million digits java throws exception and says java heap is out of space, so in my case
    * it is necessary to increase heap size or add some more RAM memory
    *
    *
    * */

    static int numberOfDigits=10000000;
    static byte[] number1 = new byte[numberOfDigits];
    static byte[] number2 = new byte[numberOfDigits];
    static Random generator = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws IOException {

        //Generating large number1
        for (int i = 0; i < numberOfDigits; i++) {
            number1[i] = (byte) Math.abs(generator.nextInt(9));
            number2[i] = (byte) Math.abs(generator.nextInt(9));
        }
        System.out.println(" --> Done generating/now writing to file");
        //File saving is optional
        //files will be located at project structure folder and for 10 million digit number1 it amounts 9,53MB
        writeFile("number1.txt", number1);
        writeFile("number2.txt", number2);

        System.out.println(" --> Generating numbers");
        BigInteger b1=new BigInteger(number1);
        BigInteger b2=new BigInteger(number1);
        long start=System.currentTimeMillis();

        System.out.println(" --> Multiplying...");
        /*
        *
        *
            main part of our code where multiplication takes place
        *
        */
        BigInteger result=new SchonhageStrassen().mult(b1,b2);


        //Time recording is optional
        long end=System.currentTimeMillis();
        System.out.println("Time needed is: "+(end-start) +" miliseconds");
        //file multipliedNumber.txt will be located at project structure folder and for 10 million digit numbers multiplication it amounts 50.5MB
        writeFile("multipliedNumber.txt", result.toByteArray());

        System.out.println(" --> Done!");
    }

    public static void writeFile(String fileName, byte[] byteArray) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < byteArray.length; i++) {
            bw.write(Integer.toString(byteArray[i]));
        }
        bw.close();
        System.out.println(" --> File written in "+fileName);
    }
}
