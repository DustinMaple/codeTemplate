package cool.dustin.ui.forms;

import javax.swing.*;

/**
 * @author DUSTIN
 */
public class EditClassDialog extends JDialog {
    private JPanel root;
    private JButton buttonOk;

    public EditClassDialog() {
        setContentPane(root);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);

        buttonOk.addActionListener(e -> onOk());
    }

    private void onOk() {
        // add your code here
        dispose();
    }

    public static void main(String[] args) {
        EditClassDialog dialog = new EditClassDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
