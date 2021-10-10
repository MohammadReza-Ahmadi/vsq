
<#--test-->
<!DOCTYPE html>
<html>
<head>
    <title>Payment Response</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <script language="javascript">
        function onLoadSubmit() {
            document.postForm.submit();
        }
    </script>


</head>
<body hidden="true" onload="onLoadSubmit()">

<br><br>


<#--<form action="/ipg-demo/mellat-payment-gateway" name="postForm" method="post" >-->
<#--<form action="https://bpm.shaparak.ir/pgwchannel/" name="postForm" method="post" >-->
<form action="https://bpm.shaparak.ir/pgwchannel/payment.mellat?RefId=${refId}‬‬" name="postForm" method="get" >
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
            <th style="text-align: right">PSP RefId: </th>
            <td style="text-align: right">
                <input  type="text" size="33" name="RefId" value="${refId}"></td>
<#--                <input  type="text" size="33" name="RefId" value="6BC85AC4C44BCD1B"></td>-->
        </tr>
        <tr>
            <th style="text-align: right">Mobile No:</th>
            <td style="text-align: right">
                <input type="text" size="33" name="mobileNo" value="${mobileNo}">
            </td>
        </tr>
        <tr>
            <td style="text-align: right"><input type="submit" value="Redirect To Mellat Payment Gateway"></td>
        </tr>
    </table>
</form>
<#--‫‪https://bpm.shaparak.ir‬‬ ‫‪/pgwchannel/startpay.mellat‬‬-->

</body>
</html>
