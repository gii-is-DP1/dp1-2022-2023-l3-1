<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="agmes">
    <jsp:body>
        <h2>
            <c:if test="${game['new']}">New </c:if> Game
        </h2>
        <form:form modelAttribute="game" class="form-horizontal" id="add-game-form">
            <div class="form-group has-feedback">
                <div class ="row">
                    <div class="col-sm-12">
                            <div class="row">
                                <petclinic:inputField label="Name of room" name="name" /> 
                            </div>
                            <div class = "row">
                                <label class="col-sm-2 control-label" for="jugadores">Number of Players:</label>
                           
                                <select id="privacity-input" name="jugadores" class="selectpicker">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                </select>
                            </div>
                            </br>
                            <div class = "row">
                                <div class="col-sm-6">
                                    <petclinic:selectField name="gameType" label="Game Type" names="${gameTypes}" size="3" />
                                </div>
                            </div>
                            <div class = "row">
                                <label class="col-sm-2 control-label" for="privacity">Privacity</label>
                           
                                <select id="privacity-input" name="privacity" class="selectpicker">
                                    <option value="PUBLIC">PUBLIC</option>
                                    <option value="PRIVATE">PRIVATE</option>
                                    
                                </select>
                            </div>    
                        <input type="hidden" name="code" id="code" value = "${game.generatePassword()}"/>
                        <input type="hidden" name="id" id="id" value = "${game.id}"/>
                        <%-- <input type="hidden" name="creator" id="creator"/> --%>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${card.id}" />
                    <button class="btn btn-default" type="submit"> Create New Game </button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
