import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Linked19 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 1->2->3->4->5, 和 n = 2.
        ListNode first = new ListNode(0),slow = first,fast = first,res = null;
        first.next = head;
        if (first.next == null || n == 0) return first.next;
        if (first.next.next == null) return null;
        // 计算长度
        int i = 0,temp = 0,cur = 0;
        while (fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
            i+=2;
        }
        if (fast.next != null && fast.next.next == null) i++;
        if (i%2==1) slow = slow.next;
        // 去头
        if (i == n){
            first.next = first.next.next;
        }else{
            temp = i + 1 - n;
            if (temp > (i/2)){
                // 从慢指针开始
                int emp = temp - (i/2+i%2);
                if (emp == 0) {
                    temp--;
                    res = first.next;
                }else{
                    temp = emp;
                    res = slow;
                }
            }else{
                temp--;
                res = first.next;
            }
            // 从半部分开始
            while (++cur != temp){
                res = res.next;
            }
            res.next = res.next.next;
        }
        return first.next;
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

    public static String listNodeToString(ListNode node) {
        if (node == null) {
            return "[]";
        }

        String result = "";
        while (node != null) {
            result += Integer.toString(node.val) + ", ";
            node = node.next;
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            ListNode head = stringToListNode(line);
            line = in.readLine();
            int n = Integer.parseInt(line);
            //[1,2,3,4,5,6,7,8,9,10]
            //2
            ListNode ret = new Linked19().removeNthFromEnd(head, n);

            String out = listNodeToString(ret);

            System.out.print(out);
        }
    }


}
