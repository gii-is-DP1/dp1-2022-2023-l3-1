<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="players">

    <form:form modelAttribute="player" action="/players" method="get" class="form-horizontal"
               id="search-player-form">
            <div>
                <h2>Find Players</h2>
            </div>
        <div class="row">
            <div class = "col-sm-12">
                Search player:
                <input placeholder ="Insert an username" type="text" name="username" id="username"/>
                <input type="button" value="Search" onclick="findPlayer()" />
            </div>
        </div>

    </form:form>

    <p> &nbsp </p>
    <div class="col text-center">
        <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
    </div>

    <script>
        function findPlayer(){
            window.location="/players/find/"+document.getElementById("username").value;
        }
    </script>
	
</parchisoca:layout>
