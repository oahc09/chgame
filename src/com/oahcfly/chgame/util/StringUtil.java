
package com.oahcfly.chgame.util;

import java.util.HashSet;

public class StringUtil {
    /** 去除重复字符 */
    public static String removeRepeatedCharII(String text) {
        String[] strings = text.split("");
        HashSet<String> hashSet = new HashSet<String>();
        for (String s : strings) {
            hashSet.add(s);
        }

        String result = "";
        for (String s : hashSet) {
            if (s.length() > 0) {
                result += s;
            }
        }
        return result;
    }

    /**
     * 
     * <pre>
     * 字符串去重
     * 
     * date: 2014-12-20
     * </pre>
     * @author caohao
     * @param text
     * @return
     */
    public static String removeRepeatedChar(String text) {
        char[] chars = text.toCharArray();
        char[] existChar = new char[chars.length];
        int i = 0;
        StringBuffer sb = new StringBuffer();
        for (char ch : chars) {
            if (isExistsChar(existChar, ch)) {
                continue;
            }
            existChar[i] = ch;
            sb.append(ch);
            ++i;
        }

        if (chars.length == i) {
            return text;
        }
        return sb.toString();
    }

    static boolean isExistsChar(char[] chars, char ch) {
        char[] arrayOfChar = chars;
        int j = chars.length;
        for (int i = 0; i < j; ++i) {
            char c = arrayOfChar[i];
            if (c == ch) {
                return true;
            }
        }

        return false;
    }

}
