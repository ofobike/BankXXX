package alibaba.service.Impl;

import alibaba.dao.AccountDao;
import alibaba.dao.Impl.AccountDaoImpl;
import alibaba.dao.Impl.TradeDaoImpl;
import alibaba.dao.TradeDao;
import alibaba.domain.Account;
import alibaba.domain.Trade;
import alibaba.service.BanKService;
import alibaba.utils.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.omg.PortableInterceptor.ACTIVE;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class BankServiceImpl implements BanKService {
    private AccountDao accountDao = new AccountDaoImpl();
    private TradeDao tradeDao = new TradeDaoImpl();

    /**
     * 用户登录
     *
     * @param account
     * @return
     */
    @Override
    public Account LoginUser(Account account) throws SQLException {
        return accountDao.LoginUser(account);
    }

    /**
     * 根据账户查询余额
     *
     * @param accountid
     * @return
     */
    @Override
    public Double selectRemaining(String accountid) throws SQLException {
        return accountDao.selectRemaining(accountid);
    }

    /**
     * 用户存钱的操作
     * 有事物的操作
     *
     * @param accountid
     * @param savemoney
     * @return
     */
    @Override
    public String savemoney(String accountid, String savemoney) {
        Connection conn = JDBCUtils.getConnection();
        try {
            // 开启事务
            conn.setAutoCommit(false);
            // 存钱
            accountDao.updateRemaining(conn, accountid, savemoney);
            // 0 存款 1 取款 2 转账
            // 添加记录
            tradeDao.addTrade(conn, accountid, "0", savemoney, new Date(), "自己辛苦挣的血汗钱 " + savemoney);
            // 提交事务
            DbUtils.commitAndCloseQuietly(conn);
        } catch (Exception e) {
            // 回滚事务
            DbUtils.rollbackAndCloseQuietly(conn);
            e.printStackTrace();
            return "银行系统出Bug了 -- 钱没有存上";
        }
        return "钱已经存上了 -- 账户上又多了  " + savemoney + "  元";
    }

    /**
     * 用户取钱操作
     *
     * @param accountid
     * @param takemoney
     * @return
     */
    @Override
    public String takemoney(String accountid, String takemoney) {
        Connection connection = JDBCUtils.getConnection();
        Double _remaining = 0d;
        try {
            //开启事物
            connection.setAutoCommit(false);
            //查询账户是否有钱
            _remaining = accountDao.selectRemaining(accountid);
            //判断账户钱是否足够
            if (_remaining <= Double.parseDouble(takemoney)) {
                //返回账户上的余额
                return "哈哈又是月光族" + String.valueOf(_remaining);

            }
            //取钱的方法
            accountDao.updateRemaining(connection, accountid, "-" + takemoney);
            // 添加记录
            tradeDao.addTrade(connection, accountid, "1", takemoney, new Date(), "取钱养活女朋友 " + takemoney);
            // 提交事务
            DbUtils.commitAndCloseQuietly(connection);
        } catch (Exception e) {
            // 回滚事务
            DbUtils.rollbackAndCloseQuietly(connection);
            e.printStackTrace();
            return "银行系统出Bug了 -- 钱没有取出来";
        }

        return "钱已经取出来了 账户上又少了  " + takemoney + "  元  余额是   " + (_remaining - Double.parseDouble(takemoney));

    }

    /**
     * 转账之间的业务逻辑操作
     *
     * @param accountid 转账人
     * @param toaccount 收款人
     * @param tmoney    钱
     * @return
     */
    @Override
    public String accountTransaction(String accountid, String toaccount, String tmoney) {
        Connection conn = JDBCUtils.getConnection();
        Double _serviceCharge = 0d;
        Double _remainning = 0d;
        try {
            //不能自己给自己转钱
            if (accountid.equals(toaccount)){
                return "OMG!为啥给自己转钱！";
            }
            //判断转账的用户存在不
            Account _account = accountDao.selectUser(toaccount);
            if (_account==null){
                return "转账的账户不存在!";
            }
            //查询账户的余额
            _remainning = accountDao.selectRemaining(accountid);
            //计算手续费
            _serviceCharge = Double.parseDouble(tmoney)*0.005;
            //转账的金额不能小于2元
            if (Double.parseDouble(tmoney)<2){
                _serviceCharge=2.00;
            }else if (Double.parseDouble(tmoney)>20){
                _serviceCharge=20.00;
            }
            if (_remainning < (Double.parseDouble(tmoney)+_serviceCharge)) {
                return "余额不足,无法完成转账";
            }
            //开启事物
            conn.setAutoCommit(false);
            //给转账人扣钱 手续费加扣取的费用
            accountDao.updateRemaining(conn,accountid,"-"+(Double.parseDouble(tmoney)+_serviceCharge));
            //给收款人加钱哥
            accountDao.updateRemaining(conn,toaccount,tmoney);
            //给转账添加记录
            tradeDao.addTrade(conn,accountid,"2",tmoney,new Date(),"转账给-->"+toaccount+"手续费是:"+String.valueOf(_serviceCharge));
            //给收款人添加记录
            tradeDao.addTrade(conn,toaccount,"2",tmoney,new Date(),"收到-->"+accountid+"账户的"+tmoney);
            //提交事物
            DbUtils.commitAndCloseQuietly(conn);
        } catch (Exception e) {
            // 回滚事务
            DbUtils.rollbackAndCloseQuietly(conn);
            e.printStackTrace();
            return "银行系统出Bug了 -- 转账没有成功";
        }
        return "转账成功了-- 转账金额   "+tmoney+" 元 手续费  "+_serviceCharge+" 元   余额还剩下  "+(_remainning-_serviceCharge-Double.parseDouble(tmoney)+" 元");

    }

    /**
     * 查询交易记录
     * @param accountid
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<Trade> selectTrade(String accountid, String startTime, String endTime) throws SQLException {
        return tradeDao.selectTrade(accountid,startTime,endTime);
    }
}
