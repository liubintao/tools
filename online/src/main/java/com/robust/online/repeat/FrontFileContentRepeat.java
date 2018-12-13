package com.robust.online.repeat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * 前端文件对文件内容处理，并处理每行字符串
 */
public class FrontFileContentRepeat {
    private static final String CHARSET = "GBK";

    public static void main(String[] args) throws IOException {
        assert args.length > 0;
        String path = args[0];
        System.out.printf("文件路径：%s", path);
        System.out.println();

        StringBuilder sb = new StringBuilder();
        List<String> lines = Files.readAllLines(Paths.get(path), Charset.forName(CHARSET));
        lines.stream()
                .filter(
                        s -> s.contains("开发库")
                ).forEach(
                s -> {
                    s = s.substring(s.indexOf("ebank_front/src") + 16);
                    if (sb.indexOf(s) == -1) {
                        sb.append(s).append(System.lineSeparator());
                    }
                });

        Files.write(Paths.get(path), sb.toString().getBytes(CHARSET), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
