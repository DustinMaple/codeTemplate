package xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/21 15:57
 */
public class TestReadTemplateFile {
    private String filePath = "e:/codeTemplate/template.xml";
    private String readFilePath = "e:/codeTemplate/templates.xml";

    @Test
    public void testCreateDefaultFile() {
        createDefaultFile();
    }

    @Test
    public void testReadTemplateFile() {
        File file = getFile();
        if (file == null) {
            file = createDefaultFile();
        }
        parseXML(file);
    }

    private void parseXML(File file) {
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document doc = saxBuilder.build(file);
            Element rootElement = doc.getRootElement();
            parseElement(rootElement);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

    private void parseElement(Element element) {
        String name = element.getName();
        String text = element.getText();
        List<Element> children = element.getChildren();
        System.out.println("name:" + name + ", text:" + text + ", childrenSize:" + children.size());

        for (Element child : children) {
            parseElement(child);
        }
    }

    private File getFile() {
        File file = new File(readFilePath);
        if (!file.exists()) {
            return null;
        }

        if (file.isDirectory()) {
            return null;
        }
        return file;
    }

    private File createDefaultFile() {
        File file = new File(filePath);
        Element root = new Element(XmlFileConstant.ELEMENT_ROOT);
        Element clazz = new Element(XmlFileConstant.ELEMENT_CLASS);
        clazz.setAttribute(XmlFileConstant.ATTR_NAME, "WingService");
        clazz.setText("public interface WingService{\\n\\t\\n}\\n");
        System.out.println(clazz.getText());
        root.addContent(clazz);
        Document document = new Document(root);
        Format format = Format.getCompactFormat();
        format.setIndent("\t");
        format.setTextMode(Format.TextMode.TRIM_FULL_WHITE);

        XMLOutputter outPutter = new XMLOutputter(format);
        try {
            outPutter.output(document, new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
