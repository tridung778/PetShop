<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 5/17/2024
  Time: 8:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="localeCookie" value="${cookie['locale'].value}" />
<fmt:setLocale value="${localeCookie}" />
<fmt:setBundle basename="messages"/>
<%--Banner --%>
<div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">

    <div class="carousel-inner">
        <div class="carousel-item active" data-bs-interval="3000">
            <img src="https://mauweb.monamedia.net/petcare/wp-content/uploads/2019/10/slider-shop-1.jpg"
                 class="d-block w-100" alt="...">
            <div class="carousel-caption d-none d-md-block">
                <h1>TD Pet</h1>
                <p><fmt:message key="sen_oi_vao_day_ma_xem"/></p>
            </div>
        </div>
        <div class="carousel-item" data-bs-interval="2000">
            <img src="https://mauweb.monamedia.net/petcare/wp-content/uploads/2019/10/slider-shop-2.jpg"
                 class="d-block w-100" alt="...">
            <div class="carousel-caption d-none d-md-block">
                <h1>Uy tín</h1>
                <p><fmt:message key="uy_tin_da_duoc_kiem_chung_chat_luong_da_duoc_khang_dinh"/></p>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
<%-- Home Component--%>
<div class="cards ms-1 mt-5 d-flex w-100 justify-content-center">
    <div class="card red mx-5 row">
        <div class="col-3 ">
            <i class="fa-solid fa-truck fs-1"></i>
        </div>
        <div class="col-9">
            <p class="tip"><fmt:message key="nhan_giao_hang"/></p>
            <p class="second-text"><fmt:message key="giao_thu_cung_toan_cac_tinh_tay_nam_bo"/><span class="invisible">Nhận Giao Hàng</span></p>
        </div>
    </div>
    <div class="card blue mx-5 row">
        <div class="col-3 ">
            <i class="fa-solid fa-piggy-bank fs-1"></i>
        </div>
        <div class="col-9">
            <p class="tip"><fmt:message key="gia_ca_hop_ly"/></p>
            <p class="second-text"><fmt:message key="san_pham_da_dang_chung_loai_va_gia_ca"/></p>
        </div>
    </div>
    <div class="card green mx-5 row">
        <div class="col-3 ">
            <i class="fa-solid fa-ticket fs-1"></i>
        </div>
        <div class="col-9">
            <p class="tip"><fmt:message key="ngap_tran_khuyen_mai"/></p>
            <p class="second-text"><fmt:message key="che_do_hau_mai_hap_dan"/><span class="invisible">Ngập Tràn Khuyến Mãi</span></p>
        </div>
    </div>
</div>
<%-- Cat Component--%>
<div class="container bg-primary mt-5 rounded">
    <div class="text-light d-flex justify-content-between align-items-center">
        <h1>Mèo</h1>
        <a href="${pageContext.request.contextPath}/pets?petCategory=cat" class="text-light text-decoration-none"><fmt:message key="xem_tat_ca"/> ></a>
    </div>
    <hr>
    <div class="row">
        <c:set var="catCount" value="0" />
        <c:forEach items="${pets}" var="pet">
            <c:if test="${catCount < 4}">
                <c:if test="${pet.category.name == 'Cat'}">
                    <c:set var="catCount" value="${catCount + 1}" />
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <img src="/images/${pet.image}"
                                 class="card-img-top"
                                 alt="...">
                            <div class="middle">
                                <div class="text">
                                    <a class="text-decoration-none text-light" href="${pageContext.request.contextPath}/pet-detail/${pet.id}"><fmt:message key="xem_chi_tiet"/></a>
                                </div>
                            </div>
                            <div class="card-body d-flex justify-content-center align-items-center flex-column">
                                <div class="d-flex flex-row">
                                    <h5 class="card-title me-2">${pet.name}</h5>
                                    <c:if test="${pet.gender.toString() == 'MALE'}">
                                        <i class="fa-solid fa-mars"></i>
                                    </c:if>
                                    <c:if test="${pet.gender.toString() == 'FEMALE'}">
                                        <i class="fa-solid fa-venus"></i>
                                    </c:if>
                                </div>
                                <p class="card-text">${pet.breed}</p>
                                <p class="text-danger">
                                    <fmt:formatNumber value="${pet.price}"/> đ
                                </p>
                                <form onsubmit="addToCart('${pet.id}', 1)">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn border-primary d-flex justify-content-center"><fmt:message key="them_vao_gio_hang"/></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:if>
        </c:forEach>
    </div>
</div>

<%-- Dog Component--%>
<div class="container bg-danger mt-5 rounded">
    <div class="text-light d-flex justify-content-between align-items-center">
        <h1>Chó</h1>
        <a href="${pageContext.request.contextPath}/pets?petCategory=dog" class="text-light text-decoration-none"><fmt:message key="xem_tat_ca"/> ></a>
    </div>
    <hr>
    <div class="row">
        <c:set var="dogCount" value="0" />
        <c:forEach items="${pets}" var="pet">
            <c:if test="${dogCount < 4}">
                <c:if test="${pet.category.name == 'Dog'}">
                    <c:set var="dogCount" value="${dogCount + 1}" />
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <img src="/images/${pet.image}"
                                 class="card-img-top"
                                 alt="...">
                            <div class="middle">
                                <div class="text">
                                    <a class="text-decoration-none text-light" href="${pageContext.request.contextPath}/pet-detail/${pet.id}"><fmt:message key="xem_chi_tiet"/></a>
                                </div>
                            </div>
                            <div class="card-body d-flex justify-content-center align-items-center flex-column">
                                <div class="d-flex flex-row">
                                    <h5 class="card-title me-2">${pet.name}</h5>
                                    <c:if test="${pet.gender.toString() == 'MALE'}">
                                        <i class="fa-solid fa-mars"></i>
                                    </c:if>
                                    <c:if test="${pet.gender.toString() == 'FEMALE'}">
                                        <i class="fa-solid fa-venus"></i>
                                    </c:if>
                                </div>
                                <p class="card-text">${pet.breed}</p>
                                <p class="text-danger">
                                    <fmt:formatNumber value="${pet.price}"/> đ
                                </p>
                                <form onsubmit="addToCart('${pet.id}', 1)">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn border-primary d-flex justify-content-center"><fmt:message key="them_vao_gio_hang"/></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:if>
        </c:forEach>
    </div>
</div>
