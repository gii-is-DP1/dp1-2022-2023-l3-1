<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<parchisoca:layout pageName="players">
    <div class="row"> 
        <div class = "col-sm-6">
            <h2>Player Information </h2>
        </div>
        <sec:authorize access="hasAuthority('admin')">
            <div class = "col-sm-6" style="text-align: right;" >
                <a href="/admin/${player.id}/edit"> 
                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>                            
                </a> 
                <a href="/admin/${player.id}/delete"> 
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>                            
                </a> 
            </div>
        </sec:authorize>
        <sec:authorize access="hasAuthority('player')">
            <div class = "col-sm-6" style="text-align: right;" >
                <a href="/players/${player.id}/sendFriendRequest"> 
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>                            
                </a> 
            </div>
        </sec:authorize>
        
    </div>

    <table class="table">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${player.firstName} ${player.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Username</th>
            <td><b><c:out value="${player.user.username}"/></b></td>
        </tr>
        <tr>
            <th>Achievements</th>
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
    </table>

    <div class="col text-center">
        <a class="btn btn-default" href='<spring:url value="/players/find" htmlEscape="true"/>'>Go Back</a>
    </div>

</parchisoca:layout>
