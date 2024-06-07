<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 6/4/2024
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div class="container">
        <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/"><i class="fa fa-house"></i></a></li>
                <li class="breadcrumb-item active" aria-current="page">${userInfo.name}</li>
            </ol>
        </nav>
    </div>
    <div>
        <form:form class="me-auto ms-auto w-50 mt-3" action="/update-user" method="post" modelAttribute="userInfo"
                   enctype="multipart/form-data">
            <h1 class="text-center">Chỉnh sửa thông tin khách hàng</h1>
            <form:hidden path="id"/>
            <form:hidden path="password"/>
            <form:hidden path="role"/>
            <div class="row">
                <div class="col-4">
                    <div class="mb-3 d-flex justify-content-center flex-column">
                        <label class="form-label">Avatar</label>
                        <c:choose>
                            <c:when test="${userInfo.photo == null}">
                                <img class="rounded-circle" src="/images/default-avatar.jpg" alt="Avatar"
                                     style="width: 15rem;height: 15rem">
                            </c:when>
                            <c:otherwise>
                                <img class="rounded-circle" src="${userInfo.photo}" alt="Avatar"
                                     style="width: 15rem;height: 15rem">
                            </c:otherwise>
                        </c:choose>
                        <input type="file" class="form-control" id="inputGroupFile01" name="photo-file"
                               style="display:none">
                        <c:choose>
                            <c:when test="${userInfo == ''}">
                                <label for="inputGroupFile01" id="file-label" class="btn btn-success">Thêm
                                    Avatar</label>
                            </c:when>
                            <c:otherwise>
                                <label for="inputGroupFile01" id="file-label"
                                       class="btn btn-success">Đổi Avatar</label>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="col-8">
                    <div class="mb-3">
                        <label for="exampleInputEmail1" class="form-label">Họ và tên</label>
                        <form:input path="name" type="text" class="form-control" id="exampleInputEmail1"
                                    aria-describedby="emailHelp"/>
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputEmail1" class="form-label">Tên đăng nhập</label>
                        <form:input path="username" type="text" readonly="true" class="form-control" id="exampleInputEmail1"
                                    aria-describedby="emailHelp"/>
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputEmail1" class="form-label">Email</label>
                        <form:input path="email" type="email" class="form-control" id="exampleInputEmail1"
                                    aria-describedby="emailHelp"/>
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputEmail1" class="form-label">Số điện thoại</label>
                        <form:input path="phone" type="number" class="form-control" id="exampleInputEmail1"
                                    aria-describedby="emailHelp"/>
                    </div>
                    <div class="col-12 second-text">
                        <label for="inputDescription" class="form-label">Địa chỉ</label>
                        <form:textarea id="inputDescription" class="form-control" aria-label="With textarea"
                                       path="address"/>
                    </div>
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" formaction="/logout" class="btn btn-danger float-end ">Đăng xuất</button>
                <button type="submit" class="btn btn-success float-end mx-3">Save</button>
                <button type="submit" formaction="/history" class="btn btn-warning float-end ">Lịch sử mua hàng</button>
            </div>
        </form:form>
    </div>
</div>
<script>
    const message = '${messageInfo}';
    if (message != '') {
        alert(message)
    }
</script>
