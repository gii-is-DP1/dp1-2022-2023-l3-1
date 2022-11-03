<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<game:layout pageName="home">


    <h2><fmt:message key="parchisBoard"/></h2>
    
    <p>	
    <h2><c:out value="${now}"/></h2>

    <div class="row">
        <div class="col-md-12">
            <game:parchisBoard parchisBoard="${parchisBoard}">
            </game:parchisBoard>
        </div>
    </div>
</game:layout>