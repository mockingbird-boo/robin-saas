package cn.com.mockingbird.robin.iam.support.util;

import lombok.experimental.UtilityClass;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 拼音工具类
 *
 * @author zhaopeng
 * @date 2023/12/6 20:04
 **/
@UtilityClass
public class Pinyin4jUtils {

    public String getFirstSpellPinYin(String src, boolean isFullSpell) {
        String targetStr = makeStringByStringSet(getPinyin(src, isFullSpell));
        String[] split = targetStr.split(",");
        if (split.length > 1) {
            targetStr = split[0];
        }

        return targetStr;
    }

    public String makeStringByStringSet(Set<String> stringSet) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        if (!stringSet.isEmpty()) {
            for(Iterator<String> var3 = stringSet.iterator(); var3.hasNext(); ++i) {
                String s = var3.next();
                if (i == stringSet.size() - 1) {
                    str.append(s);
                } else {
                    str.append(s).append(",");
                }
            }
        }
        return str.toString().toLowerCase();
    }

    public Set<String> getPinyin(String src, boolean isFullSpell) {
        if (src != null && !"".equalsIgnoreCase(src.trim())) {
            char[] srcChar = src.toCharArray();
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            String[][] temp = new String[src.length()][];

            for(int i = 0; i < srcChar.length; ++i) {
                char c = srcChar[i];
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    try {
                        temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
                        if (!isFullSpell && i != 0) {
                            String[] tTemps = new String[temp[i].length];

                            for(int j = 0; j < temp[i].length; ++j) {
                                char t = temp[i][j].charAt(0);
                                tTemps[j] = Character.toString(t);
                            }

                            temp[i] = tTemps;
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination var10) {
                        throw new RuntimeException(var10);
                    }
                } else if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
                    temp[i] = new String[]{""};
                } else {
                    temp[i] = new String[]{String.valueOf(srcChar[i])};
                }
            }

            String[] pingYinArray = exchange(temp);
            return new HashSet<>(Arrays.asList(pingYinArray));
        } else {
            return null;
        }
    }

    public String[] exchange(String[][] strJaggedArray) {
        String[][] temp = doExchange(strJaggedArray);
        return temp[0];
    }

    private static String[][] doExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len < 2) {
            return strJaggedArray;
        } else {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newLen = len1 * len2;
            String[] temp = new String[newLen];
            int index = 0;

            for(int i = 0; i < len1; ++i) {
                for(int j = 0; j < len2; ++j) {
                    temp[index] = strJaggedArray[0][i] + strJaggedArray[1][j];
                    ++index;
                }
            }

            String[][] newArray = new String[len - 1][];
            System.arraycopy(strJaggedArray, 2, newArray, 1, len - 2);
            newArray[0] = temp;
            return doExchange(newArray);
        }
    }

    public String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for(int j = 0; j < str.length(); ++j) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

}
