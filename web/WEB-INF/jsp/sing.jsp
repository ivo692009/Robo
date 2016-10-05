<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrarse</title>
    </head>

    <body>

        <h1>Registrar nuevo Usuario</h1>
        <hr/>

        <form name="formulario" action="/Robo/sing" method="POST" >

            <label>Nombre Usuario:</label><br>
            <input name="username" type="text" id="username" required>
            <br><br>

            <label>Password:</label><br>
            <input name="password1" type="password" id="password1" required>
            <br><br>
            <label>Confirmar Password:</label><br>
            <input name="password2" type="password" id="password2" required>
            <br><br>
            <label>Tipo de Permiso:</label><br>
            <label>Alta = 1</label><br>
            <label>Baja = 2</label><br>
            <label>Modificacion = 3</label><br>
            <input name="permiso" type="number" id="permiso" required>
            <br><br>

            <input type="submit" name="Submit" value="Registrar">

        </form>
        <label>Volver al Inicio</label><br>
        <a href="index.php">Inicio</a><br><br>
        <hr/>