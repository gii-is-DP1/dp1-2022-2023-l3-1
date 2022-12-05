<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen" />

<parchisoca:layout pageName="stats">
    <h2 style = "color:white;">Stats</h2>

    <table  id="statsTable" class="table" style = "background:white;">
        <thead>
            <tr>
                <th>Played Games</th>
                <th>Won Games</th>
                <th>Lost Games</th>
                <th>Ratio</th>
            </tr>
        </thead>
        <tbody>
                <tr>
                    <td>
                        <c:out value="${stats.playedGames}" />
                    </td>
                    <td>
                        <c:out value="${stats.wonGames}" />
                    </td>
                    <td>
                        <c:out value="${stats.lostGames}" />
                    </td>
                    <td>
                        <c:out value="${ratio}%" />
                    </td>
                </tr>
        </tbody>
    </table>
    
    <div class="col text-center">
        <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
    </div>

</parchisoca:layout>
<style>
    #statsTable{
        color:black;
    }
</style>