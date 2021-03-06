package cool.dustin.ui.forms;

import cool.dustin.model.TemplatePackage;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/23 16:42
 */
public class EditPackageForm {
    private JPanel root;
    private JTextField packageNameField;
    private JTextField parentNameField;
    private String parentName;
    private TemplatePackage selectPackage;

    public EditPackageForm(String parentName, TemplatePackage selectPackage) {
        this.parentName = parentName;
        this.selectPackage = selectPackage;
        init();
    }

    private void init() {
        parentNameField.setText(parentName);
        parentNameField.setEditable(false);

        if (selectPackage != null) {
            packageNameField.setText(selectPackage.getName());
        }
    }

    public JPanel getRoot() {
        return root;
    }

    public String getPackageName() {
        return packageNameField.getText();
    }
}
