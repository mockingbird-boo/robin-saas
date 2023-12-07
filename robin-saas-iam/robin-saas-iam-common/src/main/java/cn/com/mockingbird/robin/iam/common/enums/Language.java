package cn.com.mockingbird.robin.iam.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 语言枚举
 *
 * @author zhaopeng
 * @date 2023/12/7 0:10
 **/
@Getter
public enum Language {
    EN("en", "英语"),
    ZH("zh", "中文");
    ;
    private final String locale;

    private final String desc;

    Language(String locale, String desc) {
        this.locale = locale;
        this.desc = desc;
    }

    public static Language getType(String locale) {
        Language[] values = values();
        for (Language language : values) {
            if (StringUtils.equalsIgnoreCase(locale, language.locale)) {
                return language;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return locale;
    }
}
