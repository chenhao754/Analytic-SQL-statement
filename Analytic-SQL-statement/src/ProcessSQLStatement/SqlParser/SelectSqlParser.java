package ProcessSQLStatement.SqlParser;

import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment.SegmentRegExps;
import ProcessSQLStatement.SqlSegment.SqlSegment;
/**
 * Created by CH on 2016/10/25.
 */
public class SelectSqlParser extends BaseSingleSqlParser {

	/**
	 * 　* 构造函数，传入原始Sql语句，进行劈分。
	 * 　* @param originalSql
	 *
	 *
	 * @param originalSql
	 * @param processedSql
	 */
	public SelectSqlParser(String originalSql, String processedSql) {
		super(originalSql, processedSql);
	}

	@Override
	protected void initializeSegments() {
		//select .. from .. where .. group by .. having .. order by..limit..
		//中间不隔关键词
		for(String regExp : SegmentRegExps.selectRegExps){//selectSqlParser的正则表达式初始化
			segments.add(new SqlSegment(regExp));
		}
	}
	@Override
	public void Deal(String start, String body, String end) {
		// TODO Auto-generated method stub
		//如果start属于typeName
		if(this.sqlObjectObj != null){	//判空
				if(start.contains("select")){
					if(sqlObjectObj.typeName == null){
						sqlObjectObj.typeName = "select";
					}
					if(sqlObjectObj.field == null){
						sqlObjectObj.field = getOriSql(originalSql,body);
					}
				}
				if(start.equals("from")){
					if(sqlObjectObj.tableName == null){
						sqlObjectObj.tableName = getOriSql(originalSql,body);
					}
				}
				if(sqlObjectObj.condition != null){//判空
					if(start.equals("where")){
						if(sqlObjectObj.condition.get("where") == null){
							sqlObjectObj.condition.put("where", getOriSql(originalSql,body));
						}
					}
					if(start.equals("group by")){
						if(sqlObjectObj.condition.get("group by") == null){
							sqlObjectObj.condition.put("group by", getOriSql(originalSql,body));
						}
					}
					if(start.equals("having")){
						if(sqlObjectObj.condition.get("having") == null){
							sqlObjectObj.condition.put("having", getOriSql(originalSql,body));
						}
					}
					if(start.equals("order by")){
						if(sqlObjectObj.condition.get("order by") == null){
							sqlObjectObj.condition.put("order by", getOriSql(originalSql,body));
						}
					}
					if(start.equals("limit")){
						if(sqlObjectObj.condition.get("limit") == null){
							sqlObjectObj.condition.put("limit", getOriSql(originalSql,body));
						}
					}
				}
		}
	}
}
