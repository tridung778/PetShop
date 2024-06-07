<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 5/15/2024
  Time: 7:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center">
        <div class="fs-3 second-text">
            Products
        </div>
    </div>
    <form:form class="row mt-3 p-2 " action="/admin/add-product/save" method="post"
               modelAttribute="pet"
               enctype="multipart/form-data">
        <form:input type="hidden" path="id"/>
        <div class="col-md-4 second-text">
            <label for="inputEmail4" class="form-label">Tên</label>
            <form:input type="text" class="form-control " id="inputEmail4" path="name"/>
        </div>
        <div class="col-md-4 second-text">
            <label for="inputEmail4" class="form-label">Name</label>
            <form:input type="text" class="form-control " id="inputEmail4" path="breed"/>
        </div>
        <div class="col-md-4 second-text">
            <label for="inputState" class="form-label">Type</label>
            <form:select id="inputState" class="form-select" path="category.name">
                <form:option value="Cat">Mèo</form:option>
                <form:option value="Dog">Chó</form:option>
            </form:select>
        </div>
        <div class="col-md-3 second-text">
            <label for="inputPassword4" class="form-label">Price</label>
            <form:input type="number" class="form-control" id="inputPassword4" path="price"/>
        </div>
        <div class="col-md-3 second-text">
            <label for="inputPassword4" class="form-label">Tuổi</label>
            <form:input type="number" class="form-control" id="inputPassword4" path="age"/>
        </div>
        <div class="col-md-3 second-text">
            <label for="inputState" class="form-label">Type</label>
            <form:select id="inputState" class="form-select" path="gender">
                <form:option value="MALE">Đực</form:option>
                <form:option value="FEMALE">Cái</form:option>
            </form:select>
        </div>
        <div class="col-md-3 second-text">
            <label for="inputState" class="form-label">Type</label>
            <form:select id="inputState" class="form-select" path="status">
                <form:option value="AVAILABLE">AVAILABLE</form:option>
                <form:option value="OUT_OF_STOCK">OUT_OF_STOCK</form:option>
            </form:select>
        </div>
        <div class="col-12 second-text">
            <label for="inputDescription" class="form-label">Description</label>
            <form:textarea id="inputDescription" class="form-control" aria-label="With textarea" path="description"/>
        </div>
        <div class="col-12 second-text">
            <label class="form-label">Ảnh</label>
            <div class="input-group mb-3">
                <input type="file" class="form-control" id="inputGroupFile01" name="photo-file"
                            style="display:none"/>
                <c:choose>
                    <c:when test="${pet.image == null}">
                        <label for="inputGroupFile01" id="file-label" class="btn btn-success">Chon anh</label>
                    </c:when>
                    <c:otherwise>
                        <label for="inputGroupFile01" id="file-label"
                               class="btn btn-success">${pet.image}</label>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
        <button type="submit" class="btn btn-success">Add product</button>
    </form:form>
    <div class="mt-4 d-flex justify-content-between align-items-center">
        <nav class="navbar">
            <div class="container-fluid">
                <form class="d-flex m-3" role="search" action="/admin/add-product/search" method="GET">
                    <div class="input-group mb-3 p-3 pb-1">
                        <label class="second-text" style="display:flex;flex-direction: row;">
                            <input class="form-control me-2" autocomplete="off" style="border: var(--third-text-color)"
                                   list="name-product"
                                   name="name-product">
                            <button type="submit" class="btn btn-outline-success">Search</button>
                        </label>
                        <datalist id="name-product">
                            <c:forEach items="${pets}" var="pet">
                                <option value="${pet.name}"></option>
                            </c:forEach>
                        </datalist>
                    </div>
                </form>
            </div>
        </nav>
        <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                    aria-expanded="false">
                Sort
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Action</a></li>
                <li><a class="dropdown-item" href="#">Another action</a></li>
                <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
        </div>
    </div>

    <div class="container table-responsive py-5">
        <table class=" table table-striped table-bordered table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">STT</th>
                <th scope="col">Hình</th>
                <th scope="col">Tên</th>
                <th scope="col">Chủng loài</th>
                <th scope="col">Loại</th>
                <th scope="col">Giá</th>
                <th scope="col">Tuổi</th>
                <th scope="col">Giới tính</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pets}" varStatus="status" var="pet">
                <tr>
                    <td>${status.index + 1}</td>
                    <th scope="row">
                        <img src="${pageContext.request.contextPath}/images/${pet.image}"
                             width="100px" height="100px" alt="">
                    </th>
                    <td>${pet.name}</td>
                    <td>${pet.breed}</td>
                    <td>${pet.category.name}</td>
                    <td><fmt:formatNumber value="${pet.price}"/> đ</td>
                    <td>${pet.age}</td>
                    <td>${pet.gender}</td>
                    <td>${pet.status}</td>
                    <td>
                        <a href="/admin/add-product/remove/${pet.id}" class="btn btn-outline-danger">Delete</a>
                        <a href="/admin/add-product/edit/${pet.id}" class="btn btn-outline-success">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script>
    document.getElementById('inputGroupFile01').addEventListener('change', function () {
        var fileName = this.value.split('\\').pop();
        document.getElementById('file-label').textContent = fileName || 'Chọn ảnh';
    });

</script>
