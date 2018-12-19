package alibaba.domain;

/**
 * com.itheima.bank.domain
 * 账户表实体类
 *
 */
public class Account {
    // 账号ID
    private String accountid;
    // 密码
    private String password;
    // 余额
    private String remaining;

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }
}
