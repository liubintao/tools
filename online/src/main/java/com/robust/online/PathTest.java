package com.robust.online;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: robust
 * @CreateDate: 2018/12/12 15:36
 * @Version: 1.0
 */
public class PathTest {
    private static final Pattern PROFILE_PATTERN = Pattern.compile("(src/profile/\\w{3,4}/)|(src/main/resources/(dev|test|sit|uat|prod)/)");

    public static void main(String[] args) {
        String a = "soa-base-service/src/main/java/com/ifp/soa/baseService/GetMobileMessageAction.java";
        String b = "soa-deposit-service/designSource/bl/transfer/timingNextDayTransfer.bl";
        String c = "soa-base-service/WebContent/WEB-INF/conf/applicationContext.xml";
        String d = "soa-base-service/src/profile/prod/conf/applicationContext.xml";
        String e = "inmanage/src/main/resources/prod/common/config.properties";

        System.out.println(PROFILE_PATTERN.matcher(a).find());
        System.out.println(PROFILE_PATTERN.matcher(b).find());
        System.out.println(PROFILE_PATTERN.matcher(c).find());
        System.out.println(PROFILE_PATTERN.matcher(d).find());
        System.out.println(PROFILE_PATTERN.matcher(e).find());

        String str = d.replaceAll("src/profile/[a-z]*/" , "WEB-INF/");
        System.out.println(str);
    }
}
