package ProcessSQLStatement.SqlParser;


import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment;
/**
 * Created by CH on 2016/10/25.
 */
public class DropSqlParser extends BaseSingleSqlParser {

    /**
     * 　* 构造函数，传入原始Sql语句，进行劈分。
     * 　* @param originalSql
     *
     *
     * @param originalSql
     * @param processedSql
     */
    public DropSqlParser(String originalSql, String processedSql) {
        super(originalSql, processedSql);
    }

    @Override
    protected void initializeSegments() {
        //drop table .. ;
        segments.add(new SqlSegment("(drop table)(.+)(;|ENDOFSQL)"));
    }

    @Override
    public void Deal(String start, String body, String end) {
        if(sqlObjectObj != null){
            //如果(start) (body) (end) = (drop table)(.+)(;|ENDOFSQL)
            if(start.equals("drop table")){
                if(sqlObjectObj.typeName == null){
                    sqlObjectObj.typeName = "drop";
                }
                if(sqlObjectObj.tableName == null){
                    //tablename是等于body，但是body是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
                    sqlObjectObj.tableName = getOriSql(originalSql,body);;
                }
            }
        }

    }
}
