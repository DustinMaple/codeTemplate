package cool.dustin.mock;

import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.Template;
import cool.dustin.model.TemplateClass;
import cool.dustin.model.TemplatePackage;
import cool.dustin.xml.XmlUtils;

import java.util.Collections;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/14 15:58
 */
public class PluginMock {
    public static final String MOCK_TEMPLATE_NAME = "module";

    /**
     * 创建模拟模板
     */
    public static void mockTemplate() {
        Template template1 = new Template();

        // 服务
        TemplatePackage servicePackage = new TemplatePackage();
        servicePackage.setName("service");

        TemplatePackage serviceImplPackage = new TemplatePackage();
        serviceImplPackage.setName("impl");

        TemplateClass templateClass1 = new TemplateClass();
        templateClass1.setName("WingService");
        templateClass1.setContent("public interface WingService {\n" +
                "\tvoid init();\n\n\tvoid shutdown();\n" +
                "}");

        String[] importClazz = new String[]{"WingService", "ToolUtils"};
        TemplateClass templateClass2 = new TemplateClass();
        templateClass2.setName("WingServiceImpl");
        templateClass2.setImportClass(importClazz);
        templateClass2.setContent("public class WingServiceImpl implements WingService{\n" +
                "\tpublic void init(){\n\t\t\n\t}\n\n\tpublic void shutdown(){\n\t\t\n\t}\n" +
                "}");

        serviceImplPackage.addChild(templateClass2);

        servicePackage.addChild(serviceImplPackage);
        servicePackage.addChild(templateClass1);

        template1.addChild(servicePackage);

        template1.setName(MOCK_TEMPLATE_NAME);
        template1.setDescription("这是一个测试模板");

        PluginRuntimeData.getInstance().addTemplate(template1);

        XmlUtils.writeToXml(Collections.singleton(template1), "e:/mock.xml");
    }
}
