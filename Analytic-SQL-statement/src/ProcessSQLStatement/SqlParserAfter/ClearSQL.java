package ProcessSQLStatement.SqlParserAfter;

import ProcessSQLStatement.SQLObject;

import java.util.ArrayList;

/**
 * Created by CH on 2016/10/25.
 */
public class ClearSQL {

    public void Clear(SQLObject sqlObject){

    }
    //field1= a.id      field2= tablename as a  Symbol=分割符号
    public static void DealRemoveRename(StringBuffer field1,StringBuffer field2,String Symbol){
        if(field2.toString().contains(Symbol)){
            String []fields = field2.toString().split(",");
            for(String temp : fields){
                String name = temp.split(Symbol)[0];
                String rename = temp.split(Symbol)[1];
                field1.replace(0,field1.length(),field1.toString().replaceAll(rename+"\\.",name+"\\."));
                field2.replace(0,field1.length(),field2.toString().replaceAll(name+Symbol+rename,name));
            }
        }
    }
    public static void main(String []args){
        StringBuffer field1 = new StringBuffer("a.id");
        StringBuffer field2 = new StringBuffer("table a");
        DealRemoveRename(field1,field2," ");
    }
    public void DealRemoveRenameSQLToSQL(SQLObject sqlObject1, SQLObject sqlObject2, String Symbol){
        String sql1 = sqlObject1.toString();
        String sql2 = sqlObject2.toString();
        String sql1s[] = sql1.split("\",\"");
        String sql2s[] = sql2.split("\",\"");
        for(String s1 : sql1s){
            for(String s2 : sql2s){
                StringBuffer sb1 = null;
                StringBuffer sb2 = null;
                if(s1.contains(Symbol)){
                    if(s2.contains("")){
                        sb1 = new StringBuffer(sqlObject1.tableName);
                        sb2 = new StringBuffer(sqlObject2.tableName);
                        DealRemoveRename(sb1,sb2,Symbol);
                        sqlObject1.tableName = sb1.toString();
                        sqlObject2.tableName = sb2.toString();
                    }
                }
            }
        }
    }
    /*
    * 去掉sql语句中的多命名
    * */
    public SQLObject RemoveRename(SQLObject sqlObject){
        ArrayList<SQLObject> temp = new ArrayList<>();
        temp.add(sqlObject);
        for(int i = 0; i<temp.size(); i++){
            SQLObject tempSqlObject = temp.get(i);
            if(tempSqlObject.next != null){
                temp.addAll(tempSqlObject.next);
            }
        }
        for(int i = 0; i<temp.size(); i++){
            for(int j = 0; i<temp.size(); j++){
                SQLObject tempSqlObject = temp.get(j);

            }
        }
        return sqlObject;
    }
}
