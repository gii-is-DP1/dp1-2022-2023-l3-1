<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>  


<game:layout pageName = "Oca Board">
    
    <h2 style="text-align: center;">Oca Board</h2>

    <div class = "row" style="text-align: center;">
        <div class ="col-md-12">
            <game:ocaBoard ocaBoard = "${ocaBoard}"></game:ocaBoard>
        </div>
    </div>
</game:layout> 