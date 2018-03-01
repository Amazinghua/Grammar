<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="complaints.aspx.cs" Inherits="Wx_consume.complaints" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <title>投诉举报</title>
    <link href="js/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/basic.css" rel="stylesheet" />
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/Ajax.js"></script>
    <script src="js/tools.js"></script>
    <script src="js/complaints.js"></script>
</head>
<body>
    <div class="con">
        <div class="wxgs_top">
            <img src="img/投诉流程.png" />
        </div>
        <ul id="allmessage" class="ul01">
        </ul>
    </div>
</body>
</html>
