package cool.dustin.datas;

import cool.dustin.model.Template;

import java.util.Collection;
import java.util.TreeMap;

/**
 * 插件运行时数据
 * @AUTHOR Dustin
 * @DATE 2020/04/14 15:44
 */
public class PluginRuntimeData {

    /**
     * 所有模板<name, template>
     */
    private TreeMap<String, Template> templateTreeMap = new TreeMap<>();

    /**
     * 检查模板名称
     * @param name
     * @return
     */
    public int checkTemplateName(String name) {
        if (templateTreeMap.containsKey(name)) {
            return -1;
        }
        return 0;
    }

    /**
     * 获取模板
     * @param name
     * @return
     */
    public Template getTemplate(String name) {
        return templateTreeMap.get(name);
    }

    /**
     * 新增模板
     * @param tempalte
     */
    public void addTemplate(Template tempalte) {
        templateTreeMap.put(tempalte.getName(), tempalte);
    }

    public static PluginRuntimeData getInstance() {
        return InstanceHandler.instance;
    }

    public Collection<Template> getAllTemplate() {
        return templateTreeMap.values();
    }

    private static class InstanceHandler {
        private static PluginRuntimeData instance = new PluginRuntimeData();
    }
}
