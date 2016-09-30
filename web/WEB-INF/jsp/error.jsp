<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${error != null}">
            <h1>${error}</h1>
        </c:if> 
        <a type="button" href="/Robo/inicio"> Volver al inicio</a>
    </body>
</html>
