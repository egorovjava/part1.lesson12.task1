package com.gmail.egorovsonalexey;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MyServiceTest {

    @Test
    void getFileNamesTest() throws IOException {
        String home = System.getProperty("user.home");
        ArrayList<String> dirNames = new ArrayList<>();
        dirNames.add("\\test");
        dirNames.add("\\test\\test1");
        dirNames.add("\\test\\test2");

        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add("\\test\\testFile.txt");
        fileNames.add("\\test\\test1\\testFile11.txt");
        fileNames.add("\\test\\test1\\testFile12.txt");
        fileNames.add("\\test\\test2\\testFile21.txt");
        fileNames.add("\\test\\test2\\testFile22.txt");

        try {
            ArrayList<File> directories  = new ArrayList<>();
            for (String dir : dirNames) {
                File d = new File(home + dir);
                directories.add(d);
                assertTrue(d.mkdir());
            }
            ArrayList<File> files = new ArrayList<>();
            for (String file : fileNames) {
                File f = new File(home + file);
                files.add(f);
                assertTrue(f.createNewFile());
            }

            MyService myService = new MyService();
            Tree<String> fileTree = myService.getFileTree(home + dirNames.get(0));
            Tree.Node<String> root = fileTree.getRoot();
            assertEquals(root.getData(), directories.get(0).getName());
            List<String> rootChildren =
                    root.getChildren().stream().map(Tree.Node::getData).collect(Collectors.toList());
            assertEquals(rootChildren.size(), 3);
            assertTrue(rootChildren.contains(directories.get(1).getName()));
            assertTrue(rootChildren.contains(directories.get(2).getName()));
            assertTrue(rootChildren.contains(files.get(0).getName()));
            List<Tree.Node<String>> children = root.getChildren().get(0).getChildren();
            assertEquals(children.size(), 2);
            assertEquals(children.get(0).getData(), files.get(1).getName());
            assertEquals(children.get(1).getData(), files.get(2).getName());
            children = root.getChildren().get(1).getChildren();
            assertEquals(children.size(), 2);
            assertEquals(children.get(0).getData(), files.get(3).getName());
            assertEquals(children.get(1).getData(), files.get(4).getName());
        } finally {

            for (String file : fileNames) {
                File f = new File(home + file);
                assertTrue(f.delete());
            }

            for (int i = dirNames.size() - 1; i >= 0; i--) {
                assertTrue(Files.deleteIfExists(Paths.get(home, dirNames.get(i))));
            }
        }


    }

}
