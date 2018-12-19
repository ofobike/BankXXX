package alibaba.domain;

/**
 * com.itheima.bank.domain
 * 日志表实体类
 */
public class Trade {
	//账号ID
	private String accountid;
	//操作类型   0:存款 1:取款 2:转账
	private String tradetype;
	//交易金额
	private String trademoney;
	// 交易时间
	private String tradetime;
	// 交易描述
	private String tradedigest;

	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getTradetype() {
		return tradetype;
	}
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public String getTrademoney() {
		return trademoney;
	}
	public void setTrademoney(String trademoney) {
		this.trademoney = trademoney;
	}
	public String getTradetime() {
		return tradetime;
	}
	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
	}
	public String getTradedigest() {
		return tradedigest;
	}
	public void setTradedigest(String tradedigest) {
		this.tradedigest = tradedigest;
	}

	@Override
	public String toString() {
		return "Trade{" +
				"accountid='" + accountid + '\'' +
				", tradetype='" + tradetype + '\'' +
				", trademoney='" + trademoney + '\'' +
				", tradetime='" + tradetime + '\'' +
				", tradedigest='" + tradedigest + '\'' +
				'}';
	}
}
