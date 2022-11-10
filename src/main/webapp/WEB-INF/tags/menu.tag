<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">

        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand"
                    href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#main-navbar">
                    <span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
                        class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
            </div>
            <div class="navbar-collapse collapse" id="main-navbar">
                <ul class="nav navbar-nav">

                    <petclinic:menuItem active="${name eq 'home'}" url="/"
                        title="home page">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                        <span>Home</span>
                    </petclinic:menuItem>

                    <petclinic:menuItem active="${name eq 'players'}" url="/players"
                        title="Players" dropdown="${true}">
                        <ul class="dropdown-menu">
                                <li>
                                    <a href="<c:url value="/players/find" />"> <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Find Player</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                <sec:authorize access="hasAuthority('admin')">
                                    <a href="<c:url value="/players/list" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Players List </a>
                                </sec:authorize>
                                </li>
                            </ul>
                    </petclinic:menuItem>

                    <petclinic:menuItem active="${name eq 'achievements'}" url="/statistics/achievements"
                        title="Achievements" dropdown="${true}">
                            <ul class="dropdown-menu">
                                <li>
                                <sec:authorize access="hasAuthority('admin')">
                                    <a href="<c:url value="/statistics/achievements/" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Achievements listing</a>
                                </sec:authorize>
                                </li>
                                <li class="divider"></li>
                                <li>
                                <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/statistics/achievements/user" />"> <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span> My Achievements </a>
                                </sec:authorize>
                                </li>
                            </ul>
                    </petclinic:menuItem>

                    <petclinic:menuItem active="${name eq 'games'}" url="/games/create"
                        title="Games" dropdown="${true}">
                            <ul class="dropdown-menu">
                                <li>
                                <sec:authorize access="hasAuthority('admin')">
                                    <a href="<c:url value="/games/list" />"> <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Games listing</a>
                                </sec:authorize>
                                </li>
                                <li class="divider"></li>
                                <li>
                                <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/games/create" />"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New Game </a>
                                </sec:authorize>
                                </li>
                                <li>
                                <sec:authorize access="hasAuthority('player')">
                                    <a href="<c:url value="/games/lobbys" />"><span class="glyphicon glyphicon-list" aria-hidden="true"></span> Public Games </a>
                                </sec:authorize>
                                </li>
                            </ul>
                    </petclinic:menuItem>

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
                                <li class="divider"></li>

<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
