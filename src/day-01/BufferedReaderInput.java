import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Day 1 — Fast input with BufferedReader.
 * Use this when reading > 10^5 tokens (Scanner is too slow).
 *
 * Run: java -cp src day01.BufferedReaderInput
 * Type: 1000000 numbers (or whatever the prompt asks)
 */
public class BufferedReaderInput {

    public static void main(String[] args) throws IOException {
        // InputStreamReader converts byte stream → character stream.
        // BufferedReader buffers reads to reduce syscalls.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter two integers separated by space: ");
        String line = br.readLine();               // read entire line as String
        String[] parts = line.trim().split("\\s+"); // split on one or more spaces

        int a = Integer.parseInt(parts[0]);         // String → int
        int b = Integer.parseInt(parts[1]);

        System.out.println("a + b = " + (a + b));
        System.out.println("a * b = " + (a * b));
    }
}
