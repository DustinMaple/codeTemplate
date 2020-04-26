package cool.dustin.ui.forms;

import cool.dustin.constant.MessageType;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.AbstractTemplateNode;
import cool.dustin.model.Template;
import cool.dustin.model.TemplatePackage;
import cool.dustin.ui.EditTemplateDialog;
import cool.dustin.util.MessageUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;

import static javax.swing.tree.TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;

/**
 * @author DUSTIN
 */
public class EditTemplateForm {
    private static final String TEMPLATE_ROOT = "TemplateRoot";
    private final EditTemplateDialog editTemplateDialog;
    private final String selectTemplateName;
    private JPanel root;
    private JTextField textField1;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTree templateNodeTree;
    private Set<String> packageName = new HashSet<>();
    private Template template;

    public EditTemplateForm(EditTemplateDialog editTemplateDialog, String selectTemplateName) {
        this.editTemplateDialog = editTemplateDialog;
        this.selectTemplateName = selectTemplateName;
        init();
    }

    private void init() {
        findTemplate();
        initNodeTree();
        createButton.addActionListener(e -> doCreate());
        editButton.addActionListener(e -> doEdit());
        deleteButton.addActionListener(e -> doDelete());

//        //create the root node
//        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
//        //create the child nodes
//        DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Vegetables");
//        DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Fruits");
//        //add the child nodes to the root node
//        root.add(vegetableNode);
//        root.add(fruitNode);
//
//        TreeSelectionModel selectionModel = templateNodeTree.getSelectionModel();
//        selectionModel.setSelectionMode(DISCONTIGUOUS_TREE_SELECTION);
////        treePanel.(new JTree(root));
//        templateNodeTree.setModel(new DefaultTreeModel(root));

    }

    private void findTemplate() {
        if (StringUtils.isNotEmpty(selectTemplateName)) {
            Template oldTemplate = PluginRuntimeData.getInstance().getTemplate(selectTemplateName);
            if (oldTemplate != null) {
                this.template = (Template) oldTemplate.copy();
                return;
            }
            MessageUtils.showMessageLog(MessageType.ERROR, "模板不存在：{}", selectTemplateName);
        }
        this.template = new Template();
    }

    private void doCreate() {
        TreePath selectionPath = templateNodeTree.getSelectionPath();
        Object[] path = selectionPath.getPath();
        DefaultMutableTreeNode selecting = null;
        if (path.length > 0) {
            for (int i = path.length - 1; i >= 0; i--) {
                selecting = (DefaultMutableTreeNode) path[i];
                if (!selecting.isLeaf() || selecting.isRoot()) {
                    // 没有子节点
                    break;
                }
            }
        }

        if (selecting == null) {
            System.out.println("找不到可用节点");
            return;
        }

        DefaultTreeModel model = (DefaultTreeModel) templateNodeTree.getModel();
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("class");
        model.insertNodeInto(newNode, selecting, 0);
        model.reload();
    }

    private void doEdit() {

    }

    private void doDelete() {
        TreePath selectionPath = templateNodeTree.getSelectionPath();
        if (selectionPath == null) {
            return;
        }

        Object[] path = selectionPath.getPath();
        if (path.length > 0) {
            template.removeNode(getNodeValue((DefaultMutableTreeNode) path[path.length - 1]));
        }

        refreshTreeData();
    }

    private String getNodeValue(DefaultMutableTreeNode node) {
        return (String) node.getUserObject();
    }

    private void initNodeTree() {
        refreshTreeData();
        this.templateNodeTree.getSelectionModel().setSelectionMode(DISCONTIGUOUS_TREE_SELECTION);
    }

    private void refreshTreeData() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(TEMPLATE_ROOT);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        if (this.template != null) {
            addChildNodes((DefaultMutableTreeNode) treeModel.getRoot(), template);
        }

        this.templateNodeTree.setModel(treeModel);
        expandAll();
    }

    private void expandAll() {
        TreeModel model = this.templateNodeTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        expandNode(new TreePath(root));
    }

    private void expandNode(TreePath path) {
        TreeNode node = (TreeNode) path.getLastPathComponent();
        Enumeration<? extends TreeNode> children = node.children();
        while (children.hasMoreElements()) {
            TreeNode treeNode = children.nextElement();
            TreePath treePath = path.pathByAddingChild(treeNode);
            this.templateNodeTree.expandPath(treePath);
            expandNode(treePath);
        }
    }

    private void addChildNodes(DefaultMutableTreeNode root, AbstractTemplateNode template) {
        DefaultMutableTreeNode currentNode;
        List<AbstractTemplateNode> list = new ArrayList<>(template.getChildren());
        // 让包排在前面，否则按照字母顺序排序
        list.sort((n1, n2) -> {
            boolean package1 = n1 instanceof TemplatePackage;
            boolean package2 = n2 instanceof TemplatePackage;

            if (package1 ^ package2) {
                if (package1) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return n1.getName().compareTo(n2.getName());
            }
        });

        for (AbstractTemplateNode child : list) {
            currentNode = new DefaultMutableTreeNode(child.getName());
            root.add(currentNode);
            if (child instanceof TemplatePackage) {
                this.packageName.add(child.getName());
                addChildNodes(currentNode, child);
            }
        }
    }

    public JPanel getRoot() {
        return root;
    }

    public Template getTemplate() {
        return template;
    }

}
