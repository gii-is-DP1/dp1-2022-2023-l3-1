<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>

<%@ attribute name="menuName" required="true" rtexprvalue="true"
              description="Name of the active menu: home, owners, vets or error" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<sec:authorize access="authenticated">
    <parchisoca:menu name="${menuName}"/>
</sec:authorize>
