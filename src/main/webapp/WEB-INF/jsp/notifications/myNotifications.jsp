<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen" />

<parchisoca:layout pageName="notifications">
    <h2 style = "color:white;">Notifications</h2>

    <table  id="notificationsTable" class="table" style = "background:white;">
        <thead>
            <tr>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="n" items="${notifications}">
                <tr>
                    <td>
                        <c:out value="${n.text}" />
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${n.friendRequest == false && n.invitation == false}">
                                <p>None</p>
                            </c:when>
                            <c:when test="${n.friendRequest == true}">
                                <a class="btn btn-default" href='<spring:url value="/players/${n.sender}/add" htmlEscape="true"/>'>Accept</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-default" href='<spring:url value="/games/lobby/${n.gameCode}" htmlEscape="true"/>'>Accept</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a class="glyphicon glyphicon-trash" href="/notifications/${n.id}/delete"></a>                         
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <div class="col text-center">
        <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
    </div>

</parchisoca:layout>
<style>
    #notificationsTable{
        color:black;
    }
</style>