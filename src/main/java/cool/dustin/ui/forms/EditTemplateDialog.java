package cool.dustin.ui.forms;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EditTemplateDialog extends DialogWrapper {
    private JPanel contentPane;
    private JTextField textField1;
    private JButton button1;
    private JTree tree1;
    private JButton button2;
    private JButton button3;

    public EditTemplateDialog() {
        super(true);
    }


    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }
}
