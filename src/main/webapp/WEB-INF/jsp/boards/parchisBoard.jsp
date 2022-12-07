<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen"/>


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
     <div class =row>
    <table id = "ocaBoardTable" class = "table" style="background:white">
        <thead>
            <tr>
                <th scope ="col">Player</th>
                <th scope ="col">Pieces</th>
                <th scope ="col">Color</th>
            </tr>
        </thead>
        <tbody>
            
            <c:forEach var = "player" items = "${players}">
                <tr>  
                    <td>${player.user.username}</td>
                    <td>
                        <c:forEach var = "piece" items = "${pieces}"> 
                            <c:if test = "${piece.player == player}">
                                Piece Position: ${piece.position}
                                </br>
                            </c:if>
                        
                        </c:forEach>
                    </td>
                    <td> 
                        ${pieces.stream().filter(x->x.player == player).findFirst().get().colour}
                    </td>
                </tr>
            </c:forEach> 
        </tbody>

    </table >
    </div>

    <h2 style="text-align: center;">Dice: ${dice1.number}</h2>
    <h2 style="text-align: center;">Dice: ${dice2.number}</h2>
    <h2 style="text-align: center;">${error}</h2>
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice">Roll</a> 
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${ocaBoard.game.code}/exit">Exit</a> 
</game:layout>