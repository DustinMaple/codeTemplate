package cool.dustin.ui.forms;

import cool.dustin.constant.TemplateParam;
import cool.dustin.model.TemplateClass;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/23 16:41
 */
public class EditClassForm {
    private final String parentName;
    private TemplateClass selectClass;
    private JPanel root;
    private JTextField parentNameField;
    private JTextField nodeNameField;
    private JTextArea classContentField;
    private JTextField classNameField;
    private JTextPane description;

    private TemplateClass templateClass = new TemplateClass();

    public EditClassForm(String parentName, TemplateClass selectClass) {
        this.parentName = parentName;
        this.selectClass = selectClass;
        init();
    }

    private void init() {
        parentNameField.setText(parentName);
        parentNameField.setEditable(false);

        if (selectClass != null) {
            this.nodeNameField.setText(selectClass.getName());
            this.classNameField.setText(selectClass.getClassName());
            this.classContentField.setText(selectClass.getContent());
        }


        description.setText("参数只有在类名和类的内容中有效，以下为可用参数\n" + TemplateParam.getDescription());
    }

    public JPanel getRoot() {
        return root;
    }

    public TemplateClass getTemplateClass() {
        return templateClass;
    }

    public String getNodeName() {
        return nodeNameField.getText();
    }

    public String getClassContent() {
        return classContentField.getText();
    }

    public String getClassName() {
        return classNameField.getText();
    }
}
