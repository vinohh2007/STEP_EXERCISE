import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Problem 3: Reverse the Queue
 *
 * Time complexity : O(n)  - one pass over the list.
 * Extra space     : O(1)  - three references (previous, current, next); no new nodes.
 *
 * Input : one line with the list, e.g. "1 -> 2 -> 3 -> 4"
 */
public class q3 {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static Node reverse(Node head) {
        Node previous = null, current = head;
        while (current != null) {
            Node next = current.next;   // remember the rest
            current.next = previous;    // turn the link around
            previous = current;         // move previous forward
            current = next;             // move current forward
        }
        return previous;                // new head
    }

    static void print(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(" -> ");
        System.out.println(sb.append("null"));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        Node head = null, tail = null;
        Matcher m = Pattern.compile("-?\\d+").matcher(line == null ? "" : line);
        while (m.find()) {
            Node n = new Node(Integer.parseInt(m.group()));
            if (head == null) head = tail = n; else { tail.next = n; tail = n; }
        }
        print(reverse(head));
    }
}
