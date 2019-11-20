package com.gmail.egorovsonalexey;

import javax.ejb.Stateless;
import java.io.File;
import java.util.HashMap;

@Stateless(name="myservice")
public class MyService implements MyLocalService {

    //final private static String rootDir = System.getProperty("user.home");
    final private static String rootDir = "..";

    @Override
    public Tree<String> getFileTree() {
        return getFileTree(rootDir);
    }

    Tree<String> getFileTree(String root) {
        File rootFile = new File(root);
        Tree<String> tree = new Tree<>(rootFile.getName());
        HashMap<File, Tree.Node<String>> currentLevel = new HashMap<>();
        currentLevel.put(rootFile, tree.getRoot());
        HashMap<File, Tree.Node<String>> nextLevel = new HashMap<>();

        while(currentLevel.size() > 0) {
            for (HashMap.Entry<File, Tree.Node<String>> e : currentLevel.entrySet()) {
                File[] chFiles = e.getKey().listFiles();
                if(chFiles == null) {
                    continue;
                }
                for (File chFile : chFiles) {
                    Tree.Node<String> n = e.getValue().addChild(chFile.getName());
                    nextLevel.put(chFile, n);
                }
            }
            currentLevel = nextLevel;
            nextLevel = new HashMap<>();
        }
        return tree;
    }
}
