package cool.dustin.model;

import java.util.function.Consumer;

/**
 * 树结构节点
 * @AUTHOR Dustin
 * @DATE 2020/04/14 17:32
 */
public interface TreeNode<T> {
    /**
     * 添加子节点
     * @param node
     */
    void addChild(T node);

    /**
     * 从当前节点开始，到所有子节点，递归执行consumer。
     * @param consumer
     */
    void recursive(Consumer<T> consumer);
}
