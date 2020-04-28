package cool.dustin.ui.forms;

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
