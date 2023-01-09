<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/style.css" media="screen"/>


<game:layoutWithoutMenu pageName="Parchis Board">

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
                                <c:choose>
                                    <c:when test = "${piece.position != null}">
                                        Piece Position: ${piece.position}
                                    </br>
                                    </c:when>
                                    <c:when test = "${piece.finishPosition != null}">
                                        <c:choose>
                                            <c:when test = "${piece.finishPosition == 8}">
                                                Piece in Goal
                                                </br>
                                            </c:when>
                                            <c:otherwise>
                                            Position Finish : ${piece.finishPosition}
                                            </br>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
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

    <div>
    <c:if test = "${piece1.inGoal == false}">
        <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece1.id}">Piece 1</a> 
    </c:if>
    <c:if test = "${piece2.inGoal == false}">
        <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece2.id}">Piece 2</a> 
    </c:if>
    <c:if test = "${piece3.inGoal == false}">
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece3.id}">Piece 3</a> 
    </c:if>
    <c:if test = "${piece4.inGoal == false}">
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice.id}/pieceSelection/piece/${piece4.id}">Piece 4</a> 
    </c:if>
    </div>
    <h2 style="text-align: center;">${error}</h2>
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="/games/lobby/${parchisBoard.game.code}/exit">Exit</a> 
</game:layoutWithoutMenu>