<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 5/18/2024
  Time: 6:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">${petName}</li>
        </ol>
    </nav>
    <div class="row">
        <div class="col-2 second-background rounded">
            <form:form action="/filter-sort-pet" method="get" modelAttribute="petFilterForm">
                <input type="hidden" name="petCategory"
                       value="${petName == 'Mèo' ? 'cat' : (petName == 'Chó' ? 'dog' : petName)}">
                <h1 class="fs-4">Khoảng giá</h1>
                <div class="d-flex justify-content-between">
                    <div class="d-flex justify-content-between">
                        <div class="border border-dark rounded w-full text-center">
                            <form:hidden path="range1" id="range1Input"/>
                            <form:label for="range1Input"
                                        path="">10000000₫</form:label>
                        </div>
                        <div class="border border-dark rounded w-full text-center">
                            <form:hidden path="range2" id="range2Input"/>
                            <form:label for="range2Input"
                                        path="">10000000₫</form:label>
                        </div>
                    </div>
                </div>
                <div class="middle-slider">
                    <div class="multi-range-slider">
                        <input type="range" id="input-left" min="0" max="100" value="${petFilterForm.range1/1000000}"/>
                        <input type="range" id="input-right" min="0" max="100" value="${petFilterForm.range2/1000000}"/>
                        <div class="slider">
                            <div class="track"></div>
                            <div class="range"></div>
                            <div class="thumb left"></div>
                            <div class="thumb right"></div>
                        </div>
                    </div>
                </div>
                <hr>
                <h1 class="fs-4">Chủng loại</h1>
                <div>

                    <c:forEach items="${petFilterForm.breeds}" var="breed">
                        <label class="d-flex text-center">
                            <form:checkbox path="selectedBreeds" value="${breed}"/>
                            <span class="ms-2">${breed}</span>
                        </label>
                        <br/>
                    </c:forEach>

                </div>
                <hr>
                <div class="d-flex ">
                    <button type="submit" class="btn btn-success w-50 mx-auto d-flex justify-content-center me-2">Lọc
                    </button>
                    <a href="${pageContext.request.contextPath}/pets?petCategory=${petName == 'Mèo' ? 'cat' : (petName == 'Chó' ? 'dog' : petName)}"
                       class="btn btn-danger w-50 mx-auto d-flex justify-content-center">Bỏ lọc</a>
                </div>
            </form:form>
        </div>

        <div class="col-10">
            <div class="d-flex second-background rounded" style="display: flex;align-items: center">
                <h3 class="me-2">${petName}</h3>
                <span>(<span>${petSize}</span> sản phẩm)</span>
            </div>
            <div class=" second-background rounded my-2 justify-content-between" style="display: flex;align-items: center">
                <div class="d-flex align-items-center">
                    <h5>Sắp xếp theo</h5>
                    <div class="my-2 ms-2">
                        <select id="inputState" class="form-select" path="type"
                                onchange="redirect(this.value)">
                            <option value="">-- Chọn loại sắp xếp --</option>
                            <option value="price_asc">Giá tăng dần</option>
                            <option value="price_desc">Giá giảm dần</option>
                        </select>
                    </div>
                </div>
                <div>
                    <span>Page ${currentPage + 1} of ${totalPages}</span>
                    <c:if test="${currentPage > 0}">
                        <a href="${pageContext.request.contextPath}/pets?petCategory=${petName == 'Mèo' ? 'cat' : (petName == 'Chó' ? 'dog' : petName)}&p=${currentPage - 1}">Previous</a>
                    </c:if>
                    <c:if test="${currentPage < totalPages - 1}">
                        <a href="${pageContext.request.contextPath}/pets?petCategory=${petName == 'Mèo' ? 'cat' : (petName == 'Chó' ? 'dog' : petName)}&p=${currentPage + 1}">Next</a>
                    </c:if>
                </div>
            </div>
            <div class=" second-background rounded">
                <div class="row p-2">
                    <c:forEach items="${pets}" var="pet" varStatus="status">
                        <c:if test="${status.count <= 8}">
                            <div class="col-md-3 mb-3">
                                <div class="card ">
                                    <img src="/images/${pet.image}"
                                         class="card-img-top"
                                         alt="...">
                                    <div class="middle">
                                        <div class="text">
                                            <a class="text-decoration-none text-light"
                                               href="${pageContext.request.contextPath}/pet-detail/${pet.id}">Xem chi
                                                tiết</a>
                                        </div>
                                    </div>
                                    <div class="card-body d-flex justify-content-center align-items-center flex-column">
                                        <div class="d-flex flex-row">
                                            <h5 class="card-title me-2"> ${pet.name}</h5>
                                            <c:if test="${pet.gender.toString() == 'MALE'}">
                                                <i class="fa-solid fa-mars"></i>
                                            </c:if>
                                            <c:if test="${pet.gender.toString() == 'FEMALE'}">
                                                <i class="fa-solid fa-venus"></i>
                                            </c:if>
                                        </div>
                                        <p class="card-text">
                                                ${pet.breed}</p>
                                        <p class="text-danger">
                                            <fmt:formatNumber value="${pet.price}"/> đ
                                        </p>
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
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>