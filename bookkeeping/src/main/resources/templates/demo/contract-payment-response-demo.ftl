<!DOCTYPE html>
<html>
<head>
    <title>Payment Response</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<br><br>


<form action="/ipg-demo/mellat-payment-gateway" method="post" >
    <table>
        <tr>
            <td align="center"
                style="binding: none;background-color: #aa1717;color: #e0eeee; font-size: medium;font-weight: bold"
                colspan="2">صفحه پاسخ به درخواست پرداخت
            </td>
        </tr>
        <tr>
            <td></td>
        </tr>
        <tr>
            <td></td>
        </tr>

        <tr>
<#--            <th style="text-align: right">شماره یکتای تولید شده پرداخت: </th>-->
            <th style="text-align: right">Payment Request Id: </th>
            <td style="text-align: right">
                <input type="text" size="33" name="paymentRequestId" value="${payResponse.paymentRequestId}"></td>
        </tr>
        <tr>
            <th style="text-align: right">Gateway RefId: </th>
            <td style="text-align: right">
                <input  type="text" size="33" name="RefId" value="${payResponse.gatewayRefId}"></td>
        </tr>
        <tr>
            <th style="text-align: right">Mobile No:</th>
            <td style="text-align: right"><input type="text" size="33" name="mobileNo" value="${payResponse.mobileNo}"></td>
        </tr>
        <tr>
            <td style="text-align: right"><input type="submit" value="Redirect To Mellat Payment Gateway"></td>
        </tr>
    </table>
</form>
<#--‫‪https://bpm.shaparak.ir‬‬ ‫‪/pgwchannel/startpay.mellat‬‬-->

</body>
</html>