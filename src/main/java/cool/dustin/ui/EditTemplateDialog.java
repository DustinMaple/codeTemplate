package cool.dustin.ui;

import com.intellij.openapi.ui.DialogWrapper;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.ui.forms.ConfigurationForm;
import cool.dustin.ui.forms.EditTemplateForm;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/22 11:28
 */
public class EditTemplateDialog extends DialogWrapper {
    private final ConfigurationForm configurationForm;
    private final String selectTemplateName;
    private EditTemplateForm editTemplateForm = null;
    /**
     * 是否变更
     */
    private boolean isChanged = false;

    public EditTemplateDialog(ConfigurationForm configurationForm, String selectTemplateName) {
        super(true);
        this.configurationForm = configurationForm;
        this.selectTemplateName = selectTemplateName;
        init();
        setTitle("Edit Template");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        if (editTemplateForm == null) {
            editTemplateForm = new EditTemplateForm(this, selectTemplateName);
        }
        return editTemplateForm.getRoot();
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        saveTemplate();
        configurationForm.getCodeTemplateConfiguration().setModified(this.isChanged);
    }

    private void saveTemplate() {
        if (StringUtils.isNotEmpty(selectTemplateName)) {
            PluginRuntimeData.getInstance().removeTemplate(selectTemplateName);
        }

        PluginRuntimeData.getInstance().addTemplate(editTemplateForm.getTemplate());
    }

    public void changed() {
        this.isChanged = true;
    }
}
