<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
        <div>
            <div>
                <table >
                    <legend>
                    <thead>
                        <h3>Clientes</h3>
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
                            <th>
                                Acciones
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
                <hr>
                <form method="POST" action="/Robo/anonimo" >
                    <input type="submit">Volver al inico</a>
                </form>
            </div>
        </div>
    </body>
</html>