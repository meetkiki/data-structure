public class Stack224 {



    public int calculate(String s) {
        s += '#';
        char[] chars = s.toCharArray();
        // 数据栈
        long[] stack1 = new long[chars.length];
        // 符号栈
        char[] stack2 = new char[chars.length];
        int top1 = 0,top2 = 0;
        long sum = 0;
        // 1 + 2 * 3
        for (int i = 0; i < chars.length;) {
            if (chars[i] == ' '){
                i ++ ;continue;
            }
            // 数字
            if (chars[i] >= 48 && chars[i] <= 57){
                sum = chars[i] - '0';
                if (i!=0 && chars[i-1] >= 48 && chars[i-1] <= 57){
                    sum = stack1[top1 - 1] * 10 + sum;
                    stack1[top1 - 1] = sum;
                }else{
                    stack1[top1++] = sum;
                }
                i ++;continue;
            }
            // 如果为第一个运算符直接压入
            if (chars[i] == '('){
                stack2[top2++] = chars[i];
                i ++;continue;
            }
            // 符号
            char top;
            if (top2 == 0){
                top = '#';
            }else{
                top = stack2[--top2];
            }
            if (priority(chars[i],top) == 0){
                i ++;continue;
            }
            // 小于则将当前运算符压入栈
            if (priority(chars[i],top) > 0){
                if (top2 != 0){
                    stack2[top2++] = top;
                }
                stack2[top2++] = chars[i];
                i ++;continue;
            }
            // 大于
            if (priority(chars[i],top) < 0 || chars[i] == '#'){
                if(top1 == 1){
                    return (int)stack1[--top1];
                }
                // 取出栈顶的两个数 进行运算 结果压入栈
                int r = (int)stack1[--top1];
                int l = (int)stack1[--top1];
                int operation = operation(top, l, r);
                // 将运算结果压入栈顶
                stack1[top1++] = operation;
                if (!(chars[i] == ')' || chars[i] == '#')){
                    stack2[top2++] = chars[i];
                }
            }
            // 等于
            if (top2 != 0 && ((chars[i]==')' && stack2[top2 - 1] == '(') || chars[i]=='#' && stack2[top2 - 1] == '(')){
                --top2;
            }
            if (i == chars.length - 1 && top2 == 0){
                break;
            }
            if (i != chars.length - 1){
                i ++;
            }
        }
        return (int)stack1[--top1];
    }

    /**
     * 判断运算符优先级
     * @return
     */
    //判断优先级
    int priority(char x,char y)
    {
        if (x == '+' || x == '-'){
            switch (y){
                case '+': return -1;
                case '-': return -1;
                case '*': return -1;
                case '/': return -1;
                case '(': return 1;
                case ')': return 1;
                case '#': return 1;
            }
        }
        if (x == '*' || x == '/'){
            switch (y){
                case '+': return 1;
                case '-': return 1;
                case '*': return -1;
                case '/': return -1;
                case '(': return 1;
                case ')': return 1;
                case '#': return 1;
            }
        }
        if (x == '('){
            switch (y){
                case '+': return 1;
                case '-': return 1;
                case '*': return 1;
                case '/': return 1;
                case '(': return 1;
                case ')': return 0;
                case '#': return 1;
            }
        }
        if (x == ')'){
            switch (y){
                case '+': return -1;
                case '-': return -1;
                case '*': return -1;
                case '/': return -1;
                case '(': return 0;
                case ')': return -1;
                case '#': return -1;
            }
        }
        if (x == '#'){
            switch (y){
                case '+': return -1;
                case '-': return -1;
                case '*': return -1;
                case '/': return -1;
                case '(': return -1;
                case ')': return -1;
                case '#': return -1;
            }
        }
        return -1;
    }

    //判断优先级
    int operation(char c,int x,int y)
    {
        if (c == '*'){
            return x*y;
        }
        if (c == '/'){
            return x/y;
        }
        if (c == '+'){
            return x+y;
        }
        if (c == '-'){
            return x-y;
        }
        return 0;
    }


    public static void main(String[] args) {
        System.out.println(new Stack224().calculate("1 + (1)"));
    }

}
