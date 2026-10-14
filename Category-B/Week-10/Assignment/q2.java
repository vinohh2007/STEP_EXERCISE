import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Problem 2: Cancelled Orders Cleanup
 *
 * Time complexity : O(n)  - every node is visited exactly once.
 * Extra space     : O(1)  - only a few references, nodes are unlinked in place.
 *
 * Input : line 1 = list (may be empty), line 2 = code to remove
 */
public class q2 {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static Node removeAll(Node head, int code) {
        // 1) remove matching nodes from the front by moving head
        while (head != null && head.data == code) head = head.next;
        // 2) walk with a previous reference and skip matching nodes
        Node prev = head;
        while (prev != null && prev.next != null) {
            if (prev.next.data == code) prev.next = prev.next.next;
            else prev = prev.next;
        }
        return head;
    }

    static void print(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(" -> ");
        System.out.println(sb.append("null"));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String listLine = br.readLine();
        String codeLine = br.readLine();
        if (codeLine == null) {               // empty list written as a single code line
            codeLine = listLine; listLine = "";
        }
        Node head = null, tail = null;
        Matcher m = Pattern.compile("-?\\d+").matcher(listLine == null ? "" : listLine);
        while (m.find()) {
            Node n = new Node(Integer.parseInt(m.group()));
            if (head == null) head = tail = n; else { tail.next = n; tail = n; }
        }
        Matcher c = Pattern.compile("-?\\d+").matcher(codeLine);
        c.find();
        print(removeAll(head, Integer.parseInt(c.group())));
    }
}
