<!DOCTYPE html>
<html>
<head>
    <title>Payment Request</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<br><br>
Payer Id: <p style="color: #aa1717">${payerId} </p>
RecipientId: <p style="color: #6daa17">${recipientId} </p>
<form action="/ipg-demo/pay-request" method="post" modelAttribute="payRequest">
    <table >
        <tr >
            <td align="center"
                style="binding: none;background-color: #aa1717;color: #e0eeee; font-size: medium;font-weight: bold";
                colspan="2"> صفحه پرداخت قرارداد
            </td>
        </tr>
        <tr style="width: 400px">
            <td></td>
        </tr>
        <tr>
            <th style="text-align: right">Requester Type:</th>
            <td style="text-align: right">
                <select style="width: 185px" name="requesterType">
                    <option>CONTRACT</option>
                    <option>DOCUMENT</option>
                </select>
            </td>
        </tr>
        <tr>
            <th style="text-align: right">Payment Type:</th>
            <td style="text-align: right">
                <select style="width: 185px" name="paymentType">
                    <option>PURCHASE</option>
                    <option>COMMISSION</option>
                </select>
            </td>
        </tr>
        <tr>
            <th style="text-align: right">Requester ID:</th>
            <td style="text-align: right"><input type="text" name="requesterId"></td>
        </tr>
        <tr>
            <th style="text-align: right">Payer ID:</th>
            <td style="text-align: right"><input  type="text" name="payerId" ></td>
        </tr>
        <tr>
            <th style="text-align: right">Recipient ID:</th>
            <td style="text-align: right"><input type="text" name="recipientId" ></td>
        </tr>
        <tr>
            <th style="text-align: right">Payment Amount:</th>
            <td style="text-align: right"><input type="text" name="amount"></td>
        </tr>
        <tr>
            <th style="text-align: right">Mobile No:</th>
            <td style="text-align: right"><input type="text" name="mobileNo"></td>
        </tr>
        <tr>
            <th style="text-align: right">Order Id:</th>
            <td style="text-align: right"><input type="text" name="orderId"></td>
        </tr>
        <tr>
            <th style="text-align: right">Description: </th>
            <td style="text-align: right"><input type="text" name="description"></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: right"><input type="submit" value="PAYMENT"></td>
        </tr>
    </table>
</form>

</body>
</html>