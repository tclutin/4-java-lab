<%@ page import="java.io.File" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>File Manager</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <form method="POST">
        <button type="submit" class="btn btn-primary">Logout</button>
    </form>
    <h2>Файловый менеджер</h2>
    <p>Время генерации: <%= request.getAttribute("timestamp") %></p>
    <hr>
    <h3>Текущая директория: <%= request.getAttribute("basePath") %></h3>
    <ul>
        <%
            String path = ((String)request.getAttribute("basePath"));
            String parentPath = new File(path).getParent();
            if (parentPath == null) {
                parentPath = "/";
            }

        %>
        <li><a href="files?path=<%= URLEncoder.encode(parentPath, "UTF-8") %>">Вернуться</a></li>
        <%
            File[] files = (File[]) request.getAttribute("files");
            if (files != null) {
                for (File file : files) {
                    String encodePath = URLEncoder.encode(file.getAbsolutePath(), "UTF-8");
                    if (file.isDirectory()) {
        %>
                <li>[D] <a href="files?path=<%= encodePath %>"><%= file.getName() %></a></li>
        <%
                    } else {
        %>
                <li>[F] <a href="download?path=<%= encodePath %>"><%= file.getName() %></a></li>
        <%
                    }
                }
            }
        %>
    </ul>
</body>
</html>
