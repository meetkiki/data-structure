import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 判断用链表表示的字符串是否是回文
 */
public class StringHWH {

    static class SNode{
        Character data;
        SNode next;

        public SNode(){}
        public SNode(char data) {
            this.data = data;
        }

        public Character getData() {
            return data;
        }

        public void setData(Character data) {
            this.data = data;
        }

        public SNode getNext() {
            return next;
        }

        public void setNext(SNode next) {
            this.next = next;
        }
    }
    public class ListNode {
       int val;
       ListNode next;
       ListNode(int x) {
           this.val = x;
       }
   }

    static boolean isHuiWen(SNode head){
        if(head==null||head.next==null){
            return true;
        }
        if(head.next.next==null){
            return head.data.equals(head.next.data);
        }
        SNode slow = head;
        SNode fast = head.next;

        while(fast.next!=null){
            if(slow.data.equals(fast.next.data)){
                if(fast.next.next!=null){
                    return false;
                }
                fast.next = null;
                slow = slow.next;
                fast = slow.next;
                if(fast==null || slow.data.equals(fast.data)){
                    return true;
                }
            }else{
                fast = fast.next;
            }
        }
        return false;
    }

    public static char[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new char[0];
        }

        String[] parts = input.split(",");
        char[] output = new char[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = part.charAt(0);
        }
        return output;
    }

    public static SNode stringToListNode(String input) {
        // Generate array from the input
        char[] nodeValues = stringToIntegerArray(input);

        // Now convert that list into linked list
        SNode dummyRoot = new SNode((char) 0);
        SNode ptr = dummyRoot;
        for(char item : nodeValues) {
            ptr.next = new SNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        //String s = "[a,b,c,d,e,f,g]";
        while ((line = in.readLine()) != null) {
            SNode head = stringToListNode(line);

            boolean out = isHuiWen(head);

            System.out.print(out);
        }
    }

}
