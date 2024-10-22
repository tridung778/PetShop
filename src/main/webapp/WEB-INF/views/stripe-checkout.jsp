<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 6/7/2024
  Time: 11:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>Accept a payment</title>
    <meta name="description" content="A demo of a payment on Stripe" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="/css/checkout.css" />
    <script src="https://js.stripe.com/v3/"></script>
    <script>
        /*<![CDATA[*/
        const publicKey = "${publicKey}";
        const amount = "${amount}";
        const email = "${email}";
        const productName = "${productName}";
        /*]]>*/
    </script>
    <script src="${pageContext.request.contextPath}/js/checkout.js" defer></script>
</head>
<body>
<!-- Display a payment form -->
<form id="payment-form">
    <h2>Cảm ơn bạn đã sử dụng dịch vụ của PetTD</h2>
    <span>Mời bạn thanh toán hóa đơn: </span>
    <span>${amount}</span>
    <span>USD</span>
    <div id="link-authentication-element">
        <!--Stripe.js injects the Link Authentication Element-->
    </div>
    <div id="payment-element">
        <!--Stripe.js injects the Payment Element-->
    </div>
    <button id="submit">
        <div class="spinner hidden" id="spinner"></div>
        <span id="button-text">Thanh toán ngay</span>
    </button>
    <div id="payment-message" class="hidden"></div>
</form>
</body>
</html>