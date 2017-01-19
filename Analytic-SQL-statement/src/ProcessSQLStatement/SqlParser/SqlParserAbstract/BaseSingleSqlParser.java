package ProcessSQLStatement.SqlParser.SqlParserAbstract;


import ProcessSQLStatement.SQLObject;
import ProcessSQLStatement.SqlSegment.SqlSegment;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by CH on 2016/10/25.
 */
public abstract class BaseSingleSqlParser {
	protected SQLObject sqlObjectObj = new SQLObject();
	/** *//**
	 　* 处理后的Sql语句
	 　*/
	protected  String processedSql;
	/** *//**
	　* 原始Sql语句
	　*/
	protected String originalSql;

	/**
	　* Sql语句片段

	　*/
	protected List<SqlSegment> segments =new ArrayList<SqlSegment>();
	/** *//**
	　* 构造函数，传入原始Sql语句，进行劈分。
	　* @param originalSql
	　*/
	public BaseSingleSqlParser(String originalSql,String processedSql){
		this.processedSql = processedSql;
	    this.originalSql=originalSql;
	    initializeSegments();
	    //splitSql2Segment();
	}
	/** *//**
	　* 初始化segments，强制子类实现，segments是用来划分sql语句的正则表达式类的对象的数组
	　*
	　*/
	protected abstract void initializeSegments();
	/** *//**
	　* 将originalSql劈分成一个个片段
	　*
	　*/
	public SQLObject getDealResult() {
	    //每个不同的SqlParser都有自己的segements，用来将sql语句分片，利用正则表达式
		for(SqlSegment sqlSegment:segments) {
	    	//每个sqlSegment就是一个正则表达式，该函数就是将所需要处理的部分划分为三片，传入this是为了能够在SqlSegment.parse函数中处理每个不同的SqlParser的Deal函数
	        sqlSegment.parse(processedSql,this);
	    }
		return sqlObjectObj;
	}
	/*
	* 返回原本的sql语句，而不是小写后的
	* Str：未处理前的整条sql语句
	* subStr：处理后的整条sql语句中截取的部分
	* */
	public static String getOriSql(String Str,String subStr){
		String StrLower = Str.toLowerCase();
		for(int i = 0; i<StrLower.length(); i++){
			int StrIndex = i;
			int SubStrIndex = 0;
			for(; SubStrIndex<subStr.length();){
				if(StrLower.charAt(StrIndex++) != subStr.charAt(SubStrIndex++)){
					SubStrIndex = subStr.length()+1;
					break;
				}
			}
			if(SubStrIndex == subStr.length()){
				return Str.substring(i,StrIndex);
			}
		}
		return subStr;
	}
	/*
	 * 处理函数
	 * */
	public abstract void Deal(String start,String body,String end);
}
