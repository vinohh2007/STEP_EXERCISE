import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Problem 1: Priority Token Insertion
 *
 * Time complexity:
 *   addFirst(token)       -> O(1)
 *   addLast(token)        -> O(n)  (walks to the last node; O(1) if a tail reference were kept)
 *   insertAt(index,token) -> O(n)  (O(1) when index == 0, otherwise walks index-1 nodes)
 * Extra space: O(1) per operation.
 *
 * Input : line 1 = starting list, e.g. "101 -> 102 -> 103"
 *         next lines = operations, e.g. "addFirst(100)", "addLast(104)", "insertAt(2, 150)"
 */
public class q1 {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static Node head;
    static int size;

    static void addFirst(int token) {
        Node n = new Node(token);
        n.next = head;
        head = n;
        size++;
    }

    static void addLast(int token) {
        Node n = new Node(token);
        if (head == null) {
            head = n;
        } else {
            Node cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = n;
        }
        size++;
    }

    static void insertAt(int index, int token) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("index " + index);
        if (index == 0) { addFirst(token); return; }
        Node prev = head;
        for (int i = 0; i < index - 1; i++) prev = prev.next;   // node just before the index
        Node n = new Node(token);
        n.next = prev.next;      // 1) new node points to the rest of the list
        prev.next = n;           // 2) previous node points to the new node
        size++;
    }

    static void print() {
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(" -> ");
        System.out.println(sb.append("null"));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String first = br.readLine();
        Matcher m = Pattern.compile("-?\\d+").matcher(first == null ? "" : first);
        while (m.find()) addLast(Integer.parseInt(m.group()));

        String line;
        while ((line = br.readLine()) != null) {
            line = line.replaceAll("[(),=]", " ").trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\s+");
            switch (p[0].toLowerCase()) {
                case "addfirst": addFirst(Integer.parseInt(p[1])); break;
                case "addlast":  addLast(Integer.parseInt(p[1])); break;
                case "insertat": insertAt(Integer.parseInt(p[1]), Integer.parseInt(p[2])); break;
            }
        }
        print();
    }
}
