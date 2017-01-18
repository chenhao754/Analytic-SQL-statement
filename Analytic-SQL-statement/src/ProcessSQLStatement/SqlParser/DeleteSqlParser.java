package ProcessSQLStatement.SqlParser;


import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment.SegmentRegExps;
import ProcessSQLStatement.SqlSegment.SqlSegment;
/**
 * Created by CH on 2016/10/25.
 */
public class DeleteSqlParser extends BaseSingleSqlParser {

	/**
	 * 　* 构造函数，传入原始Sql语句，进行劈分。
	 * 　* @param originalSql
	 *
	 *
	 * @param originalSql
	 * @param processedSql
	 */
	public DeleteSqlParser(String originalSql, String processedSql) {
		super(originalSql, processedSql);
	}

	@Override
	protected void initializeSegments() {
		//delete from .. where ..;
		for(String regExp : SegmentRegExps.deleteRegExps){//DeleteSqlParser的正则表达式初始化
			segments.add(new SqlSegment(regExp));
		}
	}
	@Override
	public void Deal(String start, String body, String end) {
		// TODO Auto-generated method stub
		if(this.sqlObjectObj != null){//判空
			//如果(start) (body) (end) = (delete from) (tablename) (where|;|ENDOFSQL)
			if(start.equals("delete from")){
				if(sqlObjectObj.typeName == null){
					sqlObjectObj.typeName = "delete";
				}
				if(sqlObjectObj.tableName == null){
					//tablename是等于body，但是body是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
					sqlObjectObj.tableName = getOriSql(originalSql,body);
				}
			}
			if(sqlObjectObj.condition != null){//判空
				//如果(start) (body) (end) = (where)(.+)(;|ENDOFSQL)
				if(start.equals("where")){
					if(sqlObjectObj.condition.get("where") == null){
						//where是等于body，但是where是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
						sqlObjectObj.condition.put("where", getOriSql(originalSql,body));
					}
				}
			}
			
		}
		
		
	}
}
