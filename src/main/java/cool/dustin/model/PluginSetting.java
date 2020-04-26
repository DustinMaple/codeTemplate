package cool.dustin.model;

/**
 * 插件设置
 * @AUTHOR Dustin
 * @DATE 2020/04/14 18:36
 */
public class PluginSetting {
    /**
     * 模板文件保存路径
     */
    private String templateXmlPath = "e:/mock.xml";

    public String getTemplateXmlPath() {
        return templateXmlPath;
    }

    public void setTemplateXmlPath(String templateXmlPath) {
        this.templateXmlPath = templateXmlPath;
    }
}
