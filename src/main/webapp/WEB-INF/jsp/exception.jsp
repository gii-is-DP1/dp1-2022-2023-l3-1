<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="error">

    <spring:url value="/resources/images/error.gif" var="errorImage"/>
    <div class="col text-center"> 
        <h2> Something was wrong... </h2>
        <img src="${errorImage}" width="204"/>
        <p> &nbsp </p>
        <a class="btn btn-default" href='/'>Exit</a>
    </div>
    <p>${exception.message}</p>

</parchisoca:layout>
