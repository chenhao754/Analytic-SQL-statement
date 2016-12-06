package ProcessSQLStatement.SqlParser;


import ProcessSQLStatement.SingleSqlParserFactory;
import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment;

/**
 * Created by CH on 2016/10/25.
 */
public class CreateSelectSqlParser extends BaseSingleSqlParser {

    /**
     * 　* 构造函数，传入原始Sql语句，进行劈分。
     * 　* @param originalSql
     *
     *
     * @param originalSql
     * @param processedSql
     */
    public CreateSelectSqlParser(String originalSql, String processedSql) {
        super(originalSql, processedSql);
    }

    @Override
    protected void initializeSegments() {
        //create table .. as|select ..;
        segments.add(new SqlSegment("(create table)(.+)(as|select)"));
    }

    @Override
    public void Deal(String start, String body, String end) {
        if(sqlObjectObj != null){
            //如果(start)(body)(end) = (create table) (tablename) (as|select)
            if(start.equals("create table")){
                //tablename是等于body，但是body是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
                if(sqlObjectObj.tableName == null){
                    sqlObjectObj.tableName = getOriSql(originalSql,body);
                }
                if(sqlObjectObj.typeName == null){
                    //typeName 可以肯定为create
                    sqlObjectObj.typeName = "create";
                }
                //create解析完了，需要继续解析select后面的语句，将select语句单独当成一个select语句，解析形成的SQL对象放在当前sqlObj.next中
                if(sqlObjectObj.next != null){
                    //将sql语句中的create table去掉，则再次解析时从select开始
                    processedSql = processedSql.replace("create table","");
                    sqlObjectObj.next.add(SingleSqlParserFactory.generateParser(originalSql,processedSql).getSQLObj());
                }
            }
        }
    }
}
