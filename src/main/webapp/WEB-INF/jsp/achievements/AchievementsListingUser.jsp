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

            </tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${players.achievements}">
                <tr>
                    <td>
                        <c:out value="${a.name}" />
                    </td>
                    <td>
                        <c:out value="${a.actualDescription}" />
                    </td>
                    <td>
                        <c:if test="${achievement.badgeImage == ''}">none</c:if>
                        <c:if test="${achievement.badgeImage != ''}">
                            <img src="${a.badgeImage}" width="70px" />
                        </c:if>
                    </td>
                    <td>
                        <c:out value="${a.threshold}" />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</petclinic:layout>