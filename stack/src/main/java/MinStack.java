import java.util.Stack;

/**
 * 155. 最小栈
 */
class MinStack {
    private Stack<Integer> s1 = new Stack();
    private Stack<Integer> s2 = new Stack();

    /** initialize your data structure here. */
    public MinStack() {
    }
    
    public void push(int x) {
        s1.push(x);
        if (s2.isEmpty() || s2.peek() >= x) s2.push(x);
    }
    
    public void pop() {
        int peek = s1.pop();
        if (s2.peek() == peek) s2.pop();
    }
    
    public int top() {
        return s1.peek();
    }
    
    public int getMin() {
        return s2.peek();
    }
}