<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="players">
    <h2>Players</h2>

    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
            <th>firstName</th>
            <th>lastName</th>
            <th>username</th>
            <th>achievements</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player">
            <tr>
                <td>
                    <spring:url value="{playerId}" var="playerUrl">
                        <spring:param name="playerId" value="${player.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(playerUrl)}"><c:out value="${player.firstName}"/>
                </td>
                <td>
                    <c:out value="${player.lastName}"/>
                </td>
                <td>
                    <c:out value="${player.user.username}" />
                </td>
                <td> 
                    <c:choose>
                        <c:when test="${player.achievements.isEmpty()}">
                            <p>None</p>
                            
                        </c:when>
                        <c:otherwise>
                            ${player.achievements}
                            <br/>
                        </c:otherwise>
                    </c:choose>   
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
