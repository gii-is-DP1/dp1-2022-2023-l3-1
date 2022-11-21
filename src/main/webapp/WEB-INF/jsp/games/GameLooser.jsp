<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen"/>

<parchisoca:layout pageName="games">
    <h2 style  = "text-align:center">Sorry, you lost the game </h2>
    </br>
    <div class = "row" >
    <div class = "col-md-12" style = "text-align:center">
    <img id = "looser" src = "/resources/images/looser.gif" >
    </div>
    </div>
    </br>
    <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
    </div>
</parchisoca:layout>