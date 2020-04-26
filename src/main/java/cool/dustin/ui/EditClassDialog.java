package cool.dustin.ui;

import com.intellij.openapi.ui.DialogWrapper;
import cool.dustin.ui.forms.EditClassForm;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/22 11:28
 */
public class EditClassDialog extends DialogWrapper {
    private EditClassForm editClassForm = null;

    public EditClassDialog() {
        super(true);
        init();
        setTitle("Edit Class");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        if (editClassForm == null) {
            editClassForm = new EditClassForm();
        }
        return editClassForm.getRoot();
    }
}
