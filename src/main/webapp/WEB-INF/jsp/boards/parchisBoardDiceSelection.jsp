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
    <h2 style="text-align: center;">${error}</h2>
    <div>
    <c:if test = "${dice1.number != null }">
         <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice1.id}/pieceSelection">Dice 1: ${dice1.number}</a> 
    </c:if>
    <c:if test ="${dice2.number != null }">
         <a style="text-align:center" class="btn btn-warning btn-lg active" href="/boards/parchisBoard/${parchisBoard.id}/dice/${dice2.id}/pieceSelection">Dice 2: ${dice2.number}</a> 
    </c:if>
    </div>
    </br>
    <a style="text-align:center" class="btn btn-warning btn-lg active" href="">Exit</a> 
</game:layout>