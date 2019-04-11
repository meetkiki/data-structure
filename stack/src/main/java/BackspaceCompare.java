/**
 * 844. 比较含退格的字符串
 */
public class BackspaceCompare {


    public boolean backspaceCompare(String S, String T) {
        if (S.length() == 1 && S.equals(T.length())) return true;
        char[] schar = S.toCharArray();
        char[] tchar = T.toCharArray();
        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();
        for (int i = 0; i < schar.length; i++) {
            if (schar[i] == '#'){
                if (s1.length() == 0){
                    continue;
                }
                s1.setLength(s1.length()-1);
            }else{
                s1.append(schar[i]);
            }
        }
        for (int i = 0; i < tchar.length; i++) {
            if (tchar[i] == '#'){
                if (s2.length() == 0){
                    continue;
                }
                s2.setLength(s2.length()-1);
            }else{
                s2.append(tchar[i]);
            }
        }
        return s1.toString().equals(s2.toString());
    }

    public static void main(String[] args) {
        System.out.println(new BackspaceCompare().backspaceCompare("ab#c","ad#c"));
    }
}
