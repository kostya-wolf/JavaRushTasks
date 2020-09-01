package com.javarush.task.task36.task3604;

/* 
Разбираемся в красно-черном дереве
*/
public class Solution {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(13);
        tree.insert(8);
        tree.insert(17);
        tree.insert(9);
        tree.insert(11);
        System.out.println(tree);
    }
}
