package com.robust.online.fileCopy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 前端文件从源文件夹复制到目标文件夹
 * @Author: robust
 * @CreateDate: 2018/12/12 9:55
 * @Version: 1.0
 */
public class FrontFileCopy {

    private static final String CHARSET = "GBK";

    public static void main(String[] args) throws IOException {
        /**
         * 源文件夹名字通过args[0]传过来
         * 目标文件夹名字通过args[1]传过来
         * 要处理的文件名通过args[2]传过来
         */
        /*final String sourcePath = "H:\\tools\\project";
        final String targetPath = "H:\\tools\\target";
        String file = "H:\\tools\\svn.txt";*/
        final String sourcePath = args[0];
        final String targetPath = args[1];
        String file = args[2];
        final Set<String> projectSet = new HashSet<>();
        final Set<String> commonSet = new HashSet<>();

        List<String> lines = Files.readAllLines(Paths.get(file), Charset.forName(CHARSET));
        Path sourceDirectory = Paths.get(sourcePath);
        Path targetDirectory = Paths.get(targetPath);
        lines.stream().forEach(
                s -> {
                    try {
                        Files.createDirectories(targetDirectory);
                        copy(s, sourceDirectory, targetDirectory, commonSet, projectSet);
                    } catch (IOException e) {
                        System.out.printf("复制文件过程失败，目标目录：%s，文件名：%s，错误信息：%s", targetDirectory, s, e);
                        System.out.println();
                    }
                }
        );
        copyCommon(sourceDirectory, targetDirectory, commonSet, projectSet);
    }

    public static void copy(String line, Path sourceDirectory, Path targetDirectory, Set<String> commonSet, Set<String> projectSet) throws IOException {
        String tempLine = line;
        if (tempLine.startsWith("common/")) {
            commonSet.add(tempLine);
            return;
        } else {
            String projectName = tempLine.substring(0, tempLine.indexOf("/"));
            //如果还没有执行过此项目的复制过程，则需将commonSet里的每一个文件copy到此项目中
            if (!projectSet.contains(projectName)) {
                projectSet.add(projectName);
            }
            //执行当前文件的copy
            copy(sourceDirectory.resolve(tempLine), Files.createDirectories(targetDirectory.resolve(tempLine)));
        }
    }

    private static void copyCommon(Path sourceDirectory, Path targetDirectory, Set<String> commonSet, Set<String> projectSet) throws IOException {
        if (projectSet.isEmpty()) {
            System.out.println("需要提供一个上线包!");
            return;
        }
        commonSet.stream().forEach(s -> {
            String tempCommon = s.replace("common/", projectSet.iterator().next() + "/");
            try {
                copy(sourceDirectory.resolve(tempCommon), Files.createDirectories(targetDirectory.resolve(s)));
            } catch (IOException e) {
                System.out.printf("复制文件过程失败，目标目录：%s，文件名：%s，错误信息：%s", targetDirectory, s, e);
            }
        });
    }

    private static void copy(Path source, Path target) throws IOException {
        Files.createDirectories(target);
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
