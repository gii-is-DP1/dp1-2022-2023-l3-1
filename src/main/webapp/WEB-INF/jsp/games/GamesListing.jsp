<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="games">
    <h2>Games</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Creador</th>
            <th>Ganador</th>
            <th>Juego</th>
            <th>Jugadores</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td>
                    <c:out value="${game.creator}"/>
                </td>
                <td>                    
                      <c:out value="${game.winner} "/>                                        
                </td>
                
                <td>       
                    <c:out value="${game.gameType.name} "/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${game.players.isEmpty()}">
                            <p>None</p>
                            
                        </c:when>
                        <c:otherwise>
                            ${game.players}
                            <br/>
                        </c:otherwise>
                    </c:choose>
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