<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="ocaInstructions">
    <jsp:body>
        <h1> Instrucciones Oca </h1>
        <h2>Cómo Jugar:</h2>
        <ul>
        <li><span>El Juego de la Oca es un juego de mesa en el que pueden participar de dos a cuatro jugadores cada uno con fichas de un color distinto.</span></li>
        <li><span>El tablero en forma de espiral consta de 63 casillas numeradas del 1 al 63 con diferentes dibujos. Dependiendo de la casilla en la que caigas puedes avanzar, retroceder o sufrir una penalización.</span></li>
        <li><span>En su turno cada participante tira un dado que le indica el número de casillas que debe avanzar.</span></p>
        <li"><span>La casilla 63 sólo se puede alcanzar de tirada exacta. Si un jugador tira los dados y saca un número mayor al de las casillas que le quedan por recorrer deberá avanzar hasta llegar a la 63 y después retroceder hasta completar el numero de casillas que le faltan.</span></li>
        </ul>

        <h2>Casillas especiales:</h2>
        <ul>
        <li><span><span>Oca [1,5, 9,14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63] “</span><strong>De oca a oca y tiro porque me toca</strong><span>”. Se avanza a la siguiente oca y se vuelve a tirar.</span></span></li>
        <li><span><span>Puente [6,12] “</span><strong>De puente a puente y tiro porque me lleva la corriente</strong><span>”. Se avanza o retrocede hasta el otro puente y se vuelve a tirar.</span></span></li>
        <li><span><span>Dados [26,53] “</span><strong>De dados a dados y tiro porque me ha tocado</strong><span>”. Se avanza o retrocede hasta los otros dados y se vuelve a tirar.</span></span></li>
        <li><span><span>Posada [19] Se pierden </span><strong>2 turnos</strong><span>.</span></span></li>
        <li><span><span>Prisión [56] Se pierden </span><strong>3 turnos</strong><span>.</span></span></li>
        <li><span><span>Pozo [31] No se puede volver a tirar hasta que </span><strong>otro jugador pase por allí</strong><span> o pasen </span><strong>4 turnos</strong><span>.</span></span></li>
        <li><span><span>Laberinto [42] “</span><strong>Del laberinto al 30</strong><span>”. Se retrocede a la casilla 30.</span></span></li>
        <li><span><span>Muerte [58] Se vuelve a empezar desde la </span><strong>casilla 1</strong><span>.</span></span></li>
        </ul>

        <p> &nbsp </p>
        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/games/instructions/parchisInstructions" htmlEscape="true"/>'>Parchis</a>
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
        </div>
    </jsp:body>
</parchisoca:layout>

