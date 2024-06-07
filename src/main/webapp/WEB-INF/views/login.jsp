<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 5/22/2024
  Time: 12:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header text-center">
                    <h1>Login</h1>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/login">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" class="form-control" id="username" name="username" required/>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" class="form-control" id="password" name="password" required/>
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-success">Login</button>
                        </div>
                    </form>
                    <div class="d-flex justify-content-between px-5">
                        <a href="/oauth2/authorization/google" class="btn btn-primary">Google</a>
                        <a href="/oauth2/authorization/github" class="btn btn-dark">GitHub</a>
                    </div>

                </div>
                <div class="card-footer text-center">
                    <a href="/" class="btn btn-secondary">Back to Home</a>
                    <a href="/register" class="btn btn-warning">Register</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
