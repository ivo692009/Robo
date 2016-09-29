<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Baja</title>
    </head>
    <body>
        <form method="POST" action="/Robo/baja">
            <input type="hidden" name="id" id="id" value="${cliente_id}">
            <h3>¿Seguro que desea eliminar este usuario?</h3>
            <table >
                    <legend>
                    <thead>
                        <h3>Cliente</h3>
                    </thead>
                    <tbody>
                        <tr>
                            <th>
                                Nombre
                            </th>
                            <th>
                                Apellido
                            </th>
                            <th>
                                Edad
                            </th>
                        </tr>
                        <c:forEach var="cliente" items="${resultado}">
                            <tr>
                                <td>
                                    ${cliente.nombre}
                                </td>
                                <td>
                                    ${cliente.apellido}
                                </td>
                                <td>
                                    ${cliente.fechnac}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </legend>
                </table>
            <div>
                <a class="button" href="/Robo/inicio">Volver al listado</a>
                <button type="submit">Borrarrrrrrr</button>
            </div>      
        </form>
    </body>
</html>