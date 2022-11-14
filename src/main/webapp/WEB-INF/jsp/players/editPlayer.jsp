<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="editPlayer">
    <jsp:body>
        <h2>Edit Player</h2>
        <form:form modelAttribute="player"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${player.id}"/>
            <div class="form-group has-feedback">                
                <parchisoca:inputField label="First Name" name="firstName"/>
                <parchisoca:inputField label="Last Name" name="lastName"/>
                <parchisoca:inputField label="Email" name="email"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-default" type="submit">Update Player</button>
                </div>
            </div>
        </form:form>        
    </jsp:body>
</parchisoca:layout>