import java.util.Stack;

/**
 * 用栈实现队列
 */
class MyQueue {

    Stack<Integer> data1 = new Stack<>();
    Stack<Integer> data2 = new Stack<>();

    MyQueue() {}

    /** Push element x to the back of queue. */
    void push(int x) {
        while (!data1.empty()) {
            data2.push(data1.peek()); data1.pop();
        }
        data1.push(x);
        while (!data2.empty()) {
            data1.push(data2.peek()); data2.pop();
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    int pop() {
        int val = data1.peek(); data1.pop();
        return val;
    }

    /** Get the front element. */
    int peek() {
        return data1.peek();
    }

    /** Returns whether the queue is empty. */
    boolean empty() {
        return data1.empty();
    }


    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();

        myQueue.push(1);
        int param_2 = myQueue.pop();
        int param_3 = myQueue.peek();
        boolean param_4 = myQueue.empty();
    }
}