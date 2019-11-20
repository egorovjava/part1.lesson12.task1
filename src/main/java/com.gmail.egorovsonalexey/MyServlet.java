package com.gmail.egorovsonalexey;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.List;

public class MyServlet extends HttpServlet {

    private static final String jndiName = "java:global/lesson12task1/myservice!com.gmail.egorovsonalexey.MyLocalService";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        MyLocalService localService;
        try {
            final InitialContext context = new InitialContext();
            localService = (MyLocalService) context.lookup(jndiName);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");

        Tree<String> fileTree = localService.getFileTree();

        ArrayDeque<List<Tree.Node<String>>> treePath = new ArrayDeque<>();
        treePath.push(fileTree.getRoot().getChildren());
        while (treePath.size() != 0) {
            List<Tree.Node<String>> ch = treePath.peek();
            if(ch.size() == 0) {
                treePath.pop();
                writer.println("</ul>");
                writer.println("</details></li>");
                continue;
            }

            Tree.Node<String> n = ch.remove(0);
            if(n.hasChildren()) {
                treePath.push(n.getChildren());
                writer.println("<li><details><summary>" + n.getData() + "</summary>");
                writer.println("<ul>");
            } else {
                writer.println("<li>"+ n.getData() +"</li>");
            }
        }
        writer.println("</html>");
    }
}
