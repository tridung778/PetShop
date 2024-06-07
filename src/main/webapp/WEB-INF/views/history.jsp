<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 6/5/2024
  Time: 10:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Lịch sử mua hàng</li>
        </ol>
    </nav>
    <table class="table table-striped container">
        <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Ngày đặt</th>
            <th scope="col">Số lượng</th>
            <th scope="col">Tổng tiền</th>
            <th scope="col">Trạng thái</th>
            <th scope="col">Xem chi tiết</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order" varStatus="status">
            <tr>
                <th scope="row">${status.index+1}</th>
                <td>${order.orderDate}</td>
                <td>${orderQuantities[order.id]}</td>
                <td><fmt:formatNumber value="${order.totalPrice}"/>đ</td>
                <td>${order.status}</td>
                <td><a href="/history/${order.id}">Xem chi tiết</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
