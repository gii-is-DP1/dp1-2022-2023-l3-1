<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen" />

<parchisoca:layout pageName="games">
    <h2>Games In Progress</h2>
    
    <table id="gamesTable" class="table" style = "background:white;">
        <thead>
        <tr>
            <th>Game</th>
            <th>Name</th>
            <th>Creator</th>
            <th>Players</th>
            <th>Privacity</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td>
                    <c:out value="${game.gameType.name}"/>
                </td>
                <td>
                    <c:out value="${game.name}"/>
                </td>
                <td>                    
                    <c:out value="${game.creator.user.username}"/>                                        
                </td>
                <td>                    
                    <c:forEach var="item" items="${game.players}" >
                        ${item.user.username}
                        </br>
                    </c:forEach>                              
                </td>
                <td>                    
                    <c:out value="${game.privacity}"/>                               
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p> &nbsp </p>
        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
        </div>
</parchisoca:layout>