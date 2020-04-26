package cool.dustin.ui;

import com.intellij.openapi.ui.DialogWrapper;
import cool.dustin.ui.forms.EditPackageForm;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/22 11:28
 */
public class EditPackageDialog extends DialogWrapper {
    private EditPackageForm editPackageForm = null;

    public EditPackageDialog() {
        super(true);
        init();
        setTitle("Edit Package");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        if (editPackageForm == null) {
            editPackageForm = new EditPackageForm();
        }
        return editPackageForm.getRoot();
    }
}
