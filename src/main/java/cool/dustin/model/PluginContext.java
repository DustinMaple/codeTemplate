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
    private final String systemName;
    /**
     * 所选模板
     */
    private final String selectTemplate;
    /**
     * 模板名首字母大写
     */
    private final String humpName;
    /**
     * 下划线分割系统名
     */
    private final String lineName;

    public PluginContext(String selectTemplate, String systemName, String humpName, String lineName) {
        this.selectTemplate = selectTemplate;
        this.systemName = systemName;
        this.humpName = humpName;
        this.lineName = lineName;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getSelectTemplate() {
        return selectTemplate;
    }

    public String getHumpName() {
        return humpName;
    }

    public String getLineName() {
        return lineName;
    }
}