package com.zhangyue.test.nashor.java.lambda;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @desc:
 *
 *  自定义lambda实际使用
 *
 * @author: YanMeng
 * @date: 2019-05-17
 */
public class LambdaTest_5 {

    public void foo(){

        TreeNode tree = new TreeNode();

        // ...

        TreeUtils.forEach(tree, n -> {
            // lkajsdf;kljasdl;fkja;sdf
        }, TreeNode::getNexts);
    }

    @Getter
    @Setter
    class TreeNode {
        String someData;

        List<TreeNode> nexts;
    }
}
