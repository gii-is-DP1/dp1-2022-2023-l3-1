<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<game:layout pageName="Parchis Board">

    <h2 style="text-align: center;">Parchis Board</h2>

    <div class="row" style="text-align: center;">
        <div class="col-md-12">
            <game:parchisBoard parchisBoard="${parchisBoard}">
                <c:forEach items="${parchisBoard.pieces}" var="piece">
                    <game:parchisPiece size="30" piece="${piece}"/>            	
                </c:forEach> 
            </game:parchisBoard>
        </div>
    </div>

    <h2 style="text-align: center;">Dice: ${dice1.number}</h2>
    <h2 style="text-align: center;">Dice: ${dice2.number}</h2>
    <div>
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${ocaBoard.game.code}/exit">Piece 1</a> 
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${ocaBoard.game.code}/exit">Piece 2</a> 
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${ocaBoard.game.code}/exit">Piece 3</a> 
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${ocaBoard.game.code}/exit">Piece 4</a> 
    </div>
    <h2 style="text-align: center;">${error}</h2>
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${ocaBoard.game.code}/exit">Exit</a> 
</game:layout>