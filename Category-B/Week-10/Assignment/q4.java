import java.io.*;
import java.util.*;

/**
 * Problem 4: Train Coaches Both Ways (doubly linked list with head and tail)
 *
 * Time complexity:
 *   addLast        -> O(1)  (tail reference)
 *   finding coach  -> O(n)  (linear search from the head)
 *   unlinking once found -> O(1)  (just fix prev/next of the two neighbours)
 *   printForward / printBackward -> O(n)
 *
 * Input : line 1 = coaches (comma separated), line 2 = coach to remove
 */
public class q4 {
    static class Node {
        String name;
        Node prev, next;
        Node(String name) { this.name = name; }
    }

    static Node head, tail;

    static void addLast(String name) {
        Node n = new Node(name);
        if (head == null) { head = tail = n; return; }
        tail.next = n;
        n.prev = tail;
        tail = n;
    }

    static boolean remove(String name) {
        Node cur = head;
        while (cur != null && !cur.name.equals(name)) cur = cur.next;   // O(n) search
        if (cur == null) return false;                                  // not found
        // O(1) unlink
        if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;   // removing head
        if (cur.next != null) cur.next.prev = cur.prev; else tail = cur.prev;   // removing tail
        cur.prev = cur.next = null;
        return true;
    }

    static void printForward() {
        StringBuilder sb = new StringBuilder("Forward: ");
        for (Node c = head; c != null; c = c.next) {
            sb.append(c.name);
            if (c.next != null) sb.append(" <-> ");
        }
        System.out.println(sb);
    }

    static void printBackward() {
        StringBuilder sb = new StringBuilder("Backward: ");
        for (Node c = tail; c != null; c = c.prev) {
            sb.append(c.name);
            if (c.prev != null) sb.append(" <-> ");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String coaches = br.readLine();
        String toRemove = br.readLine();
        for (String s : coaches.split("\\s*,\\s*")) if (!s.trim().isEmpty()) addLast(s.trim());
        if (toRemove != null) remove(toRemove.trim().replaceFirst("(?i)^remove\\s+", ""));
        printForward();
        printBackward();
    }
}
