<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="games">
    <h2>You are the winner of the game, CONGRATULATIONS ${game.winner.user.username}! </h2>
    
    <spring:url value="/resources/images/winner.gif" var="winner"/>

    <p> &nbsp </p>
    <div class="col text-center"> 
        <a href = "/games/lobbys">
            <img src="${winner}" width="204"/>
        </a>
    </div>

    <p> &nbsp </p>
        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
        </div>
</parchisoca:layout>