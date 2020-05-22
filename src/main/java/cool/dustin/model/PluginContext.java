package cool.dustin.model;

import java.util.ArrayList;
import java.util.List;

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
    /**
     * 项目根路径列表，其中包含多种根路径，如测试路径，资源路径，源码路径
     */
    private List<String> projectRootPath = new ArrayList<>(3);
    /**
     * 使用模板时选中的包
     */
    private String selectPackage;

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

    public List<String> getProjectRootPath() {
        return projectRootPath;
    }

    public void setSelectPackage(String selectPackage) {
        this.selectPackage = selectPackage;
    }

    public String getSelectPackage() {
        return selectPackage;
    }
}