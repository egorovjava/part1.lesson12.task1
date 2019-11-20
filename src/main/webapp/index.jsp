<%@ page import="com.gmail.egorovsonalexey.MyLocalService" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="com.gmail.egorovsonalexey.Tree" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayDeque" %>
<!DOCTYPE html>
<html>
<body>

<%
    String jndiName = "java:global/lesson12task1/myservice!com.gmail.egorovsonalexey.MyLocalService";
    MyLocalService localService;
    try {
        final InitialContext context = new InitialContext();
        localService = (MyLocalService) context.lookup(jndiName);
    } catch (NamingException e) {
        throw new RuntimeException(e);
    }

    Tree<String> fileTree = localService.getFileTree();
    ArrayDeque<List<Tree.Node<String>>> treePath = new ArrayDeque<>();
    treePath.push(fileTree.getRoot().getChildren());
    while (!treePath.isEmpty()) {
        List<Tree.Node<String>> ch = treePath.peek();
        if(ch.size() == 0) {
            treePath.pop();
            if(!treePath.isEmpty()) {
                out.println("</ul>");
                out.println("</details></li>");
            }
            continue;
        }

        Tree.Node<String> n = ch.remove(0);
        if(n.hasChildren()) {
            treePath.push(n.getChildren());
            out.println("<li><details><summary>" + n.getData() + "</summary>");
            out.println("<ul>");
        } else {
            out.println("<li>"+ n.getData() +"</li>");
        }
    }
%>
</body>
</html>
