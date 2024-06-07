<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 5/18/2024
  Time: 7:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="container">
        <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/"><i class="fa fa-house"></i></a></li>
                <li class="breadcrumb-item active" aria-current="page">Giỏ hàng</li>
            </ol>
        </nav>
    </div>
    <form:errors path="quantity" class="form-text text-danger"/>
    <div class="row">
        <div class="col-8 rounded-start border border-dark">
            <div class="d-flex justify-content-between align-items-center">
                <h1>Giỏ hàng</h1>
                <div>Giỏ hàng của bạn đang có ${cartItems.size()} sản phẩm</div>
            </div>
            <hr>
            <table class="table table-hover">
                <thead>
                </thead>
                <tbody>
                <c:forEach items="${cartItems}" var="item" varStatus="status">
                    <tr class="text-center" >
                        <td>
                            <img src="${pageContext.request.contextPath}/images/${item.thumbnail}" width="75px"
                                 height="75px"
                                 alt="">
                        </td>
                        <td>
                            <label class="fs-3"> ${item.name}</label>
                            <br>
                            <label> ${item.id}</label>
                        </td>
                        <td>${item.type}</td>
                        <td><fmt:formatNumber value="${item.price}"/>đ</td>
                        <td>
                            <input type="number" id="${item.id}" name="${item.quantity}"
                                   onchange="updateQty('${item.id}')"
                                   class="quantityInput w-50" value="${item.quantity}" min="1">
                        </td>
                        <td id="total_${status.index}" class="text-danger"><fmt:formatNumber value="${item.price * item.quantity}"/>đ</td>
                        <td class="fs-3 ">
                            <a href="/remove/${item.id}" class="text-dark"><i class="fa-solid fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-4 bg-dark rounded-end text-light">
            <form action="/payment" method="post">
                <h1 class="mt-1">Thanh Toán</h1>
                <hr>
                <div class="d-flex justify-content-between mb-3">
                    <label>${cartItems.size()} sản phẩm</label>
                    <label><fmt:formatNumber value="${totalAmount}"/>đ</label>
                </div>
                <label class="mb-3">
                    <span class="fs-4">Phương thức thanh toán</span>
                    <select data-mdb-select-init class="w-100" name="paymentMethod">
                        <option value="COD">Thanh toán khi nhận hàng</option>
                        <option value="Paypal">Thanh toán Paypal</option>
                        <option value="Stripe">Thanh toán Stripe</option>
                    </select>
                </label>
                <label class="w-100 mb-3">
                    <span class="fs-4">Mã giảm giá</span>
                    <br>
                    <input type="text" placeholder="Nhập mã giảm giá" >
                </label>
                <div class="d-flex justify-content-between mb-3">
                    <label class="fs-3">Tổng tiền: </label>
                    <label><fmt:formatNumber value="${totalAmount}"/>đ</label>
                </div>
                <button type="submit" class="btn btn-primary w-100">Thanh toán</button>
            </form>
        </div>
    </div>

</div>
<script>
    $(document).ready(function() {
        const errorMessage = "<c:out value='${errorMessage}'/>";
        if (errorMessage) {
            console.log(errorMessage);
        }
    });

    function updateQty(id) {
        const newQuantity = document.getElementById(id).value;
        console.log(newQuantity)
        if (newQuantity > 0 && newQuantity <= 100) {
            fetch('/updateQuantity', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    'id': id,
                    'quantity': newQuantity
                })
            })
                .then(response => {
                    if (response.ok) {
                        location.reload();
                    } else {
                        console.error('Lỗi cập nhật số lượng');
                    }
                });
        } else {
            alert("Số lượng phải lớn hơn 0 và nhỏ hơn hoặc bằng 100");
            document.getElementById(id).value = 1;
            location.reload();
        }
    }
    
</script>
