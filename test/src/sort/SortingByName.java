package sort;
import java.util.Comparator;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import javax.naming.Name;

import static sort.SortingByNameUtil.*;
/**
 * Created by csm26 on 2017-04-16.
 */

/**
 * 한글 > 영어 > 숫자 > 특수문자 순서 정렬 객체
 */
public class SortingByName {
    private static final int REVERSE  = -1;
    private static final int LEFT_FIRST  = -1;
    private static final int RIGHT_FIRST  = 1;

    public static Comparator<String> getComparator() {
        return  new Comparator<String>() {
            public int compare(String left, String right) {
                return SortingByName.compare(left, right);
            }
        };
    }

    /**
     * 한글 > 영어 > 숫자 > 특수문자 순서 비교 함수
     * @param left
     * @param right
     * @return
     */
    public static int compare(String left, String right) {

        left = StringUtils.upperCase(left).replaceAll(" ", "");
        right = StringUtils.upperCase(right).replaceAll(" ", "");

        int leftLen = left.length();
        int rightLen = right.length();
        int minLen = Math.min(leftLen, rightLen);

        for(int i = 0; i < minLen; ++i) {
            char leftChar = left.charAt(i);
            char rightChar = right.charAt(i);

            if (leftChar != rightChar) {
                if (isKoreanAndEnglish(leftChar, rightChar)
                        || isKoreanAndNumber(leftChar, rightChar)
                        || isEnglishAndNumber(leftChar, rightChar)
                        || isKoreanAndSpecial(leftChar, rightChar)) {
                    return (leftChar - rightChar) * REVERSE;
                } else if (isEnglishAndSpecial(leftChar, rightChar)
                        || isNumberAndSpecial(leftChar, rightChar)) {
                    if (isEnglish(leftChar) || isNumber(leftChar)) {
                        return LEFT_FIRST;
                    } else {
                        return RIGHT_FIRST;
                    }
                } else {
                    return leftChar - rightChar;
                }
            }
        }

        return leftLen - rightLen;
    }

    private static boolean isKoreanAndEnglish(char ch1, char ch2) {
        return (isEnglish(ch1) && isKorean(ch2))
                || (isKorean(ch1) && isEnglish(ch2));
    }

    private static boolean isKoreanAndNumber(char ch1, char ch2) {
        return (isNumber(ch1) && isKorean(ch2))
                || (isKorean(ch1) && isNumber(ch2));
    }

    private static boolean isEnglishAndNumber(char ch1, char ch2) {
        return (isNumber(ch1) && isEnglish(ch2))
                || (isEnglish(ch1) && isNumber(ch2));
    }

    private static boolean isKoreanAndSpecial(char ch1, char ch2) {
        return (isKorean(ch1) && isSpecial(ch2))
                || (isSpecial(ch1) && isKorean(ch2));
    }

    private static boolean isEnglishAndSpecial(char ch1, char ch2) {
        return (isEnglish(ch1) && isSpecial(ch2))
                || (isSpecial(ch1) && isEnglish(ch2));
    }

    private static boolean isNumberAndSpecial(char ch1, char ch2) {
        return (isNumber(ch1) && isSpecial(ch2))
                || (isSpecial(ch1) && isNumber(ch2));
    }
    public static void main(String[] args){
        Vector <String> Names = new Vector<String> ();
        Names.add("최승민");
        Names.add("정두진");
        Names.add("유인경");
        Names.add("엄준식");
        Names.add("김장현");
        Names.add("김회민");
    }
}