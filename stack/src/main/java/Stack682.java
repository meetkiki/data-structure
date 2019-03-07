import java.util.Stack;

public class Stack682 {



    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        int sum = 0;
        for (int i = 0; i < ops.length; i++) {
            String op = ops[i];
            switch (op){
                case "C":
                    if (!stack.empty()){
                        Integer pop = stack.pop();
                        sum -= pop;
                    }
                    break;
                case "D":
                    if (!stack.empty()){
                        Integer pop = stack.pop();
                        int currNum = 2 * pop;
                        sum += currNum;
                        stack.push(pop);
                        stack.push(currNum);
                    }
                    break;
                case "+":
                    if (!stack.empty()){
                        Integer pop1 = stack.pop();
                        Integer pop2 = stack.pop();
                        int currNum = pop1 + pop2;
                        sum += currNum;
                        stack.push(pop2);
                        stack.push(pop1);
                        stack.push(currNum);
                    }
                    break;
                default:
                    int num = Integer.valueOf(op);
                    sum += num;
                    stack.push(num);
                    break;
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        String[] strings = {"-21","-66","39","+","+"};
        int points = new Stack682().calPoints(strings);
        System.out.println(points);
    }

}
