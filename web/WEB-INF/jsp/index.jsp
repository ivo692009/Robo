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
                                <td>
                                    <div>
                                        <a type="button" href="/Robo/modificar?id=${cliente.id}"> Modificar</a>
                                        <a type="button" href="/Robo/baja?id=${cliente.id}"> Eliminar </a>
                                    </div>                                    
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </legend>
                </table>
                <hr>
                <a type="button" href="/Robo/alta">Nuevo cliente</a>
            </div>
        </div>
    </body>
</html>
