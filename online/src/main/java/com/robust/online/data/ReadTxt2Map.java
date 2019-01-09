package com.robust.online.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;

/**
 * @Description:
 * @Author: robust
 * @CreateDate: 2019/1/8 10:21
 * @Version: 1.0
 */
public class ReadTxt2Map {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\bintao\\Desktop\\test.txt");
        try {
            List<String> list = readAllLines(path);
            list.stream().forEach((s) -> {
//                System.out.println(s);
                String[] strings = s.split("\t");
                System.out.println("tableMap.put(\"" + strings[1] + "\",\"" + strings[0] + "\");");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
