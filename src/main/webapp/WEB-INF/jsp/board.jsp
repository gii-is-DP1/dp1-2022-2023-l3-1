<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>


<game:layout pageName = "Home">

    <h2><fmt:message key = "Board"></h2>
    
    <div class = "row">
        <div class ="col-md-12">
            <game:board oca = "${TableroOca}"></game:board>
        </div>
    </div>
</game:layout> 