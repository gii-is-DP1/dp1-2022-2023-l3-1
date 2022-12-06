<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen" />

<parchisoca:layout pageName="playersFriends">
    <h2 style= "color:white;">Friends</h2>

    <table id="friendsTable" class="table" style ="background: white;">
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Username</th>
                <th>Achievements</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="f" items="${friends}">
                <tr>
                    <td>
                        <spring:url value="{playerId}/viewFriend" var="playerUrl">
                            <spring:param name="playerId" value="${f.id}"/>
                        </spring:url>
                        <a href="${fn:escapeXml(playerUrl)}">
                        <c:out value="${f.firstName}"/>   
                    <td>
                        <c:out value="${f.lastName}" />
                    </td>
                    <td>
                        <c:out value="${f.user.username}" />
                    </td>
                    <td>
                        <c:out value="${f.achievements}" />
                    </td>
                    <td>
                        <a class="btn btn-default" href='<spring:url value="/stats/${f.id}" htmlEscape="true"/>'>Stats</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <div class="col text-center">
        <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
        <a class="btn btn-default" href='<spring:url value="/players/find" htmlEscape="true"/>'>Find player to add</a>
    </div>

</parchisoca:layout>
