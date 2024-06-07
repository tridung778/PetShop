<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 6/4/2024
  Time: 7:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header text-center">
                    <h1>Register</h1>
                </div>
                <div class="card-body">
                    <form:form method="post" action="/register" modelAttribute="user">
                        <div class="form-group">
                            <label for="name">Name:</label>
                            <form:input type="text" class="form-control" id="name" path="name" name="name"/>
                        </div>
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <form:input type="text" class="form-control" id="username" name="username" path="username"/>
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <form:input type="email" class="form-control" id="email" name="email" path="email"/>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <form:input type="password" class="form-control" id="password" name="password"
                                        path="password"/>
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-success" id="register">Register</button>
                        </div>
                    </form:form>
                </div>
                <div class="card-footer text-center">
                    <a href="/login" class="btn btn-secondary">Back to Login</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    const btnRegister = document.getElementById('register');
    const message = '${message}';
    if (message != '') {
        alert(message)
    }

    btnRegister.addEventListener("click", () => {
        Notification.requestPermission()
            .then(permisson => {
                    if (permisson === "granted") {
                        new Notification("Đăng ký thành công!")
                    }
                }
            )
    })

</script>
</body>
</html>
