<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <body>
        <div>
            <legend>Nuevo cliente</legend>
            <c:if test="${error != null}">
                <h1>${error}</h1>
            </c:if> 
            <form method="POST" action="/Robo/alta">
                <div>
                    <label><b>Nombre:</b></label>
                    <div>
                        <input type="text" id="nombre" name="nombre" required autofocus>
                    </div>
                </div>
                <div >
                    <label><b>Apellido:</b></label>
                    <div >
                        <input type="text" id="apellido" name="apellido" required autofocus>
                    </div>
                </div>
                <div>
                    <label><b>Fecha de nacimiento:</b></label>
                    <div>
                        <input type="date" id="fechnac" name="fechnac" required autofocus>
                    </div>
                </div>
                <div>
                    <label>Nacionalidad:</label>
                    <div>
                        <select id="nacionalidad" name="nacionalidad" required>
                            <c:forEach var="pais" items="${nacionalidad}">
                                <option value="${pais.id}">${pais.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div>
                    <label><b>Activo:</b></label>
                    <div>
                        <input type="radio" id="activo" name="activo" value="1" checked> Si<br>
                        <input type="radio" id="activo" name="activo" value="0"> No<br>
                    </div>
                </div>
                <div>
                    <div>
                        <a class="button" href="/Robo/inicio">Volver al listado</a>
                        <button type="submit">Guardar</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>