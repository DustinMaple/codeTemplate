package cool.dustin.xml;

import cool.dustin.constant.TemplateConstants;
import cool.dustin.constant.TemplateParam;
import cool.dustin.model.AbstractTemplateNode;
import cool.dustin.model.Template;
import cool.dustin.model.TemplateClass;
import cool.dustin.model.TemplatePackage;
import cool.dustin.util.JavaLanguageUtil;
import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/24 14:50
 */
public class XmlUtils {
    public static final String ELEMENT_ROOT = "templates";
    public static final String ELEMENT_TEMPLATE = "template";
    public static final String ELEMENT_CLASS = "class";
    public static final String ELEMENT_PACKAGE = "package";
    public static final String ATTR_IMPORT = "importClass";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_DESC = "desc";
    public static final String XML_FILE_SUFFIX = "xml";

    /**
     * 从指定的配置文件中读取模板配置
     * @param filePath
     * @return
     */
    public static List<Template> readTemplatesWithXml(String filePath) {
        if (!filePath.contains(XML_FILE_SUFFIX)) {
            return Collections.emptyList();
        }

        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            return Collections.emptyList();
        }

        Element element = parseXML(file);
        if (!element.getName().equals(ELEMENT_ROOT)) {
            return Collections.emptyList();
        }

        List<Element> children = element.getChildren();
        List<Template> result = new ArrayList<>(children.size());

        Template template;
        for (Element templateElement : children) {
            if (ELEMENT_TEMPLATE.equals(templateElement.getName())) {
                template = (Template) parseNode(templateElement, "");
                template.refreshReference("");
                result.add(template);
            }
        }

        return result;
    }

    /**
     * 将模板写入xml配置文件
     * @param templates
     * @param path
     */
    public static void writeToXml(Collection<Template> templates, String path) {
        File file = new File(path);
        Element root = buildXmlElements(templates);

        Document document = new Document(root);
        Format format = Format.getRawFormat();
        format.setIndent("\t");

        XMLOutputter outPutter = new XMLOutputter(format);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            outPutter.output(document, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析XML文件节点
     * @param element
     * @param parentReferencePath
     * @return
     */
    private static AbstractTemplateNode parseNode(Element element, String parentReferencePath) {
        AbstractTemplateNode node = null;

        switch (element.getName()) {
            case ELEMENT_TEMPLATE:
                node = parseTemplate(element);
                break;
            case ELEMENT_PACKAGE:
                node = parsePackage(element);
                break;
            case ELEMENT_CLASS:
                node = parseClass(element);
                break;
            default:
        }

        if (node == null) {
            throw new RuntimeException("无法解析的节点：" + element.getName());
        }

        if (node instanceof Template) {
            node.setReferencePath(parentReferencePath + JavaLanguageUtil.JAVA_DOT + TemplateParam.MODULE_NAME.getExpression());
        } else {
            node.setReferencePath(parentReferencePath + JavaLanguageUtil.JAVA_DOT + node.getName());
        }

        for (Element child : element.getChildren()) {
            node.addChild(parseNode(child, node.getReferencePath()));
        }

        return node;
    }

    /**
     * 解析模板
     * @param child
     * @return
     */
    private static AbstractTemplateNode parseTemplate(Element child) {
        Template template = new Template();
        template.setName(child.getAttributeValue(ATTR_NAME));
        template.setDescription(child.getAttributeValue(ATTR_DESC));
        return template;
    }

    /**
     *
     * @param child
     * @return
     */
    private static AbstractTemplateNode parsePackage(Element child) {
        TemplatePackage templatePackage = new TemplatePackage();
        templatePackage.setName(child.getAttributeValue(ATTR_NAME));
        return templatePackage;
    }

    private static AbstractTemplateNode parseClass(Element child) {
        TemplateClass templateClass = new TemplateClass();
        templateClass.setName(child.getAttributeValue(ATTR_NAME));
        templateClass.setContent(child.getText());

        String text = child.getAttributeValue(ATTR_IMPORT);
        text = JavaLanguageUtil.noSquareBrackets(text);
        if (StringUtils.isNotEmpty(text)) {
            templateClass.setImportClass(Arrays.asList(text.split(TemplateConstants.STRING_LIST_SPLIT)));
        }

        return templateClass;
    }

    /**
     * 解析XML文件，并返回文件的根节点
     * @param file
     * @return
     */
    private static Element parseXML(File file) {
        Element rootElement = null;
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document doc = saxBuilder.build(file);
            rootElement = doc.getRootElement();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        return rootElement;
    }

    /**
     * 根据模板列表，构建xml文件元素
     * @param templates
     * @return
     */
    private static Element buildXmlElements(Collection<Template> templates) {
        Element rootElement = new Element(ELEMENT_ROOT);
        for (Template template : templates) {
            rootElement.addContent(buildNodeXmlElement(template));
        }
        return rootElement;
    }

    private static Element buildNodeXmlElement(AbstractTemplateNode node) {
        Element nodeElement = generateNodeElement(node);

        if (nodeElement == null) {
            throw new RuntimeException("不识别的类型：" + node.getClass());
        }

        for (AbstractTemplateNode child : node.getChildren()) {
            nodeElement.addContent(buildNodeXmlElement(child));
        }

        return nodeElement;
    }

    private static Element generateNodeElement(AbstractTemplateNode node) {
        if (node instanceof Template) {
            return buildTemplateElement((Template) node);
        } else if (node instanceof TemplatePackage) {
            return buildPackageElement((TemplatePackage) node);
        } else if (node instanceof TemplateClass) {
            return buildClassElement((TemplateClass) node);
        }
        return null;
    }

    private static Element buildTemplateElement(Template node) {
        Element element = new Element(ELEMENT_TEMPLATE);
        element.setAttribute(ATTR_NAME, node.getName());
        element.setAttribute(ATTR_DESC, node.getDescription());
        return element;
    }

    private static Element buildPackageElement(TemplatePackage node) {
        Element element = new Element(ELEMENT_PACKAGE);
        element.setAttribute(ATTR_NAME, node.getName());
        return element;
    }

    private static Element buildClassElement(TemplateClass node) {
        Element element = new Element(ELEMENT_CLASS);
        element.setAttribute(ATTR_NAME, node.getName());
        element.setAttribute(ATTR_IMPORT, node.getImportClass().toString());
        element.setText(node.getContent());
        return element;
    }

}
