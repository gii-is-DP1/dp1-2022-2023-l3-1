<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<parchisoca:layout pageName="home">
    <body>
    <div class = "button-info">
        <sec:authorize access="!authenticated">
            <a href = "/games/instructions/parchisInstructions">
                <img class ="info" src = "/resources/images/boton-de-informacion.png">
            </a>
        </sec:authorize>
    </div>
    <div class= "div1" style="text-align: center; margin-top: 100px " > 
        <a href="/games/create">
            <spring:url value="/resources/images/fondo-oca-web-grande.png" var="logo"/>
            <img src="${logo}" width="600"/>
        </a>
    </div>
    <div style = "text-align: center;">
        <sec:authorize access="!authenticated">
            <a href = "/players/create">
                <button class = "button" type="button">Register</button>
            </a>
        </sec:authorize>    
        </br>
        <sec:authorize access="!authenticated">
            <a href = "/login">
                <button class = "button" type="button">Log in</button>
            </a>
        </sec:authorize>    
    </div>
    <body>
</parchisoca:layout>

<style>

    .button-info{
        text-align:right;
        position:relative;
        top:120px; right:280px;
    }
    .info{
       width: 70px;
    }
</style>
