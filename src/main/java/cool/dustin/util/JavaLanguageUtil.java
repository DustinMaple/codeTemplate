package cool.dustin.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/28 14:44
 */
public class JavaLanguageUtil {
    public static final Pattern IMPORT_PATTERN = Pattern.compile("extends|implements|\\{");

    public static List<String> analysisImports(String content) {
        String[] split = IMPORT_PATTERN.split(content);
        String extendsStr = "", implementsStr = "";
        if (split.length >= 3) {
            extendsStr = split[1];
        }

        if (split.length >= 4) {
            implementsStr = split[2];
        }

        if (StringUtils.isEmpty(extendsStr) && StringUtils.isEmpty(implementsStr)) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(extendsStr.replaceAll(" ", "").split(",")));
        result.addAll(Arrays.asList(implementsStr.replaceAll(" ", "").split(",")));

        return result;
    }

//    public static void main(String[] args) {
//        String str = "public class Equip extends Item, Container implements Copyable{}";
//        List<String> strings = JavaLanguageUtil.analysisImports(str);
//        for(String s : strings){
//            System.out.println(s);
//        }
//    }
}
