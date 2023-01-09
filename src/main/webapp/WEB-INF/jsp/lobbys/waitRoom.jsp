<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen" />


<parchisoca:layoutWithoutMenu pageName="waitRoom">
    <body>
    <div class = "container">
        <div class = "row">
            <div class = "col-sm-4">
                <h2>Wait Room - ${games.name} - ${games.code}</h2> 
            </div>
                <div class = "col-sm-4" >
            <h3 style = "text-align:center">Creator: ${creator.user.username}</h3>
                </div>
            <div class = "col-sm-4">
                <h3 style="text-align: right;">${games.privacity}</h3>
            </div>
        </div>
        <table id="gamesTable" class="table" style = "background:white;">
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
            <a class="btn btn-default" href='<spring:url value="/games/lobby/${games.code}/inviteFriends" htmlEscape="true"/>'>Invite Friends</a>
            <a class="btn btn-default" href='<spring:url value="/games/lobby/${games.code}/exitWaitRoom" htmlEscape="true"/>'>Exit</a>
        </div>

    </div>       
    </body>

</parchisoca:layoutWithoutMenu>
