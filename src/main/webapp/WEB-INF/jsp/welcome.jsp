<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
        <div class="col text-center">
            <spring:url value="/resources/images/fondo-oca-web.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
        <div class="col text-center">
            <a class="btn btn-warning btn-lg active" href='/games/instructions'>Como Jugar</a>
        </div>
        <p>&nbsp</p>

        <div class="col text-center">
            <a href="/games/create" class="btn btn-warning btn-lg active">Start</a>
            <!-- <a class="btn btn-default" href='<spring:url value="/games/create" htmlEscape="true"/>'>Start</a> -->
        </div>
    </div>
</petclinic:layout>
