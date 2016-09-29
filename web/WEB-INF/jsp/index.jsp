<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="layout.jsp"></jsp:include>
<body>
        <div>
            <div>
                <table >
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
                                    ${cliente.fecha_nac}
                                </td>
                                <td>
                                    <div>
                                        <a type="button" href="/Robo/editar?id=${cliente.id}"> Editar</a>
                                        <a type="button">Eliminar</a>
                                    </div>                                    
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <hr>
                <a type="button" href="/Robo/nuevo">Nuevo cliente</a>
            </div>
        </div>
    </body>
</html>
