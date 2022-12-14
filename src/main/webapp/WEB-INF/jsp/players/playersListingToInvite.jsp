<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="players">
    <c:choose>
        <c:when test="${players.size() > 0 }">
        <h2>Friends</h2>
        <table id="playersTable" class="table">
        <thead>
            <tr>
                <th>Username</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player">
            <tr>
                <td>
                    <div class = "col-sm-6" style="text-align: left;" >
                        <c:out value="${player.user.username}"/>
                    </div> 
                    <div class = "col-sm-6" style="text-align: right;" >
                        <a class="glyphicon glyphicon-plus" href='<spring:url value="/players/${player.id}/sendGameInvitation/${code}" htmlEscape="true"/>'></a>                         
                    </div> 
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
        </c:when>
        <c:otherwise>
            <tr align="center">
                <td colspan="5">No friends available</td>
            </tr>
        </c:otherwise>
        </c:choose>
    <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/games/lobby/${code}/waitRoom" htmlEscape="true"/>'>Go Back</a>
        </div>
    
</parchisoca:layout>
