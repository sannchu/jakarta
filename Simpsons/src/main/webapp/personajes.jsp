<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Personajes de Springfield</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<h1>Personajes de Springfield</h1>

<form action="${pageContext.request.contextPath}/personajes" method="get">
    <fieldset>
        <legend>Filtrar</legend>
        <label>Lugar
            <select name="lugar">
                <option value="">— Todos —</option>
                <c:forEach var="lugar" items="${lugares}">
                    <option value="${lugar}" ${lugar == param.lugar ? 'selected' : ''}>
                        <c:out value="${lugar}" />
                    </option>
                </c:forEach>
            </select>
        </label>
        <label>Edad máxima
            <input type="number" name="edadMax" min="0" value="${edadMax}">
        </label>
    </fieldset>

    <fieldset>
        <legend>Ordenar</legend>
        <label>Ordenar por
            <select name="ordenarPor">
                <option value="nombre" ${ordenarPor == 'nombre' ? 'selected' : ''}>Nombre</option>
                <option value="apellido" ${ordenarPor == 'apellido' ? 'selected' : ''}>Apellido</option>
                <option value="edad" ${ordenarPor == 'edad' ? 'selected' : ''}>Edad</option>
            </select>
        </label>
        <label class="check">
            <input type="checkbox" name="descendente" ${descendente ? 'checked' : ''}>
            Descendente
        </label>
        <label>Mostrar como máximo
            <input type="number" name="limite" min="0" value="${limite}">
        </label>
    </fieldset>

    <button type="submit">Buscar</button>
    <a href="${pageContext.request.contextPath}/personajes">Limpiar filtros</a>
</form>

<p class="resumen"><strong><c:out value="${total}" /></strong> personajes encontrados</p>

<c:choose>
    <c:when test="${empty personajes}">
        <p class="vacio">No hay personajes que coincidan con esos filtros.</p>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>Nombre</th>
                <th>Edad</th>
                <th>Ocupación</th>
                <th>Lugar</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="personaje" items="${personajes}">
                <tr class="${personaje.menor ? 'menor' : ''}">
                    <td><c:out value="${personaje.nombreCompleto}" /></td>
                    <td><c:out value="${personaje.edad}" /></td>
                    <td><c:out value="${personaje.ocupacion}" /></td>
                    <td><c:out value="${personaje.lugar}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>
