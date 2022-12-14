<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="players">
    <c:choose>
        <c:when test="${data.size() > 0 }">
        <h2>Players</h2>
        <table id="playersTable" class="table">
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Username</th>
                <th>Achievements</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${data}" var="player">
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
        </c:when>
        <c:otherwise>
            <tr align="center">
                <td colspan="5">No Users available</td>
            </tr>
        </c:otherwise>
        </c:choose>
        <div class="col text-center">
            <c:if test="${data.size() > 0 }">
                <ul class="pagination">
                    <c:forEach begin="1" end="${totalPages}" var="page">
                        <li class="page-item">
                            <a class="btn btn-default" href="list?page=${page}&size=${size}" class="page-link">${page}</a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Go Back</a>
        </div>
    
</parchisoca:layout>
