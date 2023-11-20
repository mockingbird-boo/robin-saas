package cn.com.mockingbird.robin.web.desensitize;

import cn.com.mockingbird.robin.common.constant.Standard;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏工具类，从网上找的一个工具类，暂未测试
 *
 * @author zhaopeng
 * @date 2023/11/21 2:10
 **/
@SuppressWarnings("unused")
@UtilityClass
public class DesensitizeUtils {

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public String chineseName(final String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return Standard.Str.EMPTY;
        }
        final String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), Standard.Str.STAR);
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public String chineseName(final String familyName, final String givenName) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return Standard.Str.EMPTY;
        }
        return chineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：420**********5762>
     */
    public String idCardNum(final String id) {
        if (StringUtils.isBlank(id)) {
            return Standard.Str.EMPTY;
        }

        return StringUtils.left(id, 3).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(id, 4), StringUtils.length(id), Standard.Str.STAR),
                        "***"));
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     */
    public String fixedPhone(final String num) {
        if (StringUtils.isBlank(num)) {
            return Standard.Str.EMPTY;
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), Standard.Str.STAR);
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public String mobilePhone(final String num) {
        if (StringUtils.isBlank(num)) {
            return Standard.Str.EMPTY;
        }
        return StringUtils.left(num, 3).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), Standard.Str.STAR),
                        "***"));

    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param sensitiveSize 敏感信息长度
     */
    public String address(final String address, final int sensitiveSize) {
        if (StringUtils.isBlank(address)) {
            return Standard.Str.EMPTY;
        }
        final int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, Standard.Str.STAR);
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     */
    public String email(final String email) {
        if (StringUtils.isBlank(email)) {
            return Standard.Str.EMPTY;
        }
        final int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, 1), index, Standard.Str.STAR)
                    .concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     */
    public String bankCard(final String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return Standard.Str.EMPTY;
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), Standard.Str.STAR),
                "******"));
    }

    /**
     * [api秘钥] 前3位，后3位，其他用星号隐藏每位1个星号<例子:Aj3**********8Kl>
     */
    public String apiSecret(final String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return Standard.Str.EMPTY;
        }
        return StringUtils.left(cardNum, 3).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(cardNum, 3), StringUtils.length(cardNum), Standard.Str.STAR),
                "******"));
    }

}
