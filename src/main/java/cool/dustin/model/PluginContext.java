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
    private final String templateName;
    /**
     * 所选模板
     */
    private final String selectTemplate;

    public PluginContext(String templateName, String selectTemplate) {
        this.templateName = templateName;
        this.selectTemplate = selectTemplate;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getSelectTemplate() {
        return selectTemplate;
    }
}
