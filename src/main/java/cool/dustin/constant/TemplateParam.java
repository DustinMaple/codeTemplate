package cool.dustin.constant;

import org.apache.commons.lang.StringUtils;

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
    TEMPLATE_PARAM("@MODULE_NAME@", "模块名"),
    /**
     * 驼峰形式
     */
    HUMP_NAME("@HUMP_NAME@", "模块名驼峰形式", "EquipLevelUp"),
    /**
     * 下划线形式
     */
    LINE_NAME("@LINE_NAME@", "模块名下划线形式", "equip_level_up");

    private final String expression;
    private final Pattern pattern;
    private String example;
    private String description;

    TemplateParam(String expression, String description) {
        this.expression = expression;
        this.description = description;
        this.pattern = Pattern.compile(this.expression);
    }

    TemplateParam(String expression, String description, String example) {
        this(expression, description);
        this.example = example;
    }

    public static String getDescription() {
        StringBuilder description = new StringBuilder();

        for (TemplateParam param : TemplateParam.values()) {
            description.append("参数：").append(param.expression).append(", 说明：").append(param.description);
            if (StringUtils.isNotEmpty(param.example)) {
                description.append("，例：").append(param.example);
            }
            description.append("\n");
        }
        return description.toString();
    }

    public String putInParam(String targetStr, String paramStr) {
        return pattern.matcher(targetStr).replaceAll(paramStr);
    }

    public String getExpression() {
        return expression;
    }
}
