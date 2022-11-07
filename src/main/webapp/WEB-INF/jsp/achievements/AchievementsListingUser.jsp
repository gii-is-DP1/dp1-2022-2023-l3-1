<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<petclinic:layout pageName="achievement">
    <h2>Achievements</h2>

    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>BadgeImage</th>
            <th>Threshold</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                <td>
                    <c:out value="${achievement.name}"/>
                </td>
                <td>
                    <c:out value="${achievement.threshold}" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
