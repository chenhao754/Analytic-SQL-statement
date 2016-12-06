package ProcessSQLStatement;


import ProcessSQLStatement.SqlParserBefore.Layer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by CH on 2016/10/25.
 */
public class SqlParserUtil {
	/** *//**
   　* 方法的主要入口
   　* @param sql:要解析的sql语句
   　* @return 返回解析结果
   　*/
   public SQLObject getParsedSql(String sql){
       sql=sql.trim();
       sql=sql.replaceAll("\\s{1,}", " ");
       sql = sql.replace(" view "," table ");   //不存在View，都改为table处理
       sql = sql.replaceAll("\\s*,\\s* ",",");   //去掉逗号前后的空格
       sql = sql.replaceAll("\\s*\\)\\s* ",") ");   //去掉)前后的空格
       sql = sql.replaceAll("\\s*\\(\\s* "," (");   //去掉(前后的空格
       sql = sql.replaceAll("\\s*=\\s* ","=");   //去掉=前后的空格

       //SQL语句分层处理，即两层嵌套语句就为两层
       return new Layer().nesting(sql+"ENDOFSQL",sql.toLowerCase()+"ENDOFSQL");
       //return SingleSqlParserFactory.generateParser(sql).getSQLObj();
   }
    /** *//**
     　* 看word是否在lineText中存在，支持正则表达式
     　* @param sql:要解析的sql语句
     　* @param regExp:正则表达式
     　* @return
     　*/
    public static boolean contains(String sql,String regExp){
        Pattern pattern= Pattern.compile(regExp,Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(sql);
        return matcher.find();
    }
}