<script type="text/javascript">
    window.onload = function () {
        document.forms['input'].submit();
    }
</script>
<#--valid token: f9cc0793-4260-4f6d-afe9-e63d65d3f11b-->
<form name="input" hidden="true"
      action="https://bpm.shaparak.ir/pgwchannel/payment.mellat?RefId=${refId}‬"
      method="get"
      target="_self"
      modelAttribute="sample"
>

    <input type="text" id="RefId" name="RefId" value="${refId}"/>
<#--    <input type="text" id="mobileNo" name="mobileNo" value="${mobileNo}"/>-->
    <input type="submit" value="Submit"/>
</form>


