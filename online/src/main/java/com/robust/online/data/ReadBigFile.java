package com.robust.online.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: robust
 * @CreateDate: 2019/1/17 19:21
 * @Version: 1.0
 */
public class ReadBigFile {

    static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\bintao\\Desktop\\inmanage-01-16-2019-1.appLog");
        execute(path);
    }

    private static void execute(Path path) {
        File file = path.toFile();
        try (FileInputStream in = new FileInputStream(file);
             FileChannel channel = in.getChannel();
        ) {
            MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                    channel.size());
            byte[] b = new byte[1024];
            int len = (int) file.length();

            long begin = System.currentTimeMillis();

            for (int offset = 0; offset < len; offset += 1024) {

                if (len - offset > BUFFER_SIZE) {
                    buff.get(b);
                } else {
                    buff.get(new byte[len - offset]);
                }
                String str = new String(b);
                String[] ss = str.split("\t");
                Stream.of(ss).filter(s ->
                                s.contains("c300175467") && s.contains("20190116114526")
//                        s.contains("IDC001-18.73.51.3-inmanage-1547610949608P8lOBzI")
                ).forEach(str1 -> {
                    System.out.println(str1);
                });
            }
            long end = System.currentTimeMillis();
            System.out.println("time is:" + (end - begin));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
