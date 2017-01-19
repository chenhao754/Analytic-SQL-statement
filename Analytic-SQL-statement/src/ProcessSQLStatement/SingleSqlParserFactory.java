package ProcessSQLStatement;

import ProcessSQLStatement.SqlParser.*;
import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by CH on 2016/10/25.
 */
public class SingleSqlParserFactory {
	public static BaseSingleSqlParser generateParser(String originalSql, String processedSql)
	{//区分sql语句类型
		if(SqlParserUtil.contains(processedSql,"union") || SqlParserUtil.contains(processedSql,"union all")){
			return new UnionSqlParser(originalSql,processedSql);
		}
		else if(SqlParserUtil.contains(processedSql,"(alter)")){
			return new AlterSqlParser(originalSql,processedSql);
		}
		else if(SqlParserUtil.contains(processedSql,"(drop table)(.+)(ENDOFSQL)")){
			return new DropSqlParser(originalSql,processedSql);
		}
		else if(SqlParserUtil.contains(processedSql,"(create table)(.+)(select)")){
			return new CreateSelectSqlParser(originalSql,processedSql);
		}
		else if(SqlParserUtil.contains(processedSql,"(insert into)(.+)(select)")){
			return new InsertSelectSqlParser(originalSql,processedSql);
		}
		else if(SqlParserUtil.contains(processedSql,"(create table)")){
			return new CreateSqlParser(originalSql,processedSql);
		}
		else if(SqlParserUtil.contains(processedSql,"(insert into)(.+)(select)(.+)(from)(.+)")) {
	        return new InsertSelectSqlParser(originalSql,processedSql);
	    }
	    else if(SqlParserUtil.contains(processedSql,"select")) {
	        return new SelectSqlParser(originalSql,processedSql);
	    }
	    else if(SqlParserUtil.contains(processedSql,"delete")) {
	        return new DeleteSqlParser(originalSql,processedSql);
	    }
	    else if(SqlParserUtil.contains(processedSql,"update")) {
	        return new UpdateSqlParser(originalSql,processedSql);
	    }
	    else if(SqlParserUtil.contains(processedSql,"(insert into)(.+)(values)(.+)")) {
	        return new InsertSqlParser(originalSql,processedSql);
	    }
	    //sql=sql.replaceAll("ENDSQL", "");
	    else
			return null;
	        //return new InsertSqlParser(originalSql,processedSql);
	       //throw new NoSqlParserException(sql.replaceAll("ENDOFSQL", ""));//对异常的抛出
	}

}
