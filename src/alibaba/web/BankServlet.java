package alibaba.web;

import alibaba.domain.Account;
import alibaba.domain.Result;
import alibaba.domain.Trade;
import alibaba.service.BanKService;
import alibaba.service.Impl.BankServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.ir.BaseNode;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.AccessControlContext;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/bankServlet")
public class BankServlet extends HttpServlet {
    // 记录用户登陆状态
    private Account _account = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //先判断用户的登录状态
        _account = (Account) request.getSession().getAttribute("accountid");


        //接受请求表单传过来的方法名
        String _name = request.getParameter("method_name");
        if ("_login".equalsIgnoreCase(_name)) {
            //调用用户登录的方法
            userLogin(request, response);
        } else if ("select_remaining".equalsIgnoreCase(_name)) {
            //用户登录成功后查询余额的方法
            selectRemaining(request, response);
        } else if ("save_money".equalsIgnoreCase(_name)) {
            saveMoney(request, response);
        } else if ("take_money".equalsIgnoreCase(_name)) {
            takeMoney(request, response);
        } else if ("accuont_transaction".equalsIgnoreCase(_name)) {
            accountTransaction(request, response);
        } else if ("select_trade".equalsIgnoreCase(_name)) {
            selectTrade(request, response);
        }
    }

    /**
     * 交易记录查询
     *
     * @param request
     * @param response
     */
    private void selectTrade(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (_account==null){
                response.getWriter().print("没有登陆不能查询啊");
                return;
            }
            //判断用户输入的日期
            // 接收参数
            String _startTime = request.getParameter("startTime");
            String _endTime = request.getParameter("endTime");
            if ("".equals(_startTime)||"".equals(_endTime)){
                //封装数据
                Result result = new Result();
                result.setCode(1024);
                result.setMsg("查询日期不能为空");
                JSONObject jsonObject = JSONObject.fromObject(result);
                response.getWriter().print(jsonObject);
                return;
            }
            //判断日期格式是否正确
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date _startdate = sdf.parse(_startTime);
            Date _enddate = sdf.parse(_endTime);
            //获取当前的事件日期
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            instance.add(Calendar.MONTH,-1);
            //得到当前的日期
            Date _ccdate = instance.getTime();
            //结束日期应该大于当前的日期
            if (_enddate.before(_startdate)){
                Result result = new Result();
                result.setCode(1024);
                result.setMsg("开始日期不能大于结束日期");
                JSONObject jsonObject = JSONObject.fromObject(result);
                response.getWriter().print(jsonObject.toString());
                return;
            }
            // 查询日期不能超过一个月
            if (_ccdate.after(_enddate)){
                Result result = new Result();
                result.setCode(1024);
                result.setMsg("只能查询近一个月的记录噢");
                JSONObject jsonObject = JSONObject.fromObject(result);
                response.getWriter().print(jsonObject.toString());
                return;
            }
            // 查询日期不能超过当前日期
            if (new Date().before(_enddate)) {
                Result result = new Result();
                result.setCode(1024);
                result.setMsg("查询日期不能超过当前日期噢");
                JSONObject jsonObject = JSONObject.fromObject(result);
                response.getWriter().print(jsonObject.toString());
                return;
            }
            //调用业务层的方法
            BanKService  banKService = new BankServiceImpl();
            List<Trade> _tTrades = banKService.selectTrade(_account.getAccountid(), _startTime, _endTime);
            Result result = new Result();
            result.setCode(999);
            result.setMsg("查询成功");
            result.setList(_tTrades);
            JSONObject jsonObject = JSONObject.fromObject(result);
            response.getWriter().print(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户转账的操作
     *
     * @param request
     * @param response
     */
    private void accountTransaction(HttpServletRequest request, HttpServletResponse response) {
        try {
            //判断用户存在才能转账
            if (_account == null) {
                response.getWriter().print("m没有登录不能转账");
                return;
            }
            //判断接受的参数
            String _toaccount = request.getParameter("to_account");
            String _tmoney = request.getParameter("t_money");
            if ("".equals(_toaccount) || "".equals(_tmoney)) {
                response.getWriter().print("参数不能为空");
                return;
            }
            //调用业务逻辑方法
            BanKService _bankService = new BankServiceImpl();
            String _resultString = _bankService.accountTransaction(_account.getAccountid(), _toaccount, _tmoney);
            response.getWriter().println(_resultString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户取钱功能
     *
     * @param request
     * @param response
     */
    private void takeMoney(HttpServletRequest request, HttpServletResponse response) {
        try {
            //判断用户的状态
            if (_account == null) {
                response.getWriter().print("没有登录不不能取钱呀");
                //返回终止这次操作
                return;
            }
            String _takemoney = request.getParameter("take_money");
            if ("".equals(_takemoney) || _takemoney == null) {
                response.getWriter().print("请输入要取的金额!");
                //返回终止这次操作
                return;
            }
            //调用业务层的方法
            BanKService _banKService = new BankServiceImpl();
            String _resultString = _banKService.takemoney(_account.getAccountid(), _takemoney);
            response.getWriter().println(_resultString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户存钱的方法
     *
     * @param request
     * @param response
     */
    private void saveMoney(HttpServletRequest request, HttpServletResponse response) {
        try {
            //判断用户的状态
            if (_account == null) {
                response.getWriter().print("没有登录不能存钱呀");
                //返回终止这次操作
                return;
            }
            String _savemoney = request.getParameter("save_money");
            if ("".equals(_savemoney) || _savemoney == null) {
                response.getWriter().print("请输入要存入的金额!");
                //返回终止这次操作
                return;
            }
            //调用业务层的方法
            BanKService _banKService = new BankServiceImpl();
            //调用存钱的方法
            String _result = _banKService.savemoney(_account.getAccountid(), _savemoney);
            response.getWriter().print(_result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录成功后查询余额的方法
     *
     * @param request
     * @param response
     */
    private void selectRemaining(HttpServletRequest request, HttpServletResponse response) {
        try {
            //用户只有登录之后才能查询余额没有登录不行
            Account _account = (Account) request.getSession().getAttribute("accountid");
            if (_account == null) {
                //如果用户没有登录提示用户登录
                response.getWriter().write("还没有登录查询不了余额");
            } else {
                //用户已经登录
                BanKService banKService = new BankServiceImpl();
                //获取账户的余额
                Double _remaining = banKService.selectRemaining(_account.getAccountid());
                //返回前台数据
                response.getWriter().print(_remaining);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录的方法
     *
     * @param request
     * @param response
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 这里异常不能再抛出应该try catch
         */
        try {
            //接受参数
            String _username = request.getParameter("username");
            String _password = request.getParameter("password");
            //封装参数
            Account account = new Account();
            account.setAccountid(_username);
            account.setPassword(_password);
            //调用业务层的登录的方法
            BanKService banKService = new BankServiceImpl();
            Account user = banKService.LoginUser(account);
            //登录成功页面跳转
            if (user != null) {
                //把用户信息保存再Session
                request.getSession().setAttribute("accountid", user);
                //登录成功重定向到主页面
                response.sendRedirect(request.getContextPath() + "/home.jsp");
            } else {
                //登录失败转发到登录页面
                request.setAttribute("msg", "用户名和密码错误");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
