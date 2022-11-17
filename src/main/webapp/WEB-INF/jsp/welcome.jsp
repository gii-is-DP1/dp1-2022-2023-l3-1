<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="home">
    <div style="text-align: center; margin-top: 100px"> 
        <a href="/games/create">
            <spring:url value="/resources/images/fondo-oca-web.png" var="logo"/>
            <img src="${logo}" width="600"/>
        </a>
    </div>
</parchisoca:layout>
