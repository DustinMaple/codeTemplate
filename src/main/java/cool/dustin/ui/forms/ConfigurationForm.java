package cool.dustin.ui.forms;

import cool.dustin.config.CodeTemplateConfiguration;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/21 21:15
 */
public class ConfigurationForm {
    private JPanel root;
    private JButton button1;
    private CodeTemplateConfiguration codeTemplateConfiguration;

    private EditTemplateDialog editTemplateDialog = null;

    public ConfigurationForm(CodeTemplateConfiguration codeTemplateConfiguration) {
        this.codeTemplateConfiguration = codeTemplateConfiguration;
        init();
    }

    private void init() {
        button1.addActionListener((b) -> onCreate());
    }

    private void onCreate() {
        if (editTemplateDialog == null) {
            editTemplateDialog = new EditTemplateDialog();
        }
        editTemplateDialog.showAndGet();
    }

    public JPanel getRoot() {
        return root;
    }
}
