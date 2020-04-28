package cool.dustin.model;

/**
 * 插件使用的上下文环境
 * @AUTHOR Dustin
 * @DATE 2020/04/17 13:01
 */
public class PluginContext {
    /**
     * 模板名称
     */
    private final String name;
    /**
     * 所选模板
     */
    private final String selectTemplate;
    /**
     * 模板名首字母大写
     */
    private final String capitalTemplateName;

    public PluginContext(String templateName, String selectTemplate) {
        this.name = templateName;
        this.selectTemplate = selectTemplate;

        // 模板名首字母大写
        char[] chars = templateName.toCharArray();
        if (chars.length > 1 && chars[0] > 'Z') {
            chars[0] = (char) (chars[0] - 32);
        }
        this.capitalTemplateName = String.valueOf(chars);
    }

    public String getName() {
        return name;
    }

    public String getSelectTemplate() {
        return selectTemplate;
    }

    public String getCapitalTemplateName() {
        return capitalTemplateName;
    }
}