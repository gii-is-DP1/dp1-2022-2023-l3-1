<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="home">
    <div class="row">
        <div class="col text-center">
            <a href="/games/create">
                <spring:url value="/resources/images/fondo-oca-web.png" htmlEscape="true" var="logoImage"/>
                <img class="img-responsive" src="${logoImage}"/>
            </a>
        </div>
    </div>
</parchisoca:layout>
