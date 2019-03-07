import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 141. 环形链表
 */
public class Linked141 {

    /**
     * Definition for singly-linked list.
     *
     *
     * 给定一个链表，判断链表中是否有环。
     *
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
     */
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * 环形链表
     * @param head
     * @param pos
     * @return
     */
    public boolean hasCycle(ListNode head,int pos) {
        ListNode temp = head,old = null;
        int i = 0;
        while (temp.next != null){
            if (i++ == pos){
                old = temp;
            }
            temp = temp.next;
        }
        temp.next = old;



        // 头结点 单独处理 3,2,0,-4
        ListNode first = new ListNode(0),tempA = first,tempB = first;
        first.next = head;
        if (first.next == null || first.next.next == null) return false;
        do {
            tempA = tempA.next;
            tempB = tempB.next.next;
        } while (tempB.next != null && tempA != tempB);
        return tempA == tempB;
    }

    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static ListNode stringToListNode(String input) {
        // Generate array from the input
        int[] nodeValues = stringToIntegerArray(input);

        // Now convert that list into linked list
        ListNode dummyRoot = new ListNode(0);
        ListNode ptr = dummyRoot;
        for(int item : nodeValues) {
            ptr.next = new ListNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }

    public static String booleanToString(boolean input) {
        return input ? "True" : "False";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            ListNode head = stringToListNode(line);
            line = in.readLine();
            int pos = Integer.parseInt(line);

            boolean ret = new Linked141().hasCycle(head, pos);

            String out = booleanToString(ret);

            System.out.print(out);
        }
    }

}
