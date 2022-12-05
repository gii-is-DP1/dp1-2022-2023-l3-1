<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">

        <div class="container-fluid">
            <div class="navbar-header">
                <div style= "margin: 14px">
                    <a href="/">
                        <spring:url value="/resources/images/fondo-oca-web.png" var="logo"/>
                            <img src="${logo}" width="94"/>
                    </a>
                </div>
            </div>
            <div class="navbar-collapse collapse" id="main-navbar">
                <ul class="nav navbar-nav">

                    <parchisoca:menuItem active="${name eq 'home'}" url="/"
                        title="home page">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                        <span>Home</span>
                    </parchisoca:menuItem>

                    <parchisoca:menuItem active="${name eq 'players'}" url="/players"
                        title="Players" dropdown="${true}">
                        <ul class="dropdown-menu">
                                <li>
                                    <sec:authorize access="authenticated">
                                    <a href="<c:url value="/players/find" />"> <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Find Player</a>
                                    </sec:authorize>
                                </li>
                                <li>
                                <sec:authorize access="hasAuthority('admin')">
                                    <a href="<c:url value="/players/list" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Players List </a>
                                </sec:authorize>
                                </li>
                                <li>
                                    <sec:authorize access="hasAuthority('admin')">
                                    <a href="<c:url value="/admin/players/create" />"> <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> Create player </a>
                                </sec:authorize>
                                </li>
                                <li>
                                    <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/players/myFriends" />"> <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> My friends </a>
                                </sec:authorize>
                                </li>
                            </ul>
                    </parchisoca:menuItem>

                    <parchisoca:menuItem active="${name eq 'achievements'}" url="/statistics/achievements"
                        title="Achievements" dropdown="${true}">
                            <ul class="dropdown-menu">
                                <li>
                                <sec:authorize access="hasAuthority('admin')">
                                    <a href="<c:url value="/statistics/achievements/" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Achievements listing</a>
                                </sec:authorize>
                                </li>
                                <li>
                                <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/statistics/achievements/user" />"> <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span> My achievements </a>
                                </sec:authorize>
                                </li>
                            </ul>
                    </parchisoca:menuItem>

                    <parchisoca:menuItem active="${name eq 'games'}" url="/games/create"
                        title="Games" dropdown="${true}">
                            <ul class="dropdown-menu">
                                <li>
                                <sec:authorize access="hasAuthority('admin')">
                                    <a href="<c:url value="/games/list" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Games Listing</a>
                                </sec:authorize>
                                </li>
                                <li>
                                <sec:authorize access="hasAuthority('admin')"> 
                                    <a href="<c:url value="/games/admin/lobbys/played" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Played Games Listing</a>
                                </sec:authorize>
                                </li>
                                <li>
                                <sec:authorize access="hasAuthority('admin')"> 
                                    <a href="<c:url value="/games/admin/lobbys/inProgress" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> In Progress Games Listing</a>
                                </sec:authorize>
                                </li>
                                <li>
                                <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/games/create" />"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New Game</a>
                                </sec:authorize>
                                </li>
                                <li>
                                <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/games/lobbys" />"><span class="glyphicon glyphicon-list" aria-hidden="true"></span> Public Games</a>
                                </sec:authorize>
                                </li>
                            </ul>
                    </parchisoca:menuItem>

                    <parchisoca:menuItem active="${name eq 'Instructions'}" url="/games/instructions"
                        title="How to play" dropdown="${true}">
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="<c:url value="/games/instructions/parchisInstructions" />"> <span class="glyphicon glyphicon-book" aria-hidden="true"></span> Parchis Instructions </a>
                                </li>
                                <li>
                                    <a href="<c:url value="/games/instructions/ocaInstructions" />"> <span class="glyphicon glyphicon-book" aria-hidden="true"></span> Oca Instructions </a>
                                </li>
                            </ul>
                    </parchisoca:menuItem>

                    <parchisoca:menuItem active="${name eq 'Stats'}" url="/stats/playerStats"
                        title="Stats">
                        <span class="glyphicon glyphicon-signal" aria-hidden="true"></span>
                        <span>Stats</span>
                    </parchisoca:menuItem>

                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <sec:authorize access="!isAuthenticated()">
                        <li><a href="<c:url value="/login" />">Login</a></li>
                        <li><a href="<c:url value="/players/create" />">Register</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-user"></span>
                            <strong><sec:authentication property="name" /></strong>
                             <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/players/myProfile" />"> <span class = "glyphicon glyphicon-user" aria-hidden="true"></span> Mi perfil</a>
                                </sec:authorize>
                            </li>
                            <li>
                                <p class="text-center">
                                    <a href="<c:url value="/logout" />" class="btn btn-primary btn-block btn-sm">Cerrar sesion</a>
                                </p>
                            </li>
                        </ul>
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>

