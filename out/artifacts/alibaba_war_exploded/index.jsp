<%--
  Created by IntelliJ IDEA.
  User: ysj
  Date: 2018/12/16
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<center>
    <h1>欢迎登陆XXX网上银行</h1>
    <form action="/bankServlet" method="post">
        <input type="hidden" name="method_name" value="_login">
        <table border="1" bordercolor="gold" cellspacing="0" cellpadding="5" width="">
            <tr>
                <td>账号:</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td colspan=2 align="center">
                    <input type="submit" value=" 登 陆 "/>&nbsp;
                    <input type="reset" value=" 重 置 "/>
                </td>
            </tr>
        </table>
    </form>
    <br><br>
    <font color="red" size="5">${ msg }</font>
</center>
</body>
</html>
