package cool.dustin.ui.forms;

import cool.dustin.constant.MessageType;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.AbstractTemplateNode;
import cool.dustin.model.Template;
import cool.dustin.model.TemplateClass;
import cool.dustin.model.TemplatePackage;
import cool.dustin.ui.EditClassDialog;
import cool.dustin.ui.EditPackageDialog;
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
    //---------------------- 界面 -----------------------
    private JPanel root;
    private JTextField templateNameField;
    private JButton addPackageButton;
    private JButton addClassButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTree templateNodeTree;
    private JTextField descriptionField;
    //---------------------- 界面 -----------------------
    /**
     * 模板的临时数据
     */
    private Template tempTemplate;
    private final EditTemplateDialog editTemplateDialog;
    private final String selectTemplateName;
    private Map<Integer, AbstractTemplateNode> nameToNodeMap = new HashMap<>();

    public EditTemplateForm(EditTemplateDialog editTemplateDialog, String selectTemplateName) {
        this.editTemplateDialog = editTemplateDialog;
        this.selectTemplateName = selectTemplateName;
        init();
    }

    public void refreshTreeData() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(TreeNodeData.ROOT);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        if (this.tempTemplate != null) {
            addChildNodes((DefaultMutableTreeNode) treeModel.getRoot(), tempTemplate);
        }

        this.templateNodeTree.setModel(treeModel);
        expandAll();
    }

    private void init() {
        findTemplate();
        this.templateNameField.setText(this.tempTemplate.getName());
        this.descriptionField.setText(this.tempTemplate.getDescription());

        initNodeTree();
        addPackageButton.addActionListener(e -> doAddPackage());
        addClassButton.addActionListener(e -> doAddClass());
        editButton.addActionListener(e -> doEdit());
        deleteButton.addActionListener(e -> doDelete());
    }

    private void doAddPackage() {
        DefaultMutableTreeNode selectNode = getSelectTreeNode();
        if (selectNode == null) {
            MessageUtils.showMessageLog(MessageType.INFO, "请先选中节点");
            return;
        }

        AbstractTemplateNode parentTemplateNode = getParentTemplateNode(selectNode);
        if (parentTemplateNode == null) {
            MessageUtils.showMessageLog(MessageType.ERROR, "选中节点：{}，找不到可用的父节点", selectNode.getUserObject());
            return;
        }
        new EditPackageDialog(editTemplateDialog, null, parentTemplateNode).showAndGet();
    }

    private void doAddClass() {
        DefaultMutableTreeNode selectNode = getSelectTreeNode();
        if (selectNode == null) {
            MessageUtils.showMessageLog(MessageType.INFO, "请先选中节点");
            return;
        }

        AbstractTemplateNode parentTemplateNode = getParentTemplateNode(selectNode);
        if (parentTemplateNode == null) {
            MessageUtils.showMessageLog(MessageType.ERROR, "选中节点：{}，找不到可用的父节点", selectNode.getUserObject());
            return;
        }

        new EditClassDialog(editTemplateDialog, null, parentTemplateNode, this.tempTemplate).showAndGet();
    }

    private void doEdit() {
        DefaultMutableTreeNode selectNode = getSelectTreeNode();
        if (selectNode == null) {
            MessageUtils.showMessageLog(MessageType.INFO, "请先选中节点");
            return;
        }

        // 根节点不可以编辑
        TreeNodeData nodeData = (TreeNodeData) selectNode.getUserObject();
        if (nodeData.getName().equals(TEMPLATE_ROOT)) {
            return;
        }

        AbstractTemplateNode parentTemplateNode = getParentTemplateNode(selectNode);
        if (parentTemplateNode == null) {
            MessageUtils.showMessageLog(MessageType.ERROR, "选中节点：{}，找不到可用的父节点", selectNode.getUserObject());
            return;
        }

        AbstractTemplateNode templateNode = nameToNodeMap.get(nodeData.getId());
        if (templateNode instanceof TemplatePackage) {
            new EditPackageDialog(editTemplateDialog, (TemplatePackage) templateNode, parentTemplateNode).showAndGet();
        } else if (templateNode instanceof TemplateClass) {
            new EditClassDialog(editTemplateDialog, (TemplateClass) templateNode, parentTemplateNode, this.tempTemplate).showAndGet();
        } else {
            MessageUtils.showMessageLog(MessageType.ERROR, "不识别的类型：{}", templateNode.getClass());
        }
    }

    private void doDelete() {
        TreePath selectionPath = templateNodeTree.getSelectionPath();
        if (selectionPath == null) {
            return;
        }

        Object[] path = selectionPath.getPath();
        if (path.length > 0) {
            TreeNodeData nodeValue = getNodeValue((DefaultMutableTreeNode) path[path.length - 1]);
            tempTemplate.removeNode(nodeValue.getName());
        }

        refreshTreeData();

        this.editTemplateDialog.changed();
    }

    /**
     * 获取可以做父节点的模板节点
     * @param selectTreeNode
     * @return
     */
    private AbstractTemplateNode getParentTemplateNode(DefaultMutableTreeNode selectTreeNode) {
        TreeNodeData selectNodeData = (TreeNodeData) selectTreeNode.getUserObject();
        if (selectNodeData.getName().equals(TEMPLATE_ROOT)) {
            return tempTemplate;
        }

        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectTreeNode.getParent();
        selectNodeData = (TreeNodeData) parent.getUserObject();
        AbstractTemplateNode templateNode = nameToNodeMap.get(selectNodeData.getId());

        if (templateNode instanceof TemplatePackage) {
            return templateNode;
        } else {
            return getParentTemplateNode((DefaultMutableTreeNode) selectTreeNode.getParent());
        }
    }

    private DefaultMutableTreeNode getSelectTreeNode() {
        TreePath selectionPath = this.templateNodeTree.getSelectionPath();
        if (selectionPath == null) {
            return null;
        }
        return (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
    }

    private void findTemplate() {
        if (StringUtils.isNotEmpty(selectTemplateName)) {
            Template oldTemplate = PluginRuntimeData.getInstance().getTemplate(selectTemplateName);
            if (oldTemplate != null) {
                this.tempTemplate = (Template) oldTemplate.copy();
                return;
            }
            MessageUtils.showMessageLog(MessageType.ERROR, "模板不存在：{}", selectTemplateName);
        }
        this.tempTemplate = new Template();
    }

    private TreeNodeData getNodeValue(DefaultMutableTreeNode node) {
        return (TreeNodeData) node.getUserObject();
    }

    private void initNodeTree() {
        refreshTreeData();
        this.templateNodeTree.getSelectionModel().setSelectionMode(DISCONTIGUOUS_TREE_SELECTION);
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
            currentNode = new DefaultMutableTreeNode(new TreeNodeData(child.getId(), child.getName()));
            root.add(currentNode);
            nameToNodeMap.put(child.getId(), child);
            if (child instanceof TemplatePackage) {
                addChildNodes(currentNode, child);
            }
        }
    }

    public JPanel getRoot() {
        return root;
    }

    public Template getTempTemplate() {
        tempTemplate.setName(templateNameField.getText());
        tempTemplate.setDescription(descriptionField.getText());
        return tempTemplate;
    }

    static class TreeNodeData {
        private static TreeNodeData ROOT = new TreeNodeData(-1, TEMPLATE_ROOT);

        private int id;
        private String name;

        public TreeNodeData(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
