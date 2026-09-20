/**
 * Day 2 — Hard practice solutions.
 *
 * Compile: javac src/day-02/PracticeHard.java
 * Run    : java -cp src/day-02 PracticeHard
 */
public class PracticeHard {

    // Q11: Node with append/printList/length (separate class to avoid clash)
    static class LLNode {
        int data;
        LLNode next;
        LLNode(int d) { data = d; }
        static int length(LLNode head) {
            int c = 0; for (LLNode cur = head; cur != null; cur = cur.next) c++;
            return c;
        }
        static LLNode append(LLNode head, int d) {
            LLNode n = new LLNode(d);
            if (head == null) return n;
            LLNode cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = n;
            return head;
        }
        static void print(LLNode head) {
            for (LLNode cur = head; cur != null; cur = cur.next)
                System.out.print(cur.data + " -> ");
            System.out.println("null");
        }
    }

    // Q12: MinStack using two stacks
    static class MinStack {
        private java.util.ArrayDeque<Integer> stack = new java.util.ArrayDeque<>();
        private java.util.ArrayDeque<Integer> mins  = new java.util.ArrayDeque<>();

        public void push(int x) {
            stack.push(x);
            if (mins.isEmpty() || x <= mins.peek()) mins.push(x);
        }
        public int pop() {
            int x = stack.pop();
            if (x == mins.peek()) mins.pop();
            return x;
        }
        public int peek() { return stack.peek(); }
        public int getMin() { return mins.peek(); }
    }

    // Q13: LRUCache skeleton (full impl in Day 14)
    static class LRUCache<K, V> {
        private java.util.HashMap<K, V> map = new java.util.HashMap<>();
        // full doubly-linked-list structure deferred
        public V get(K key) { return map.get(key); }
        public void put(K key, V val) { map.put(key, val); }
    }

    // Q14: Fraction
    static class Fraction {
        final long num, den;
        Fraction(long n, long d) {
            if (d == 0) throw new IllegalArgumentException();
            long g = gcd(Math.abs(n), Math.abs(d));
            long sign = (n < 0) ^ (d < 0) ? -1 : 1;
            this.num = sign * Math.abs(n) / g;
            this.den = Math.abs(d) / g;
        }
        Fraction add(Fraction o) {
            return new Fraction(num * o.den + o.num * den, den * o.den);
        }
        Fraction multiply(Fraction o) {
            return new Fraction(num * o.num, den * o.den);
        }
        @Override public boolean equals(Object o) {
            if (!(o instanceof Fraction)) return false;
            Fraction f = (Fraction) o;
            return num == f.num && den == f.den;
        }
        @Override public int hashCode() { return (int)(31 * num + den); }
        @Override public String toString() { return num + "/" + den; }
        static long gcd(long a, long b) { return b == 0 ? a : gcd(b, a % b); }
    }

    // Q15: TrieNode skeleton
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEndOfWord;
    }

    public static void main(String[] args) {
        // Q11
        LLNode head = null;
        for (int v : new int[]{1, 2, 3, 4}) head = LLNode.append(head, v);
        LLNode.print(head);
        System.out.println("Q11 length = " + LLNode.length(head));

        // Q12
        MinStack ms = new MinStack();
        ms.push(3); ms.push(5); System.out.println("Q12 min after push 3,5: " + ms.getMin());
        ms.push(2); ms.push(1); System.out.println("Q12 min after push 2,1: " + ms.getMin());
        ms.pop();   System.out.println("Q12 min after pop 1:       " + ms.getMin());

        // Q13
        LRUCache<String, Integer> cache = new LRUCache<>();
        cache.put("a", 1); System.out.println("Q13 a=" + cache.get("a"));

        // Q14
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        System.out.println("Q14 " + f1 + " + " + f2 + " = " + f1.add(f2));
        System.out.println("Q14 " + f1 + " * " + f2 + " = " + f1.multiply(f2));

        // Q15
        TrieNode root = new TrieNode();
        root.children['a' - 'a'] = new TrieNode();
        root.children['a' - 'a'].isEndOfWord = true;
        System.out.println("Q15 trie built");
    }
}
