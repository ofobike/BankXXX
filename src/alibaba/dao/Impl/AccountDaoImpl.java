package alibaba.dao.Impl;

import alibaba.dao.AccountDao;
import alibaba.domain.Account;
import alibaba.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 操作用户表的Dao实现类
 */
public class AccountDaoImpl implements AccountDao {
    private QueryRunner qr = new QueryRunner(JDBCUtils.getDateSource());

    /**
     * 根据用户名和密码查询用户
     *
     * @param account
     * @return
     * @throws Exception
     */
    @Override
    public Account LoginUser(Account account) throws SQLException {
        String sql = "select * from account where accountid =? and password =?";
        return qr.query(sql, new BeanHandler<Account>(Account.class), account.getAccountid(), account.getPassword());

    }

    /**
     * 根据账户查询余额
     *
     * @param accountid
     * @return
     */
    @Override
    public Double selectRemaining(String accountid) throws NumberFormatException, SQLException {
        String sql = "select remaining from account where accountid = ?";
        Object object = qr.query(sql, new ScalarHandler(), accountid);
        //强制转换
        return Double.parseDouble(object.toString());

    }

    /**
     * 根据用户id修改用户余额
     *
     * @param conn
     * @param accountid
     * @param money
     * @throws SQLException
     */
    @Override
    public void updateRemaining(Connection conn, String accountid, String money) throws SQLException {
        String sql = "update account set remaining = remaining + ? where accountid = ?";
        qr.update(conn, sql, money, accountid);
    }

    /**
     * 根据账户的id查询账户是否存在
     *
     * @param toaccount
     * @return
     */
    @Override
    public Account selectUser(String toaccount) throws SQLException {
        String sql = "select * from account where accountid = ?";
        return qr.query(sql, new BeanHandler<Account>(Account.class), toaccount);
    }


}
