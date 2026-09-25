<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Formulario de alta</title>
<style>
  body { font-family: 'Segoe UI', Arial, sans-serif; background: #F7F5F0; color: #0E2438; }
  .form-card { max-width: 460px; margin: 60px auto; background: #fff; padding: 32px 36px;
               border-radius: 10px; box-shadow: 0 8px 30px rgba(0,0,0,.08); }
  label { display: block; font-weight: bold; margin: 16px 0 6px; }
  input, select { width: 100%; padding: 9px; border: 1px solid #ccc; border-radius: 6px;
                  box-sizing: border-box; font-size: 1rem; }
  button { margin-top: 24px; background: #E8432A; color: #fff; border: none; padding: 12px 28px;
           border-radius: 8px; font-weight: bold; cursor: pointer; }
  button:hover { background: #c93a22; }
  .error-msg { background: #FDEDEA; color: #E8432A; border: 1px solid #E8432A;
               border-radius: 6px; padding: 10px 14px; margin-bottom: 16px; font-weight: bold; }
</style>
</head>
<body>
  <div class="form-card">
    <h1>Formulario de alta</h1>


    <!-- Si el nombre está vacío, se muestra un mensaje de error. -->
    <% if (request.getAttribute("mensaje") != null) { %>
      <div class="error-msg"><c:out value="${mensaje}"/></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/alta" method="post">

      <label for="nombre">Nombre</label>
      <!--<input type="text" id="nombre" name="nombre" required>-->
      <input type="text" id="nombre" name="nombre" value="${fn:escapeXml(nombre)}">

      <label for="email">Email</label>
      <input type="email" id="email" name="email" value="${fn:escapeXml(email)}" required>

      <label for="tecnologia">Tecnología con la que más te gustaría trabajar</label>
      <select id="tecnologia" name="tecnologia">
<%--      <%--%>
<%--        List<String> tecnologias = (List<String>) request.getAttribute("tecnologias");--%>
<%--        String tecnologiaSeleccionada = (String) request.getAttribute("tecnologia");--%>
<%--        for (String t : tecnologias) {--%>
<%--      %>--%>
<%--      <option value="<%= t %>" <%= t.equals(tecnologiaSeleccionada) ? "selected" : "" %>><%= t %></option>--%>
<%--      <%--%>
<%--        }--%>
<%--      %>--%>
        <c:forEach var="t" items="${tecnologias}">
          <option value="${fn:escapeXml(t)}" <c:if test="${t eq tecnologia}">selected</c:if>>
            <c:out value="${t}"/>
          </option>
        </c:forEach>
      </select>

      <label for="nivel">Tu nivel actual</label>
      <select id="nivel" name="nivel">
        <c:forEach var="n" items="${niveles}">
          <option value="${fn:escapeXml(n)}" <c:if test="${n eq nivel or (empty nivel and n eq niveles[0])}">selected</c:if>>
            <c:out value="${n}"/>
          </option>
        </c:forEach>
      </select>

      <button type="submit">Enviar</button>
    </form>
  </div>
</body>
</html>
