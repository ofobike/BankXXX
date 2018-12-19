package alibaba.dao;

import alibaba.domain.Account;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 账户查询的借口
 */
public interface AccountDao {
    /**
     * 根据用户名和密码登录
     * @param account
     * @return Account
     */
    Account LoginUser(Account account) throws SQLException;

    /**
     * 根据账户查询余额
     * @param accountid
     * @return
     */
    Double selectRemaining(String accountid) throws SQLException;

    /**
     * 用户存钱的操作
     *
     */
    public void updateRemaining(Connection conn, String accountid, String money) throws SQLException;

    /**
     * 根据账户id查询账户存在不
     * @param toaccount
     * @return
     */
    Account selectUser(String toaccount) throws SQLException;
}
