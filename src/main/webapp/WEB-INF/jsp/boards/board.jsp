<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>  


<game:layout pageName = "Board">
    
    <h2><c:out value = "${now}"/></h2>

    <div class = "row">
        <div class ="col-md-12">
            <game:board ocaBoard = "${ocaBoard}"></game:board>
        </div>
    </div>
</game:layout> 