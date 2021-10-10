<!DOCTYPE html>
<html>
<head>
    <title>Mellat Payment Gateway</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<script>

var url;

    function confirm() {
        url = "/ipg-demo/pay-request-confirm";
    }

    function cancel() {
        url = "/ipg/pay-request-cancel";
    }

    function getUrl() {
        document.myForm.action = this.url;
    }

</script>

<body>

<br><br>

RefId and mobileNo have been posted to Mellat Payment Gateway.
<#--<form action="/ipg/pay-request-confirm" method="post">-->
    <form name="myForm" method="post" onsubmit="getUrl()" >
    <table border="0">
        <tr>
            <td align="center"
                style="binding: none;background-color: #aa1717;color: #e0eeee; font-size: medium;font-weight: bold"
                colspan="2"> دروازه پرداخت ملت
            </td>
        </tr>
        <tr>
            <#--            <td colspan="2" ><img src="file:///ipg-demo/mellat-payment-gateway.jpg" ></td>-->
        </tr>
        <tr>
            <th style="text-align: right">Posted RefId:</th>
            <td style="text-align: left"><label > ${RefId}</label></td>
            <input hidden="true" name="refId" type="text" value="${RefId}" />
        </tr>
        <tr>
            <th style="text-align: right">Posted MobileNo:</th>
            <td style="text-align: left"><label> ${mobileNo}</label></td>
        </tr>
        <tr style="height: 50px">
            <td style="text-align: right">
                <input type="submit" value="انصراف" onclick="cancel()" >
            </td>
            <td style="text-align: right">
                <input type="submit" value="پرداخت" onclick="confirm()">
            </td>
        </tr>
    </table>
</form>


</body>
</html>