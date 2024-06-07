<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 6/5/2024
  Time: 11:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/history">Lịch sử mua hàng</a></li>
            <li class="breadcrumb-item active" aria-current="page">Chi tiết lịch sử mua hàng</li>
        </ol>
    </nav>
    <table class="table table-striped container">
        <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Tên sản phẩm</th>
            <th scope="col">Số lượng</th>
            <th scope="col">Tổng tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderDetails}" var="orderDetail" varStatus="status">
            <tr>
                <th scope="row">${status.index+1}</th>
                <td>${productName}</td>
                <td>${orderDetail.quantity}</td>
                <td><fmt:formatNumber value="${orderDetail.price}"/>đ</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>