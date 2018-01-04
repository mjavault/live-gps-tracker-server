<%@ page import="tracking.domain.Tracker" %>
<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 8/6/2015
  Time: 4:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("tracker", Tracker.getInstance());
%>
<html>
<head>
    <title>GPS Tracker</title>
    <style>
        @import url(Cesium/Widgets/widgets.css);

        #cesiumContainer {
            position: absolute;
            top: 0;
            left: 0;
            height: 100%;
            width: 100%;
            margin: 0;
            overflow: hidden;
            padding: 0;
            font-family: sans-serif;
        }

        html {
            height: 100%;
        }

        body {
            padding: 0;
            margin: 0;
            overflow: hidden;
            height: 100%;
        }
    </style>
</head>
<body>
<div id="cesiumContainer"></div>

<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="Cesium/Cesium.js"></script>
<script src="js/main.js"></script>
</body>
</html>
