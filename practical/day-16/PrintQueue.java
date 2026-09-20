/** Day 16 tiny project: FIFO print queue. */
import java.util.*;
public class PrintQueue {
    public static void main(String[] args) {
        Deque<String> q = new ArrayDeque<>();
        q.offer("doc1.pdf"); q.offer("doc2.pdf"); q.offer("doc3.pdf");
        while (!q.isEmpty()) System.out.println("printing " + q.poll());
    }
}
