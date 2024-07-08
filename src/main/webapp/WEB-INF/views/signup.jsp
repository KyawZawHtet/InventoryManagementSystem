<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0"/>
    <meta name="description" content="Inventory Management System"/>
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, management, minimal, modern,  html5, responsive"/>
    <meta name="author" content="ACE Java Web Team"/>

    <title>Inventory Management System</title>

    <link
            rel="shortcut icon"
            type="image/x-icon"
            href="<c:url value="/resources/assets/img/favicon.jpg"/> "
    />

    <link rel="stylesheet" href="<c:url value="/resources/assets/css/bootstrap.min.css"/> " />

    <link
            rel="stylesheet"
            href="<c:url value="/resources/assets/plugins/fontawesome/css/fontawesome.min.css"/> "
    />
    <link rel="stylesheet" href="<c:url value="/resources/assets/plugins/fontawesome/css/all.min.css"/> " />

    <link rel="stylesheet" href="<c:url value="/resources/assets/css/style.css"/> " />

</head>

<body class="account-page">
<div class="main-wrapper">
    <div class="account-content">
        <div class="login-wrapper">
            <div class="login-content">
                <div class="login-userset">
                    <div class="login-logo">
                        <img src="<c:url value="/resources/assets/img/logo.png"/> " alt="img" />
                    </div>
                    <div class="login-userheading">
                        <h3>Create an Account</h3>
                        <h4>Continue where you left off</h4>
                    </div>
                    <div class="form-login">
                        <label>Full Name</label>
                        <div class="form-addons">
                            <input type="text" placeholder="Enter your full name" />
                            <img src="<c:url value="/resources/assets/img/icons/users1.svg"/> " alt="img" />
                        </div>
                    </div>
                    <div class="form-login">
                        <label>Email</label>
                        <div class="form-addons">
                            <input type="text" placeholder="Enter your email address" />
                            <img src="<c:url value="/resources/assets/img/icons/mail.svg"/> " alt="img" />
                        </div>
                    </div>
                    <div class="form-login">
                        <label>Password</label>
                        <div class="pass-group">
                            <input
                                    type="password"
                                    class="pass-input"
                                    placeholder="Enter your password"
                            />
                            <span class="fas toggle-password fa-eye-slash"></span>
                        </div>
                    </div>
                    <div class="form-login">
                        <a class="btn btn-login">Sign Up</a>
                    </div>
                    <div class="signinform text-center">
                        <h4>
                            Already a user?
                            <a href="<c:url value="#"/> " class="hover-a">Sign In</a>
                        </h4>
                    </div>
                    <div class="form-setlogin">
                        <h4>Or sign up with</h4>
                    </div>
                    <div class="form-sociallink">
                        <ul>
                            <li>
                                <a href="javascript:void(0);">
                                    <img
                                            src="<c:url value="/resources/assets/img/icons/google.png"/> "
                                            class="me-2"
                                            alt="google"
                                    />
                                    Sign Up using Google
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <img
                                            src="<c:url value="/resources/assets/img/icons/facebook.png"/> "
                                            class="me-2"
                                            alt="google"
                                    />
                                    Sign Up using Facebook
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="login-img">
                <img src="<c:url value="/resources/assets/img/login.jpg"/> " alt="img" />
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/assets/js/jquery-3.6.0.min.js"/> "></script>

<script src="<c:url value="/resources/assets/js/feather.min.js"/> "></script>

<script src="<c:url value="/resources/assets/js/bootstrap.bundle.min.js"/> "></script>

<script src="<c:url value="/resources/assets/js/script.js"/> "></script>
</body>
</html>