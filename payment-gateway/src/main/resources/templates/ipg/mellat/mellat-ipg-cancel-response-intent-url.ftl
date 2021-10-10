<!DOCTYPE html>
<html>

<head>
    <style>
        #svgStyle {
            position: relative;
            /*width: 100%;*/
            width: auto;
            height: auto;
            padding-bottom: 100%;
            vertical-align: middle;
            margin-top: 100px;
            /*margin: 0;*/
            overflow: hidden;

        }
        #svgStyle svg {
            display: inline-block;
            position: absolute;
            top: 0px; left: 0px;
        }
    </style>

</head>

<body style="text-align:left;">
<p align="center" style="margin-top: 180px;">
    <a style="color: rgba(30,144,255,0.82); text-decoration: none; font-size: 48px; font-weight: bolder;"
       href="vsq://contract/status/${contractId}" target="_self" > بازگشت به وثوق </a>
</p>
<#--viewBox="0 0 1200 808"-->
<figure id=svgStyle>
    <svg  version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
          viewBox="0 0 10 7" preserveAspectRatio="xMinYMin meet">
        <image height="100%" width="100%"
               xlink:href="ipg-payment-failed.svg">
        </image>
        <a xlink:href="vsq://contract/status/${contractId}">
            <rect x="540" y="131" fill="#fff" opacity="0" width="120" height="30"/>
        </a>
    </svg>
</figure>
<p align="center" style="font-weight: bolder; font-size: 38px; margin-top: -160px; color: rgba(255,0,0,0.82) ">
    انصراف کاربر از عملیات پرداخت
</p>
</body>
</html>

