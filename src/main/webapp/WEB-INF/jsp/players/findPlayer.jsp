<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="parchisoca" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<parchisoca:layout pageName="players">
          
    <div class = "container">
        <div  class="row" style = "justify-content">
        <div class = "col-md-3"></div>
        <div class= "col-md-6" >
            <h1 style = "padding:20px; color : black;">Find Players</h1 >
        
            <div style = "text-align: center;">

                <div class="row">
                    <div class="col-sm-10">
                    <input type="text" id="username" class="form-control" />
                    </div>
                    <div class="col-sm-1">
                    <button style="border-radius:5px" id="search-button" type="button" onclick="findPlayer()" class="btn btn-primary">
                    <span class= "glyphicon glyphicon-search" />
                    </button>
                    </div>
                </div>

                <%-- <input id="username" type="text" class="form-control" />
                <input placeholder ="Insert an username" type="text" name="username" id="username"/>
                <label id="search-input" type="button" id="form1" class="form-control" onclick = "findPlayer()">Search </label>
                <input type="button" value="Search" onclick="findPlayer()" /> --%>
            </div>
        

            <p> &nbsp </p>
            <div class="col text-center">
                <a href = "/">
                    <button class = "btn-outline-primary" type="button">Exit</button>
                </a>
            </div>
        </div>
        <div class = "col-md-3"></div>
    </div>

    <script>
        function findPlayer(){
            window.location="/players/find/"+document.getElementById("username").value;
        }
    </script>
	
</parchisoca:layout>




<style>
    .col-md-6{
        background : white;
        width : 500px;
        text-align: center; 
        border-color: grey; 
        border-style: solid;
        border-radius: 12px;
        border-width: 8px;
        padding: 50px; padding-top:20px;
    }  
    .btn-outline-primary {
    color: #007bff;
    background-image: none;
    border-color: #007bff;
    border-radius: 5px;
    border-width: 1px;
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 1px 5px 0 rgba(0,0,0,0.19);
    font-size: 17.5px;
    font-family: "montserratregular", sans-serif;
    } 
    .btn-outline-primary:hover{
        color : white;
        background-color:#337ab7;
    }
</style>
