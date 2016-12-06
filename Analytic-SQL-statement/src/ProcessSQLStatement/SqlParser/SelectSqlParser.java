package ProcessSQLStatement.SqlParser;

import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment;
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
		segments.add(new SqlSegment("(select distinct)(.+)(from)"));
	    segments.add(new SqlSegment("(select)(.+)(from)"));
	    segments.add(new SqlSegment("(from)(.+)(where)"));
	    segments.add(new SqlSegment("(where)(.+)(group by)"));
	    segments.add(new SqlSegment("(group by)(.+)(having)"));
	    segments.add(new SqlSegment("(having)(.+)(order by)"));
		segments.add(new SqlSegment("(order by)(.+)(limit)"));
	    //中间隔一个关键词
	    segments.add(new SqlSegment("(from)(.+)(group by)"));
	    segments.add(new SqlSegment("(where)(.+)(having)"));
	    segments.add(new SqlSegment("(group by)(.+)(order by)"));
		segments.add(new SqlSegment("(having)(.+)(limit)"));
	    //中间隔两个关键词
	    segments.add(new SqlSegment("(from)(.+)(having)"));
	    segments.add(new SqlSegment("(where)(.+)(order by)"));
		segments.add(new SqlSegment("(group by)(.+)(limit)"));
	    //中间隔三个关键词
	    segments.add(new SqlSegment("(from)(.+)(order by)"));
		segments.add(new SqlSegment("(where)(.+)(limit)"));
		//中间隔四个关键词
		segments.add(new SqlSegment("(from)(.+)(limit)"));
	    //结尾
		segments.add(new SqlSegment("(select)(.+)(;|ENDOFSQL)"));
		segments.add(new SqlSegment("(from)(.+)(;|ENDOFSQL)"));
		segments.add(new SqlSegment("(where)(.+)(;|ENDOFSQL)"));
	    segments.add(new SqlSegment("(order by)(.+)(;|ENDOFSQL)"));
	    segments.add(new SqlSegment("(having)(.+)(;|ENDOFSQL)"));
	    segments.add(new SqlSegment("(group by)(.+)(;|ENDOFSQL)"));
		segments.add(new SqlSegment("(limit)(.+)(;|ENDOFSQL)"));

	    /*
	    segments.add(new SqlSegment("(from)(.+)( where | on | having | group by | order by | ENDOFSQL)"));
	    segments.add(new SqlSegment("(where|on|having)(.+)( group by | order by | ENDOFSQL)"));
	    segments.add(new SqlSegment("(group by)(.+)( order by| ENDOFSQL)"));
	    segments.add(new SqlSegment("(order by)(.+)( ENDOFSQL)"));*/
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
