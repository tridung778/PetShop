<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 6/1/2024
  Time: 1:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li class="breadcrumb-item"><a
                    href="${pageContext.request.contextPath}/pets?petCategory=${pet.category.name}">${pet.category.name}</a>
            </li>
            <li class="breadcrumb-item"><a
                    href="${pageContext.request.contextPath}/filter-sort-pet?petCategory=${pet.category.name}&range1=0&range2=10&selectedBreeds=${pet.breed}">${pet.breed}</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">${pet.name}</li>
        </ol>
    </nav>
    <div class="row">
        <div class="col-8">
            <img src="/images/${pet.image}"
                 class="img-fluid rounded"
                 alt="...">
        </div>
        <div class="col-4">
            <h1>${pet.name}</h1>
            <p>Mô tả: ${pet.description}</p>
            <p>Chủng loại: ${pet.breed}</p>
            <p>Tuổi: ${pet.age} tháng</p>
            <c:if test="${pet.gender.toString() == 'MALE'}">
                <p>Giới tính: Đực</p>
            </c:if>
            <c:if test="${pet.gender.toString() == 'FEMALE'}">
                <p>Giới tính: Cái</p>
            </c:if>
            <c:if test="${pet.status.toString() == 'AVAILABLE'}">
                <p class="text-success">Trạng thái: Chưa bán</p>
            </c:if>
            <c:if test="${pet.status.toString() == 'OUT_OF_STOCK'}">
                <p class="text-danger">Trạng thái: Đã bán</p>
            </c:if>
            <p>Giá: <fmt:formatNumber value="${pet.price}"/> đ</p>
            <form onsubmit="addToCart('${pet.id}', 1)">
                <input type="hidden" name="quantity" value="1">
                <button type="submit"
                        class="btn border-primary d-flex justify-content-center">Thêm vào
                    giỏ hàng
                </button>
            </form>
        </div>
    </div>
</div>
