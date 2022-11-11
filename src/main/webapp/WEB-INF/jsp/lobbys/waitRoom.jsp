<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="waitRoom">
    <jsp:body>
            <h2>Wait Room - ${games.name} - ${games.code}</h2> 
            <h3>${creator.user.username}</h3>
        <table id="gameTable" class="table table-striped">
            <thead>
            <tr>
                <th>Username</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${games.players}" var="player">
                <tr>
                    <td>
                        <c:out value="${player.user.username}" />
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="col text-center">
            <a href="/games/lobby/${games.code}/board" class="btn btn-warning btn-lg active">Start</a>
            <a class="btn btn-default" href="/games/lobby/${games.code}/delete">Exit</a>
        </div>

            
    </jsp:body>

</parchisoca:layout>
