package cool.dustin.util;

/**
 * Java语法工具类
 * @AUTHOR Dustin
 * @DATE 2020/04/28 14:44
 */
public class JavaLanguageUtil {
    /**
     * java语法关键字
     */
    public static final String JAVA_INTERFACE = "interface";
    /**
     * java语法关键字
     */
    public static final String JAVA_IMPORT = "import";
    /**
     * 包路径的点
     */
    public static final String JAVA_DOT = ".";

    /**
     * 去掉字符串的方括号
     * @param str
     * @return
     */
    public static String noSquareBrackets(String str) {
        return str == null ? "" : str.replaceAll("[\\[\\]]", "");
    }
}
