package ProcessSQLStatement.SqlSegment;


import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by CH on 2016/10/25.
 */
public class SqlSegment {

	/** *//**
	　* 表示片段的正则表达式
	　*/
	private String segmentRegExp;
	/** *//**
	　* 分割后的Body小片段
	　*/
	private List<String> bodyPieces;
	/** *//**
	　* 构造函数
	　* @param segmentRegExp 表示这个Sql片段的正则表达式
	　* @param bodySplitPattern 用于分割body的正则表达式
	　*/
	public SqlSegment(String segmentRegExp){
	    this.segmentRegExp=segmentRegExp;
	}
	/** *//**
	　* 从sql中查找符合segmentRegExp的部分，并赋值到start,body,end等三个属性中
	　* @param sql
	　*/
	public void parse(String sql,BaseSingleSqlParser baseSingleSqlParser){
	    Pattern pattern=Pattern.compile(segmentRegExp,Pattern.CASE_INSENSITIVE);
	    for(int i=0;i<=sql.length();i++)
	    {
	     String shortSql=sql.substring(0, i);
	     //测试输出的子句是否正确
	     //System.out.println(shortSql);
	     Matcher matcher=pattern.matcher(shortSql);
	     while(matcher.find())
	     {
	         String start = matcher.group(1);	//SQL正则表达式分片头部分
	         String body = matcher.group(2);	//SQL正则表达式分片中间部分
	         String end = matcher.group(3);		//SQL正则表达式分片尾部分
	         baseSingleSqlParser.Deal(start, body, end);	//处理SQL正则表达式分片后的结构
	         return;
	     }
	    }
	}
}
