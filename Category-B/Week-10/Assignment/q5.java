import java.io.*;
import java.util.*;

/**
 * Problem 5: Round-Robin Game Turns (circular singly linked list, tail reference)
 *
 * Time complexity:
 *   addLast(name) -> O(1)  (tail reference; head is tail.next)
 *   printTurns(t) -> O(t)  (one step per turn; the list wraps around by itself)
 *
 * Input : line 1 = players (comma or space separated), line 2 = number of turns
 */
public class q5 {
    static class Node {
        String name;
        Node next;
        Node(String name) { this.name = name; }
    }

    static Node tail;

    static void addLast(String name) {
        Node n = new Node(name);
        if (tail == null) {
            n.next = n;              // single node points to itself
        } else {
            n.next = tail.next;      // new node points to head
            tail.next = n;           // old tail points to new node
        }
        tail = n;
    }

    static void printTurns(int turns) {
        if (tail == null) return;
        Node cur = tail.next;        // head
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < turns; i++) {
            if (i > 0) sb.append(' ');
            sb.append(cur.name);
            cur = cur.next;
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String players = br.readLine();
        String turnsLine = br.readLine();
        for (String s : players.trim().split("[,\\s]+")) if (!s.isEmpty()) addLast(s);
        printTurns(Integer.parseInt(turnsLine.replaceAll("[^0-9]", "")));
    }
}
