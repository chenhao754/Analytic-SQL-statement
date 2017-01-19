package ProcessSQLStatement;


import ProcessSQLStatement.SQLObject.SQLObject;
import ProcessSQLStatement.SqlParserBefore.SqlParserUtil;

/**
 * Created by CH on 2016/10/25.
 */
public class Test {
	/** *//**
	    * Sql解析器
	    * @author CH
	    *
	    * @since 2016/10/25
	    * @version 1.00
	    */
	    public static void main(String[] args) {
	        // TODO Auto-generated method stub
	       //String test="select  a from  b " +
	           //    "\n"+"where      a=b";
	       //test=test.replaceAll("\\s{1,}", " ");
	       //System.out.println(test);
	       //程序的入口
			String testSql= "select a.title,a.username,b.adddate from table a,(select max(adddate) adddate from table where table.title=a.title) b";	//需要解析的sql语句
			String a1 = "SELECT Company, OrderNumber FROM Orders ORDER BY Company DESC";
			SqlParserUtil test=new SqlParserUtil();	//SQL语句解析工具类
	        SQLObject sqlObjectObj = test.getParsedSql(a1);	//得到解析SQL语句后的类型对象
	        sqlObjectObj.PrintToString();	//打印出来
		 }

}
