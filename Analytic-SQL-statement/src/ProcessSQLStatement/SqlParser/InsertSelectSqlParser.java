package ProcessSQLStatement.SqlParser;


import ProcessSQLStatement.SingleSqlParserFactory;
import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment.SegmentRegExps;
import ProcessSQLStatement.SqlSegment.SqlSegment;
/**
 * Created by CH on 2016/10/25.
 */
public class InsertSelectSqlParser extends BaseSingleSqlParser {

	/**
	 * 　* 构造函数，传入原始Sql语句，进行劈分。
	 * 　* @param originalSql
	 *
	 *
	 * @param originalSql
	 * @param processedSql
	 */
	public InsertSelectSqlParser(String originalSql, String processedSql) {
		super(originalSql, processedSql);
	}

	@Override
	protected void initializeSegments() {
		//insert into .. select ..;
		//INSERT INTO Store_Information (store_name, Sales, Date) SELECT store_name, Sales, Date FROM Sales_Information WHERE Year(Date) = 1998  ";
		for(String regExp : SegmentRegExps.insertSelectRegExps){//InsertSelectSqlParser的正则表达式初始化
			segments.add(new SqlSegment(regExp));
		}
	}
	
	@Override
	public void Deal(String start, String body, String end) {
		// TODO Auto-generated method stub
		if (sqlObjectObj != null) {
			//如果(start) (body) (end) = (insert into)(.+)(select)
			if (start.contains("insert into") && end.contains("select")) {
				//tablename+(field1,field2,...)是等于body，但是body是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
				body = body.trim();
				if(sqlObjectObj.typeName == null){
					sqlObjectObj.typeName = "insert";
				}
				if (sqlObjectObj.tableName == null) {
					//通过空格分隔body，获得表名
					sqlObjectObj.tableName = getOriSql(originalSql,body.split("\\(")[0]);
				}
				if(sqlObjectObj.field == null){
					//通过空格分隔body，获得字段名
					String fields = body.split("\\(")[1];
					fields = fields.replaceAll("\\)","");
					sqlObjectObj.field = getOriSql(originalSql,fields);;
				}
				//去掉原sql语句上的insert into
				processedSql = processedSql.replaceAll("insert into","");
				if(sqlObjectObj.next != null){
					//再次分析时，从select开始，形成的SQL对象放入原对象的sqlObj.next中
					sqlObjectObj.next.add(SingleSqlParserFactory.generateParser(originalSql,processedSql).getDealResult());
				}
			}
		}
	}
	
}
