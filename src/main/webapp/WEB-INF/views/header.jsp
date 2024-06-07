<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 5/17/2024
  Time: 3:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="localeCookie" value="${cookie['locale'].value}"/>
<fmt:setLocale value="${localeCookie}"/>
<fmt:setBundle basename="messages"/>
<div>
    <nav class="navbar navbar-expand-lg mt-1 navbar-light bg-light fixed-top w-100">
        <div class="container navbar-custom">
            <a class="navbar-brand" href="/">
                <img src="${pageContext.request.contextPath}/images/td-pet-logo-zip-file/png/logo-no-background.png"
                     alt="Bootstrap" height="30">
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            <fmt:message key="mat_hang_chung_toi_dang_co"/>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="/pets?petCategory=dog"> <fmt:message key="cho"/></a></li>
                            <li><a class="dropdown-item" href="/pets?petCategory=cat"><fmt:message key="meo"/></a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#"><fmt:message key="do_choi_thu_cung"/></a></li>
                            <li><a class="dropdown-item" href="#"><fmt:message key="thuc_an_thu_cung"/></a></li>
                        </ul>
                    </li>
                </ul>
                <form class="navbar-nav d-flex mx-auto" role="search" action="/search" method="GET">
                    <div class="input-group">
                        <label class="second-text">
                            <input class="form-control me-2 w-100 awesomplete" autocomplete="off"
                                   list="mylist"
                                   name="name-product"
                                   placeholder="<fmt:message key="tim_ten_san_pham"/>">
                        </label>
                        <datalist id="mylist">
                            <c:forEach items="${pets}" var="pet">
                                <option value="${pet.name}">
                                    <div>${pet.breed}</div>
                                </option>
                            </c:forEach>
                        </datalist>
                    </div>

                </form>
                <div class="d-flex mx-auto ">
                    <div class="d-flex me-4 align-items-center">
                        <div class="mt-2">
                            <c:choose>
                                <c:when test="${userInfo == ''}">
                                    <img class="rounded-circle" src="/images/default-avatar.jpg" alt="Avatar"
                                         style="width: 2.5rem;height: 2.5rem">
                                </c:when>
                                <c:otherwise>
                                    <img class="rounded-circle" src="${userInfo.photo}" alt="Avatar"
                                         style="width: 2.5rem;height: 2.5rem">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <c:if test="${not empty userInfo}">
                            <!-- Hiển thị thông tin người dùng đã đăng nhập -->
                            <div class="d-flex flex-column ms-2">
                                <a class="text-decoration-none text-dark"
                                   href="${pageContext.request.contextPath}/user-page">
                                    <span>${userInfo.name}</span>
                                </a>
                            </div>
                        </c:if>
                        <c:if test="${empty userInfo}">
                            <!-- Hiển thị liên kết đăng nhập/đăng ký nếu chưa đăng nhập -->
                            <a class="text-decoration-none text-dark" href="${pageContext.request.contextPath}/login">
                                <div class="d-flex flex-column ms-2">
                                    <span><fmt:message key="dang_nhap"/></span>
                                    <span><fmt:message key="dang_ky"/></span>
                                </div>
                            </a>
                        </c:if>
                    </div>
                    <a href="/openCart" class="d-flex text-dark text-decoration-none">
                        <i class="fa fa-cart-shopping fs-3 mt-2"></i>
                        <div class="d-flex flex-column ms-2">
                            <span><fmt:message key="gio_hang_cua_ban"/></span>
                            <span id="cart-count">${animalIndex} <fmt:message key="san_pham"/></span>
                        </div>
                    </a>
                    <div class="d-flex ms-4 align-items-center text-dark">
                        <a href="/locale?lang=vi"><fmt:message key="tieng_viet"/></a> | <a href="/locale?lang=en"><fmt:message key="tieng_anh"/></a>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</div>