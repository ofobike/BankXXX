<%--
  Created by IntelliJ IDEA.
  User: ysj
  Date: 2018/12/16
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
   <script>
       $(function(){
           //绑定用户查询余额的功能
           $("#selectRemainingBtn").click(function(){
                 $.get("bankServlet",{method_name:"select_remaining"},function(data){
                      $("#selectRemainingSpan").html("<font color='red'>"+data+"元</font>");
                 });
           });
           //绑定用户存钱的按钮
           $("#saveMoneyBtn").click(function () {
                $.post("bankServlet",{"method_name":"save_money","save_money":$("#save_money").val()},function (data) {
                    $("#saveMoneySpan").html("<font color='red'>"+data+"</font>");
                    //把当前输入框为空
                    $("#save_money").val("");
                });
           });
           //绑定取钱的功能
           $("#takeMoneyBtn").click(function(){
               $.post("bankServlet",{"method_name":"take_money","take_money":$("#take_money").val()},function(data){
                   $("#takeMoneySpan").html("<font color='red'>"+data+"</font>");
                   //把当前输入框为空
                   $("#take_money").val("");
               });
           });
           //转账记录
           $("#transactionBtn").click(function(){
               $.post("bankServlet",$("#transactionForm").serialize(),function(data){
                   $("#transactionSpan").html("<font color='red'>"+data+"</font>");
                   $("#t_money").val("");
                   $("#to_account").val("");
               });
           });
           $("#selectTradeBtn").click(function(){
               $.post("bankServlet", $("#selectTradeForm").serialize(),function(data){
                   if(data.code==1024){
                       $("#selectTradeDiv").html(data.msg);
                   }else if(data.code==999){
                       var html = "<table border='1' bordercolor='gold' width='100%'><tr><td>账户ID</td><td>操作类型</td><td>操作金额</td><td>操作说明</td></tr>";
                       // 如果没有查询出来结果
                       $(data.list).each(function(i,n) {
                           var type;
                           if(n.tradetype==0){
                               type="存款";
                           }else if(n.tradetype==1){
                               type="存款";
                           }else{
                               type="转账";
                           }
                           html += "<tr><td>"
                               + n.accountid
                               + "</td><td>"
                               + type
                               + "</td><td>"
                               + n.trademoney
                               + "</td><td>"
                               + n.tradedigest
                               + "</td></tr>"
                       });
                       html += "</table>";

                       $("#selectTradeDiv").html(html);
                   }
                   $("#startT").val("");
                   $("#endT").val("");
               },"json");
           });
       });
   </script>
</head>
<body>
<center>
    <h1>
        欢迎<font color="gold">-${ accountid.accountid }-</font>登陆XXX银行系统
    </h1>

    <table border="1" bordercolor="gold" cellspacing="0" width="50%">
        <tr>
            <td colspan="3" align="center">账号余额查询</td>
        </tr>
        <tr>
            <td width="40%" align="center">查询结果</td>
            <td width="40%" align="center"><span id="selectRemainingSpan"></span></td>
            <td width="20%" align="center"><input type="button"
                                                  id="selectRemainingBtn" value="查余额"></td>
        </tr>
    </table>
    <br> <br>
    <table border="1" bordercolor="gold" cellspacing="0" width="50%">
        <tr>
            <td colspan="3" width="100%" align="center">存款操作</td>
        </tr>
        <tr>
            <td width="40%" align="center">存入金额</td>
            <td width="40%" align="center"><input type="text"
                                                  id="save_money"></td>
            <td width="40%" align="center"><input id="saveMoneyBtn"
                                                  type="button" value="存钱"></td>
        </tr>
        <tr>
            <td colspan="3" width="100%" align="center"><span
                    id="saveMoneySpan">我是勤劳的小蜜蜂</span></td>
        </tr>
    </table>
    <br> <br>
    <table border="1" bordercolor="gold" cellspacing="0" width="50%">
        <tr>
            <td colspan="3" width="100%" align="center">取款操作</td>
        </tr>
        <tr>
            <td width="40%" align="center">取款金额</td>
            <td width="40%" align="center"><input type="text"
                                                  id="take_money"></td>
            <td width="40%" align="center"><input id="takeMoneyBtn" type="button" value="取钱"></td>
        </tr>
        <tr>
            <td colspan="3" width="100%" align="center"><span
                    id="takeMoneySpan">有钱任性花</span></td>
        </tr>
    </table>
    <br>
    <br>
    <form id="transactionForm">
        <input type="hidden" name="method_name" value="accuont_transaction">
        <table border="1" bordercolor="gold" cellspacing="0" width="50%">
            <tr>
                <td colspan="2" width="100%" align="center">账户交易转账</td>
            </tr>
            <tr>
                <td width="50%" align="center">接收账号</td>
                <td width="50%" align="center"><input type="text"
                                                      name="to_account" id="to_account"></td>
            </tr>
            <tr>
                <td width="50%" align="center">转账金额</td>
                <td width="50%" align="center"><input type="text"
                                                      name="t_money" id="t_money"></td>
            </tr>
            <tr>
                <td colspan="2" width="100%" align="center"><span id="transactionSpan">不要轻易借钱给别人</span></td>
            </tr>
            <tr>
                <td colspan="2" width="100%" align="center"><input
                        id="transactionBtn" type="button" value="开始转账"></td>
            </tr>
        </table>
    </form>
    <br><br>
    <form id="selectTradeForm">
        <input type="hidden" name="method_name" value="select_trade">
        <table border="1" bordercolor="gold" cellspacing="0" width="50%">
            <tr>
                <td colspan="4" align="center">交易记录查询</td>
            </tr>
            <tr>
                <td width="50%" align="center">开始时间</td>
                <td width="50%" align="left"><input type="text" id="startT"
                                                    name="startTime"></td>
            </tr>
            <tr>
                <td width="50%" align="center">结束时间</td>
                <td width="50%" align="left"><input type="text" id="endT"
                                                    name="endTime"></td>
            </tr>
            <tr>
                <td colspan="2" width="100%" align="center"><input
                        type="button" id="selectTradeBtn" value="查询交易明细"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div id="selectTradeDiv" align="center">只能查询距当前一个月的记录 日期格式为yyyy-MM-dd</div>
                </td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>
