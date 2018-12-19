package alibaba.test;

import alibaba.dao.AccountDao;
import alibaba.dao.Impl.AccountDaoImpl;
import alibaba.dao.Impl.TradeDaoImpl;
import alibaba.dao.TradeDao;
import alibaba.domain.Account;
import alibaba.domain.Person;
import alibaba.domain.Trade;
import alibaba.utils.JDBCUtils;
import alibaba.utils.MyDataUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.omg.CORBA.PERSIST_STORE;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestMain {
    @Test
    public void testString() {
        /**
         * 将 Array 解析成 Json 串
         */
        String[] str = {"Jack", "Tom", "90", "true"};
        JSONArray json = JSONArray.fromObject(str);
        System.err.println(json);
    }

    @Test
    public void testArr() {
        /**
         * 对像数组，注意数字和布而值
         */
        Object[] o = {"北京", "上海", 89, true, 90.87};
        JSONArray json = JSONArray.fromObject(o);
        System.err.println(json);
    }

    @Test
    public void testList() {
        /**
         * 使用集合类
         */
        List<String> list = new ArrayList<String>();
        list.add("Jack");
        list.add("Rose");
        JSONArray json = JSONArray.fromObject(list);
        System.err.println(json);
    }

    @Test
    public void testSet() {
        /**
         * 使用 set 集
         */
        Set<Object> set = new HashSet<Object>();
        set.add("Hello");
        set.add(true);
        set.add(99);
        JSONArray json = JSONArray.fromObject(set);
        System.err.println(json);
    }

    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Tom");
        map.put("age", 33);
        JSONObject jsonObject = JSONObject.fromObject(map);
        System.out.println(jsonObject);

    }

    /**
     * 测试JavaBean转为Json
     */
    @Test
    public void testJava() {
        /**
         * 解析 JavaBean
         */
        Person person = new Person("A001", "Jack");
        JSONObject jsonObject = JSONObject.fromObject(person);
        System.out.println(jsonObject);

        /**
         * 解析嵌套的对象
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("person", person);
        JSONObject jsonObject1 = JSONObject.fromObject(map);
        System.out.println(jsonObject1);
    }

    /**
     * 测试过滤属性
     */
    @Test
    public void testConfig() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"name"});
        Person person = new Person("maozedong", "123456");
        JSONObject object = JSONObject.fromObject(person, jsonConfig);
        System.out.println(object);
    }

    /**
     * 在将 Json 形式的字符串转换为 JavaBean
     * 的时候需要注意 JavaBean 中必须有无参构造函数，否则会报如下找不到初始化方法的错误：
     */
    @Test
    public void testJsonToJavaBean() {
        /**
         * 将 Json 形式的字符串转换为 JavaBean
         */
        String str = "{\"name\":\"A001\",\"passsword\":\"Jack\"}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        System.out.println(jsonObject);
        Person person = (Person) JSONObject.toBean(jsonObject, Person.class);
        System.out.println(person);
    }

    @Test
    public void testConnection(){
        DataSource dateSource = JDBCUtils.getDateSource();
        System.out.println(dateSource);
    }

    @Test
    public  void testUpdateRemaining() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.updateRemaining(connection,"4000","admin");
    }

    /**
     * 测试日期格式的类
     */
    @Test
    public void testDataUtils(){
        Calendar calendar = MyDataUtils.dateToCalendar(new Date());
        System.out.println(calendar);
    }

    /**
     * 获取指定日期格式
     */
    @Test
    public void testGetFormat(){
        String result = MyDataUtils.getyyyy_MM_dd(new Date());
        System.out.println(result);
    }

    /**
     * 修改天数
     */
    @Test
    public void testaddDay(){
        Date date = MyDataUtils.addDayToDate(new Date(), 10);
        String mmDd = MyDataUtils.getyyyy_MM_dd(date);
        System.out.println(mmDd);
    }

    /**
     * 获取两个日期之间的差值
     */
   @Test
    public void testDiffernt(){
       String date ="2018-12-18";
       String year = "2019-12-18";
       Date date1 = MyDataUtils.getDateFromString(date);
       Date date2 = MyDataUtils.getDateFromString(year);
       long days = MyDataUtils.getDiffDays(date2, date1);
       System.out.println("两个日期之间天数:"+days);
   }

    /**
     * 获取每个月有多少天
     */
   @Test
    public void testMonthDay(){
       String str = "2020-02-12";
       Date date = MyDataUtils.getDateFromString(str);
       int days = MyDataUtils.getDaysOfMonth(date);
       System.out.println(days);
   }

    /**
     * 判断日期是否是当天
     */
   @Test
    public void testSameDay(){
       boolean day = MyDataUtils.isSameDay("2018-12-17");
       if (day){
           System.out.println("相同一天");
       }
   }
   @Test
    public void testMaxOrMin(){
       String str = "2018-12-08";
       String str1="2018-12-18";
       MyDataUtils.compareDate(str,str1);
   }
   @Test
    public void testTrade() throws SQLException {
       TradeDao tradeDao = new TradeDaoImpl();
       List<Trade> list = tradeDao.selectTrade("root", "2018-12-17", "2018-12-19");
       System.out.println(list);
   }
}
