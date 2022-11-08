<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="games">
    <h2>Public Games</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Game</th>
            <th>Creator</th>
            <th>Players</th>
            <th>Join</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td>
                    <c:out value="${game.gameType.name}"/>
                </td>
                <td>                    
                      <c:out value="${game.creator.user.username}"/>                                        
                </td>
                <td>                    
                      <c:out value="${game.numberOfPlayers}"/>                               
                </td>
                
                <td>       
                    <a class="btn btn-default" href=''>Join</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p> &nbsp </p>
        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Volver</a>
        </div>

</petclinic:layout>