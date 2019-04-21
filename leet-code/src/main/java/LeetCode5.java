/**
 * @author ypt
 * @ClassName 最长回文子串
 * @Description Longest Palindromic Substring
 * @date 2019/3/26 9:48
 */
public class LeetCode5 {
    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     *
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * 示例 2：
     *
     * 输入: "cbbd"
     * 输出: "bb"
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if(s.length() <= 1)
            return s;
        for(int i = s.length();i > 0; i--) {//子串长度
            for (int j = 0; j <= s.length() - i; j++) {
                String sub = s.substring(j , i + j);//子串位置
                int count = 0;//计数，用来判断是否对称
                for (int k = 0; k < sub.length() / 2; k++) {//左右对称判断
                    if (sub.charAt(k) == sub.charAt(sub.length() - k - 1))
                        count++;
                }
                if (count == sub.length() / 2)
                    return sub;
            }
        }
        return "";//表示字符串中无回文子串
    }
}
