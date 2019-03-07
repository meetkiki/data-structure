public class Linked876 {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }

    public ListNode middleNode(ListNode head) {
        ListNode first = new ListNode(0),slow = first,fast = first;
        first.next = head;
        if (first.next == null) return null;
        // 计算长度
        while (fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow.next;
    }

}
