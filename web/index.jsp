<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
    </head>
    <body>
        <h1>Inicio de la aplicaicon</h1>
        
            <c:if test="${error != null}">
                <h1>${error}</h1>
            </c:if>     
        <form action="log" method="POST">
        <input type="text" name="user" value="usuario">
        <input type="password" name="password" value="contraseña">
        <input type="submit" value="Enviar">
        <form>
    </body>
</html>
