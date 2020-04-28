package cool.dustin.constant;

import java.util.regex.Pattern;

/**
 * 定义模板内置的所有参数
 * @AUTHOR Dustin
 * @DATE 2020/04/28 14:46
 */
public enum TemplateParam {
    /**
     * 模板名参数
     */
    TEMPLATE_PARAM("@TEMPLATE_PARAM@"),
    /**
     * 驼峰形式
     */
    HUMP_NAME("@CLASS_NAME@"),
    /**
     * 下划线形式
     */
    LINE_NAME("@LINE_NAME@");

    private final String expression;
    private final Pattern pattern;

    TemplateParam(String expression) {
        this.expression = expression;
        this.pattern = Pattern.compile(this.expression);
    }

    public String putInParam(String targetStr, String paramStr) {
        return pattern.matcher(targetStr).replaceAll(paramStr);
    }

    public String getExpression() {
        return expression;
    }
}
