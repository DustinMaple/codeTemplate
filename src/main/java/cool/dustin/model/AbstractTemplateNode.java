package cool.dustin.model;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.sun.istack.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 模板结构的节点
 * @AUTHOR Dustin
 * @DATE 2020/04/13 20:50
 */
public abstract class AbstractTemplateNode implements TreeNode<AbstractTemplateNode>, TemplateElement, Copyable<AbstractTemplateNode> {
    /**
     * 元素名称
     */
    private String name;
    /**
     * 所有子节点
     */
    private Set<AbstractTemplateNode> children = new HashSet<>();

    @Override
    public void addChild(AbstractTemplateNode node) {
        children.add(node);
    }

    @Override
    public void recursive(Consumer<AbstractTemplateNode> consumer) {
        consumer.accept(this);
        children.forEach(node -> node.recursive(consumer));
    }

    @Override
    public final void generatePsi(Project project, PluginContext context, @NotNull PsiElement parentElement) {
        // 自己在父元素下生成自身的psiElement
        PsiElement psiElement = createSelfPsiElement(project, context, parentElement);

        // 遍历所有子节点，将自己作为父节点，让子节点创建psiElement
        children.forEach(child -> child.generatePsi(project, context, psiElement));

    }

    /**
     * 创建自身的psi元素
     * @param project 所处项目
     * @param context 插件上下文
     * @param parentElement 父级psi元素
     * @return
     */
    protected abstract PsiElement createSelfPsiElement(Project project, PluginContext context, PsiElement parentElement);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractTemplateNode that = (AbstractTemplateNode) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AbstractTemplateNode> getChildren() {
        return children;
    }

    public void setChildren(Set<AbstractTemplateNode> children) {
        this.children = children;
    }

    /**
     * 深度遍历移除目标节点
     * @param nodeName
     * @return
     */
    public AbstractTemplateNode removeNode(String nodeName) {
        AbstractTemplateNode removed = null;
        Iterator<AbstractTemplateNode> iterator = children.iterator();
        while (iterator.hasNext()) {
            AbstractTemplateNode next = iterator.next();
            if (next.getName().equals(nodeName)) {
                iterator.remove();
                removed = next;
                break;
            }

            removed = next.removeNode(nodeName);
            if (removed != null) {
                break;
            }
        }

        return removed;
    }
}
