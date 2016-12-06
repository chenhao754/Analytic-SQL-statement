package ProcessSQLStatement.SqlParser;

import ProcessSQLStatement.SQLObject;
import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment;


/**
 * Created by CH on 2016/10/25.
 */
public class CreateSqlParser extends BaseSingleSqlParser {

	/**
	 * 　* 构造函数，传入原始Sql语句，进行劈分。
	 * 　* @param originalSql
	 *
	 *
	 * @param originalSql
	 * @param processedSql
	 */
	public CreateSqlParser(String originalSql, String processedSql) {
		super(originalSql, processedSql);
	}

	@Override
	protected void initializeSegments() {
		// TODO Auto-generated method stub
		//create table .. like|(...)
		segments.add(new SqlSegment("(create table)(.+)(like)"));
		segments.add(new SqlSegment("(like)(.+)(;|ENDOFSQL)"));

		segments.add(new SqlSegment("(create table)(.+)([(])"));
	    segments.add(new SqlSegment("([(])(.+)(;|ENDOFSQL)"));
	}

	@Override
	public void Deal(String start, String body, String end) {
		// TODO Auto-generated method stub
		if(sqlObjectObj != null){
			//如果(start)(body)(end) = (create table) (tablename) (（|like)
			if(start.equals("create table")){
				if(sqlObjectObj.typeName == null){
					//typeName 可以肯定为create
					sqlObjectObj.typeName = "create";
				}
				if(sqlObjectObj.tableName == null){
					//tablename是等于body，但是body是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
					sqlObjectObj.tableName = getOriSql(originalSql,body);
				}
			}
			if(start.equals("like")){
				SQLObject sqlObject = new SQLObject();
				if(sqlObject.tableName == null) {
					sqlObject.tableName = getOriSql(originalSql,body);;
				}
				if(sqlObjectObj.next != null){
					sqlObjectObj.next.add(sqlObject);
				}
			}
			if(start.equals("(")){
				//如果(start)(body)(end) = (() (field1 ..,field2 ..,....) ())
				if(sqlObjectObj.field == null){
					//body=(field1 类型，field2 类型，...)
					sqlObjectObj.field = "";
					//body是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
					String field = getOriSql(originalSql,body);;	//创建的表中的所有信息
					String fields[] = field.split(",");				//通过逗号将字段分隔
					for(String s : fields){
						String f[] = s.split(" ");					//通过空格取到第一个，就是字段名
						sqlObjectObj.field += getOriSql(originalSql,f[0]) + ",";	//字段之间用逗号隔开
					}
					sqlObjectObj.field = sqlObjectObj.field.substring(0, sqlObjectObj.field.length()-1);	//去掉最后一个字段后的逗号
				}
			}
			
		}
		
		
	}

}
