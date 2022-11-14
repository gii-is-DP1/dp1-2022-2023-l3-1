<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="games">
    <h2>Public Games</h2>
        <p>
            Buscar partida privada:
            </br>
            <input placeholder ="Inserte un código" type="text" name="code" id="code"/>
            <input type="button" value="Search" onclick="privateGame()" />
        </p>
        </br>
    
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Game</th>
            <th>Name</th>
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
                    <c:out value="${game.name}"/>
                </td>
                <td>                    
                      <c:out value="${game.creator.user.username}"/>                                        
                </td>
                <td>                    
                      <c:out value="${game.numberOfPlayers}"/>                               
                </td>
                
                <td>       
                    <a class="btn btn-default" href='/games/lobby/${game.code}'>Join</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p> &nbsp </p>
        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
        </div>



    <script>
        function privateGame(){
            window.location="/games/lobby/"+document.getElementById("code").value;
        }
    </script>
</parchisoca:layout>