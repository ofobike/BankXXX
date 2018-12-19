package alibaba.dao.Impl;

import alibaba.dao.TradeDao;
import alibaba.domain.Trade;
import alibaba.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TradeDaoImpl implements TradeDao {
    private QueryRunner qr = new QueryRunner(JDBCUtils.getDateSource());
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
    @Override
    public void addTrade(Connection conn, String _accountid, String tradetype, String trademoney, Date date, String tradedigest) throws SQLException {
        String sql = "INSERT INTO trade VALUES (NULL,?,?,?,?,?)";
        qr.update(conn, sql, _accountid,tradetype,trademoney,date,tradedigest);
    }

    @Override
    public List<Trade> selectTrade(String accountid, String startTime, String endTime) throws SQLException {
        String sqlString = "SELECT * FROM trade WHERE accountID = ? AND  tradetime BETWEEN ? AND ?";
        List<Trade> query = qr.query(sqlString, new BeanListHandler<Trade>(Trade.class), accountid, startTime + " 00:00:00", endTime + " 23:59:59");
        return query;
    }
}
