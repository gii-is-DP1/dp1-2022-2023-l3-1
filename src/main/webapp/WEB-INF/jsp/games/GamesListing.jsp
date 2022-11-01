<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Games</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Creador</th>
            <th>Ganador</th>
            <th>Juego</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td>
                    <c:out value="${game.creator}"/>
                </td>
                <td>                    
                      <c:out value="${game.winner} "/>                                        
                </td>
                
                <td>       
                    <c:out value="${game.tipo} "/>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>