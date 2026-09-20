/** Day 13 tiny project: singly linked-list playlist with reverse. */
public class Playlist {
    static class Song { String title; Song next; Song(String t) { title = t; } }
    static Song reverse(Song h) {
        Song prev = null, cur = h;
        while (cur != null) { Song nxt = cur.next; cur.next = prev; prev = cur; cur = nxt; }
        return prev;
    }
    static void print(Song h) {
        for (Song c = h; c != null; c = c.next) System.out.print(c.title + " -> ");
        System.out.println("null");
    }
    public static void main(String[] args) {
        Song head = new Song("A"); head.next = new Song("B"); head.next.next = new Song("C");
        System.out.print("orig: "); print(head);
        System.out.print("rev : "); print(reverse(head));
    }
}
