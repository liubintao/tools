package com.robust.online.fileCopy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 从源文件夹复制到目标文件夹
 * @Author: robust
 * @CreateDate: 2018/12/12 9:55
 * @Version: 1.0
 */
public class FileCopy {

    private static final String CHARSET = "GBK";
    private static final Pattern PROFILE_PATTERN = Pattern.compile("(src/profile/prod/)|(src/main/resources/prod/)");
    private static final AtomicBoolean hasCommonLib = new AtomicBoolean(false);
    private static final Set<String> projectSet = new HashSet<>();
    private static final String COMMON_LIB_NAME = "common-lib-0.0.1-SNAPSHOT.jar";

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

        List<String> lines = Files.readAllLines(Paths.get(file), Charset.forName(CHARSET));
        Path sourceDirectory = Paths.get(sourcePath);
        Path targetDirectory = Paths.get(targetPath);
        lines.stream().forEach(
                s -> {
                    try {
                        Files.createDirectories(targetDirectory);
                        copy(s, sourceDirectory, targetDirectory);
                    } catch (IOException e) {
                        System.out.printf("复制文件过程失败，目标目录：%s，文件名：%s，错误信息：%s", targetDirectory, s, e);
                        System.out.println();
                    }
                }
        );
        copyCommonLib(sourceDirectory, targetDirectory);
    }

    private static void copy(String line, Path sourceDirectory, Path targetDirectory) throws IOException {
        String tempLine = line;
        String projectName = tempLine.substring(0, tempLine.indexOf("/"));
        if (!projectSet.contains(projectName) && !tempLine.startsWith("common-lib/")) {
            projectSet.add(projectName);
        }
        if (!hasCommonLib.get() && tempLine.startsWith("common-lib/")) {
            hasCommonLib.compareAndSet(false, true);
            return;
        }
        if(tempLine.indexOf("WebContent") != -1) {
            tempLine = tempLine.replace("WebContent/", "");
            copy(sourceDirectory.resolve(tempLine), Files.createDirectories(targetDirectory.resolve(tempLine)));
            return;
        }

        if(PROFILE_PATTERN.matcher(tempLine).find()) {
            tempLine = tempLine.replaceAll("src/main/resources/[a-z]*/", "WEB-INF/");
            tempLine = tempLine.replaceAll("src/profile/[a-z]*/", "WEB-INF/");
            copy(sourceDirectory.resolve(tempLine), Files.createDirectories(targetDirectory.resolve(tempLine)));
            return;
        }

        if(tempLine.endsWith(".bl")) {
            tempLine = tempLine.replace("designSource", "WEB-INF/conf");
            tempLine = tempLine.replace(".bl", ".xml");
            copy(sourceDirectory.resolve(tempLine), Files.createDirectories(targetDirectory.resolve(tempLine)));
            return;
        }

        if(tempLine.endsWith(".java")) {
            tempLine = tempLine.replace("src/main/java", "WEB-INF/classes");
            Path filePath = sourceDirectory.resolve(tempLine);
            String fileName = filePath.getFileName().toString();
            /**
             * 匹配java中的普通类和内部类
             */
            Pattern pattern = Pattern.compile(fileName.substring(0, fileName.indexOf(".java")) + "[\\$\\w]*.class");
            /**
             * 获取字节码文件所在的路径，用于遍历此文件夹内的字节码文件
             */
            String prefixDirectory = tempLine.substring(0, tempLine.indexOf(fileName));
            Path prefixPath = sourceDirectory.resolve(prefixDirectory);
            Files.walkFileTree(prefixPath, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String name = file.getFileName().toString();
                    Matcher matcher = pattern.matcher(name);
                    if(matcher.find()) {
                        copy(file, targetDirectory.resolve(prefixDirectory).resolve(file.getFileName()));
                    }
                    return super.visitFile(file, attrs);
                }

            });
            return;
        }
    }

    private static void copy(Path source, Path target) throws IOException {
        Files.createDirectories(target);
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void copyCommonLib(Path source, Path target) throws IOException {
        if (hasCommonLib.get()) {
            if (projectSet.isEmpty()) {
                System.out.println("需要从其他项目中copy出lib包..............................");
            } else {
                target = target.resolve("common-lib/WEB-INF/lib");
                Files.createDirectories(target);
                Files.copy(source.resolve(projectSet.iterator().next()).resolve("WEB-INF/lib").resolve(COMMON_LIB_NAME), target.resolve(COMMON_LIB_NAME), StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            System.out.println("没有common-lib包需要处理!");
        }
    }
}
