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

    <div>
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece1.id}">Piece 1</a> 
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece2.id}">Piece 2</a> 
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece3.id}">Piece 3</a> 
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece4.id}">Piece 4</a> 
    </div>
    <h2 style="text-align: center;">${error}</h2>
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${ocaBoard.game.code}/exi">Exit</a> 
</game:layout>