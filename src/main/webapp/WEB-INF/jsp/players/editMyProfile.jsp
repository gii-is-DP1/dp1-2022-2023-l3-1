<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="players">
    <jsp:body>
        <h2>Editar Perfil</h2>
            <form:form modelAttribute="player" class="form-horizontal">
                        <div class="form-group has-feedback">
                            <petclinic:inputField label="FirstName" name="player.firstName"/>
                            <petclinic:inputField label="LastName" name="player.lastName"/>
                            <petclinic:inputField label="Username" name="player.user.username"/>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button class="btn btn-default" type="submit">Guardar perfil</button>
                            </div>
                        </div>
            </form:form>
    </jsp:body>
</petclinic:layout>
