<!DOCTYPE html>
<html>
<head>
    <title>Mellat Payment Response Gateway</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<br><br>
Here payment-gateway callback method is called.
<form method="post"  action="http://localhost:8013/callbacks/mellat-ipg" >
    <table border="0">
        <tr>
            <td align="center"
                style="binding: none;background-color: #aa1717;color: #e0eeee; font-size: medium;font-weight: bold"
                colspan="2"> پاسخ دروازه پرداخت ملت
            </td>
        </tr>

        <tr>
            <th style="text-align: right">Posted RefId:</th>
            <td style="text-align: right"><input type="text" size="33" name="RefId" value="${RefId}"></td>
        </tr>
       <tr>
            <th style="text-align: right">Posted ResCode:</th>
            <td style="text-align: right">
                <select style="width: 290px" name="ResCode">
                    <option>0</option>
                    <option>1</option>
                </select>
        </tr>
       <tr>
            <th style="text-align: right">Posted SaleOrderId:</th>
            <td style="text-align: right"><input type="text" size="33" name="SaleOrderId" ></td>
        </tr>
        <tr>
            <th style="text-align: right">Posted SaleReferenceId:</th>
            <td style="text-align: right"><input type="text" size="33" name="SaleReferenceId" ></td>
        </tr>
        <tr>
            <th style="text-align: right">Posted FinalAmount:</th>
            <td style="text-align: right"><input type="text" size="33" name="FinalAmount" ></td>
        </tr>
          <tr>
            <th style="text-align: right">Posted CardHoldPAN:</th>
            <td style="text-align: right"><input type="text" size="33" name="CardHoldPAN" ></td>
        </tr>
        <tr>
            <th style="text-align: right">Posted CreditCardSaleResponseDetail:</th>
            <td style="text-align: right"><input type="text" size="33" name="CreditCardSaleResponseDetail" ></td>
        </tr>

        <tr style="height: 50px">
            <td style="text-align: right">
                <input type="submit" value="پرداخت ناموفق"  >
            </td>
            <td style="text-align: right">
                <input type="submit" value="پرداخت موفق" >
            </td>
        </tr>
    </table>
</form>


</body>
</html>