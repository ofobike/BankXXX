package alibaba.service;

import alibaba.domain.Account;
import alibaba.domain.Trade;

import java.sql.SQLException;
import java.util.List;

public interface BanKService {
    /**
     * 用户登录
     *
     * @param account
     * @return
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
     * @param accountid
     * @param savemoney
     * @return
     */
    String savemoney(String accountid, String savemoney) throws SQLException;

    /**
     * 用户取钱的操作
     * @param accountid
     * @param takemoney
     * @return
     */
    String takemoney(String accountid, String takemoney);

    /**
     * 账户之间的转账操作
     * @param accountid
     * @param toaccount
     * @param tmoney
     * @return
     */
    String accountTransaction(String accountid, String toaccount, String tmoney);

    /**
     * 根据Accountid查询交易记录
     * @param accountid
     * @param startTime
     * @param endTime
     * @return
     */
    List<Trade> selectTrade(String accountid, String startTime, String endTime) throws SQLException;
}
