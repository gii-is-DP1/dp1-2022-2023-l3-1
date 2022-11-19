<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="games">
    <jsp:body>
        <h2 style = "text-align:center">
            <c:if test="${game['new']}">New </c:if> Game
        </h2>
        </br>
        <form:form modelAttribute="game" class="form-horizontal" id="add-game-form">
            <div class="form-group has-feedback">
                <div class ="row">
                    <div class="col-md-12">
                            <div class="row">
                                
                                <parchisoca:inputField label="Name of room" name="name" /> 
                                
                            </div>
                            
                            </br>
                            <div class = "row">
                                
                                <parchisoca:selectField name="gameType" label="Game Type" names="${gameTypes}" size="2"/>
                                
                            </div>
                            <div class = "row">
                                <div style = "text-align:center">
                                
                                    <label  for="privacity">Privacity</label>
                           
                                    <select id="privacity-input" name="privacity" class="selectpicker">
                                        <option value="PUBLIC">PUBLIC</h3></option>
                                        <option value="PRIVATE">PRIVATE</option>
                                    
                                    </select>
                                    &nbsp 
                                    &nbsp 
                                    <label for="jugadores">Number of Players:</label>
                           
                                    <select id="privacity-input" name="jugadores" class="selectpicker" >
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                    
                                </div>

                            </div>  
                            
                            <div class = "row">
                                
                                <div class="form-group" style="text-align:center">
                                    </br>
                                    <input type="hidden" name="id" value="${card.id}" />
                                    <button class="btn btn-default" type="submit"> Create New Game </button>
                                </div>                        
                            </div>  
                        <input type="hidden" name="code" id="code" value = "${game.generatePassword()}"/>
                        <input type="hidden" name="id" id="id" value = "${game.id}"/>
                        <input type="hidden" name="id" id="id" value = "${game.inProgress}"/>
                    </div>
                </div>
            </div>
        </form:form>

        <div class="col text-center">
            <a class="btn btn-default" href='<spring:url value="/" htmlEscape="true"/>'>Exit</a>
        </div>
    </jsp:body>
</parchisoca:layout>

<style>
    #privacity-input {
        color: black;
    }
</style>