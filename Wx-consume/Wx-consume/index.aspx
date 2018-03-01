<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="Wx_consume.index" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <title>放心消费</title>
    <link href="js/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/indexnew.css" rel="stylesheet" />
    <script src="js/jquery-1.9.1.js"></script>
    <script src="js/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <img class="img-row" src="img/全面.png" />
            <img class="img-row" src="img/主.png" />
        </div>
        <div class="row">
            <div class="col-xs-4">
                <a href="consume.aspx">
                    <img class="img-row" src="img/放心消费.png" />
                </a>
            </div>
            <div class="col-xs-4">
                <a href="news.aspx">
                    <img class="img-row" src="img/新闻动态.png" />
                </a>
            </div>
            <div class="col-xs-4">
                <a href="business.aspx">
                    <img class="img-row" src="img/商家资讯.png" />
                </a>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-4">
                <a href="warn.aspx">
                    <img class="img-row" src="img/消费警示.png" />
                </a>
            </div>
            <div class="col-xs-4">
                <a href="announcement.aspx">
                    <img class="img-row" src="img/公告公示.png" />
                </a>
            </div>
            <div class="col-xs-4">
                <a href="another.aspx">
                    <img class="img-row" src="img/问卷调查.png" />
                </a>
            </div>
        </div>
        <div class="row" style="background-color: #b6cc59;">
            <div class="col-xs-4 col-xs-offset-4">
                <img style="padding-top: 25px;" class="img-row" src="img/底.png" />
            </div>
            <div class="col-xs-4" style="text-align: center; color: white; padding-top: 50px;">
                <p>扫码关注</p>
                <p>放心消费</p>
            </div>
        </div>
        <div class="row">
            <img style="height:45px;" class="img-row" src="img/浅绿.png" />
        </div>
    </div>
</body>
</html>
