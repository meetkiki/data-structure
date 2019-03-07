public class Linked23 {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }


//    public ListNode mergeKLists(ListNode[] lists) {
//        int[] ints = new int[lists.length];
//        ListNode first = new ListNode(0),temp = first;
//        for (int i = 0; i < lists.length; i++) {
//            if (lists[i] == null){
//                // 标记为已跳过
//                ints[i] = 1;
//                continue;
//            }
//        }
//        while (isNull(lists,ints) > 1){
//            // 找到最小的值
//            int min = Integer.MAX_VALUE,pos = -1;
//            for (int i = 0; i < lists.length; i++) {
//                // 如果为null 则退出循环
//                if (lists[i] == null){
//                    ints[i] = 1;
//                    continue;
//                }
//                if (ints[i] == 1){
//                    continue;
//                }
//                // 找到最小的节点
//                if (min >= lists[i].val){
//                    min = lists[i].val;
//                    pos = i;
//                }
//            }
//            temp.next = lists[pos];
//            temp = temp.next;
//            lists[pos] = lists[pos].next;
//        }
//        for (int i = 0; i < lists.length; i++) {
//            if (lists[i] != null){
//                temp.next = lists[i];
//                temp = temp.next;
//            }
//        }
//        return first.next;
//    }
//
//    /**
//     * 返回不为null的个数
//     * @param lists
//     * @param ints
//     * @return
//     */
//    private int isNull(ListNode[] lists, int[] ints) {
//        int flag = 0;
//        for (int i = 0; i < lists.length; i++) {
//            if (ints[i] == 1){
//                continue;
//            }
//            if (lists[i] != null){
//                flag++;
//            }
//        }
//        return flag;
//    }

    /**
     * 优化方案 分治的方法
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        int length = lists.length;
        int begin = 0;
        if(length == 0) return null;
        return merge(lists, begin, length-1);
    }

    private ListNode merge(ListNode[] lists, int begin, int end) {
        if (begin == end) return lists[begin];
        int middle = (begin + end) / 2;
        ListNode l1 = merge(lists, begin, middle);
        ListNode l2 = merge(lists, middle+1, end);
        return mergeTwoLists(l1, l2);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode head = new ListNode(0),temp = head;
        while (l1 != null && l2 != null){
            //1->2->4, 1->3->4
            if (l1.val >= l2.val){
                head.next = l2;
                l2 = l2.next;
            }else{
                head.next = l1;
                l1 = l1.next;
            }
            head = head.next;
        }
        if (l1 != null) head.next = l1;
        if (l2 != null) head.next = l2;
        return temp.next;
    }


    public boolean isPalindrome(int x) {
        int temp = 0,old = x;
        while (x != 0){
            temp = temp * 10 + x % 10;
            x = x / 10;
        }
        return old == temp;
    }
}
