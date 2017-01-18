package ProcessSQLStatement.SqlParser;

import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment.SegmentRegExps;
import ProcessSQLStatement.SqlSegment.SqlSegment;

/**
 * Created by CH on 2016/10/25.
 */
public class AlterSqlParser extends BaseSingleSqlParser {
    /**
     * 　* 构造函数，传入原始Sql语句，进行劈分。
     * 　* @param originalSql
     *
     *
     * @param originalSql
     * @param processedSql
     */
    public AlterSqlParser(String originalSql, String processedSql) {
        super(originalSql, processedSql);
    }
    @Override
    protected void initializeSegments() {
        //alter table .. add|drop|change .. ;
        for(String regExp : SegmentRegExps.alterRegExps){//AlterSqlParser的正则表达式初始化
            segments.add(new SqlSegment(regExp));
        }
    }

    @Override
    public void Deal(String start, String body, String end) {
        if(sqlObjectObj != null){
            //如果(start) (body) (end) = (alter table) (tablename) (add|drop|change)
            if(start.equals("alter table")){
                if(sqlObjectObj.tableName == null){
                    //tablename是等于body，但是body是在原sql语句上进行了小写处理的，所以需要通过getOriSql进行还原
                    sqlObjectObj.tableName = getOriSql(originalSql,body);
                }
                if(sqlObjectObj.typeName == null){
                    //typeName 可以肯定为alter
                    sqlObjectObj.typeName = "alter";
                }
            }
            if(start.equals("add") || start.equals("drop") || start.equals("change") || start.equals("modify")){
                body = body.replace("primary key","");
                body = body.replace("(","");
                body = body.replace(")","");
                String fields[];
                if(body.contains(",")){
                    fields = body.split(",");
                }else{
                    fields = new String[1];
                    fields[0] = body;
                }
                sqlObjectObj.field = "";
                for(String field : fields){
                    String s[] = field.trim().split("\\s");
                    sqlObjectObj.field += getOriSql(originalSql,s[0])+",";
                }
                sqlObjectObj.field = sqlObjectObj.field.substring(0, sqlObjectObj.field.length()-1);
            }
        }

    }
}
