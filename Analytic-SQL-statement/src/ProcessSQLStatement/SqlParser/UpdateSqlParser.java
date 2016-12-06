package ProcessSQLStatement.SqlParser;

import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment;
/**
 * Created by CH on 2016/10/25.
 */
public class UpdateSqlParser extends BaseSingleSqlParser {

	/**
	 * 　* 构造函数，传入原始Sql语句，进行劈分。
	 * 　* @param originalSql
	 *
	 *
	 * @param originalSql
	 * @param processedSql
	 */
	public UpdateSqlParser(String originalSql, String processedSql) {
		super(originalSql, processedSql);
	}

	@Override
	protected void initializeSegments() {
		//update ... set ... where ... ;
	    segments.add(new SqlSegment("(update)(.+)(set)"));
	    segments.add(new SqlSegment("(set)(.+)(where|;|ENDOFSQL)"));
	    segments.add(new SqlSegment("(where)(.+)(;|ENDOFSQL)"));
	}
	@Override
	public void Deal(String start, String body, String end) {
		// TODO Auto-generated method stub
		if(this.sqlObjectObj != null){//判空
			if(start.equals("update")){
				if(sqlObjectObj.typeName == null){
					sqlObjectObj.typeName = "update";
				}
				if(sqlObjectObj.tableName == null){
					sqlObjectObj.tableName = getOriSql(originalSql,body);
				}
			}
			if(start.equals("set")){
				body = body.replaceAll("=",",");
				String temp[] = body.split(",");
				sqlObjectObj.field = "";
				for(int i = 0; i<temp.length; i++){
					if(i%2 == 0){
						sqlObjectObj.field += getOriSql(originalSql,temp[i]) + ",";
					}
				}
				sqlObjectObj.field = sqlObjectObj.field.substring(0, sqlObjectObj.field.length()-1);
			}
			if(start.equals("where")){
				if(sqlObjectObj.condition != null){//判空
					if(sqlObjectObj.condition.get("where") == null){
						sqlObjectObj.condition.put("where", getOriSql(originalSql,body));
					}
				}
			}
		}
	}
}
