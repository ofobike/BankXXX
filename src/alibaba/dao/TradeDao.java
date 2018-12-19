package alibaba.dao;

import alibaba.domain.Trade;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface TradeDao {
    /**
     * 添加一条记录到日志表中
     * 添加功能
     * @param conn
     * @param _accountid
     * @param tradetype
     * @param trademoney
     * @param date
     * @param tradedigest
     * @throws SQLException
     */
    public void addTrade(Connection conn, String _accountid, String tradetype, String trademoney, Date date, String tradedigest) throws SQLException;

    /**
     * 根据accountid查询交易记录
     * @param accountid
     * @param startTime
     * @param endTime
     * @return
     */
    List<Trade> selectTrade(String accountid, String startTime, String endTime) throws SQLException;
}
