<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<petclinic:layout pageName="gamesInstruction">
    <jsp:body>
        <h1> Instrucciones Parchís </h1>
        <h2>Cómo Jugar:</h2>
        <p>Tradicionalmente en el parchís participan 4 jugadores, cada uno de ellos utilizando un color diferente: amarillo, azul, rojo y verde. Se emplean 4 fichas de cada color, y cada jugador utiliza un dado.</p>
        <p>El tablero de parchís tiene varias zonas diferenciadas:</p>
        <ul>
            <li> Zonas de salida o casas, situadas en las esquinas del tablero, de los cuatro colores.</li>
            <li>Un recorrido común de 68 casillas por el que avanzan todas las fichas.</li>
            <li>Pasillo y casilla final de los 4 colores, que convergen en el centro del tablero desde los cuatro laterales.</li>
        </ul>
        <h2>
            <span class="glyphicon glyphicon-chevron-right"></span>
                Movimiento de las fichas
        </h2>
        <p>
            Inicialmente cada jugador tiene un par de fichas en la casilla inicial del recorrido, y el resto de fichas situadas en <i>casa</i>.
        </p>
        <p>
            Los participantes en su turno deben <i>tirar el dado</i> y avanzar con una de sus fichas el número de casillas indicado por el número obtenido, teniendo en cuenta las siguientes reglas:
        </p>
        <ul>
            <li>
                    <b>En caso de tirar un 5</b> y tener fichas en casa, una de ellas entra en juego obligatoriamente situándose en la casilla de salida.
            </li>
            <li>
                    Las fichas avanzan siguiendo el recorrido en sentido inverso a las agujas del reloj.
            </li>
            <li>
                    Cuando una ficha completa una vuelta al tablero y llega a la casilla que conecta con el pasillo de su color, se desvía hacia la casilla destino (hacia el centro del tablero).
            </li>
            <li>
                    <b>En caso de tirar un 6</b> y tener todas las fichas del mismo color <i>fuera de casa</i>, se avanzan 7 casillas.
            </li>
            <li>
                    El jugador que ha obtenido <b>un 6 juega de nuevo</b>.
            </li>
            <li>
                    En cada casilla del recorrido puede haber un máximo de dos fichas. Una ficha no puede jugarse si el movimiento le llevara a una casilla en la que ya hay dos fichas.
            </li>
            <li>
                    Una ficha no puede moverse si para completar el avance tuviera que atravesar una barrera. <b>Las barreras son pares de fichas de un mismo color situadas en la misma casilla</b>.
            </li>
            <li>
                    Para alcanzar el final del recorrido es necesario avanzar el número exacto de casillas que restan hasta completarlo. No es posible mover la ficha si el número del dado es superior.
            </li>
            <li>
                    <b>Cuando una ficha completa su recorrido</b>, el jugador debe <b>avanzar 10 casillas</b> con otra de sus fichas.
            </li>
            <li>
                    Puede darse el caso de que todas las fichas estén bloqueadas (por estar en casa, o tras una barrera, o en el final del recorrido). En este caso, simplemente no se realiza ningún movimiento.
            </li>
        </ul>

        <section>
            <h2>
                <span class="glyphicon glyphicon-chevron-right"></span>
                    Capturas
            </h2>
            <p>
                    Una ficha <i>come</i> a otra de diferente color si finaliza su avance en la casilla ocupada por esta última.
            </p>
            <ul>
                <li>
                        Las casillas de salida y las casillas marcadas con un círculo son <b>seguros</b>, donde las capturas no son posibles. Por tanto, en los seguros pueden coincidir dos fichas de diferente color.
                </li>
                <li>
                        Si en la casilla de salida se encuentran dos fichas de diferente color y una nueva ficha sale de su <i>casa</i>, la ficha de diferente color (o si ambas lo son, la última ficha que hubiera llegado a la casilla) resulta capturada.
                </li>
                <li>
                        Las <i>fichas comidas</i> vuelven a su casa, de manera que vuelven a entrar en juego cuando el jugador obtiene un 5 en su tirada.
                </li>
                <li>
                        <b>Quien come una ficha</b> tiene el premio de <b>avanzar 20 casillas</b> con cualquiera de sus fichas.
                </li>
                <li>
                        Si un jugador obtiene <b>un 6 tres veces consecutivas</b>, el tercer movimiento no se realiza, y la ficha movida con el segundo 6 <i>vuelve a su casa</i>, salvo que ya hubiese alcanzado el pasillo final del recorrido.
                </li>
            </ul>
        </section>

        <p> &nbsp </p>
        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/games/instructions/oca" htmlEscape="true"/>'>Siguiente</a>
        </div>
        <p> &nbsp </p>
        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Volver</a>
        </div>

    </jsp:body>
</petclinic:layout>
