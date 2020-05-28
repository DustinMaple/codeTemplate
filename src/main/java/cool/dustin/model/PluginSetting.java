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
    private String templateXmlPath = "";
    /**
     * 作者
     */
    private String author = "";
    /**
     * 源代码路径
     */
    private String sourceRootPath = "";

    public String getTemplateXmlPath() {
        return templateXmlPath;
    }

    public void setTemplateXmlPath(String templateXmlPath) {
        this.templateXmlPath = templateXmlPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceRootPath() {
        return sourceRootPath;
    }

    public void setSourceRootPath(String sourceRootPath) {
        this.sourceRootPath = sourceRootPath;
    }
}
