package cn.com.mockingbird.robin.iam.support.security.password.weak;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 默认的弱密码库实现
 *
 * @author zhaopeng
 * @date 2023/12/6 19:28
 **/
@Slf4j
public class DefaultPasswordWeakLibImpl implements PasswordWeakLib {

    private static final String DEFAULT_DICTIONARY_PATH = "/dictionaries/10k-most-common.txt";

    /**
     * 弱密码库字典缓存
     */
    private final Map<String, Boolean> dictionary = new HashMap<>(16);

    public DefaultPasswordWeakLibImpl() {
        this.safeReadEmbeddedFile();
    }

    private void safeReadEmbeddedFile() {
        try {
            log.debug("加载嵌入式弱密码库字典");
            this.readStream(this.getClass().getResourceAsStream("/dictionaries/10k-most-common.txt"));
            log.debug("已加载嵌入式弱密码库字典");
        } catch (Exception var2) {
            log.error("无法加载嵌入式弱密码库字典", var2);
        }
    }

    private void readStream(InputStream inputStream) throws IOException {
        if (Objects.nonNull(inputStream)) {
            HashMap<String, Boolean> newEntries = new HashMap<>(16);
            HashSet<String> entriesToKeep = new HashSet<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                if (this.wordExists(line)) {
                    entriesToKeep.add(line);
                } else {
                    newEntries.put(line, true);
                }
            }
            synchronized(this.dictionary) {
                this.dictionary.keySet().retainAll(entriesToKeep);
                this.dictionary.putAll(newEntries);
            }
        }
    }

    @Override
    public Boolean wordExists(String word) {
        synchronized(this.dictionary) {
            return Boolean.TRUE.equals(this.dictionary.get(word));
        }
    }

    @Override
    public List<String> getWordList() {
        return this.dictionary.keySet().stream().toList();
    }

}
