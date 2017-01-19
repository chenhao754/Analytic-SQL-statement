package ProcessSQLStatement.SqlSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by CH on 2017/1/18.
 */
public class SegmentRegExps {
    private List<String> AllRegExps = new ArrayList<>();//所有的正则表达式
    public List<String> getAllRegExps() {
        return AllRegExps;
    }

    public void setAllRegExps(List<String> allRegExps) {
        AllRegExps = allRegExps;
    }


    public SegmentRegExps(){//初始化所有的正则表达式
        Collections.addAll(AllRegExps,alterRegExps);
        Collections.addAll(AllRegExps,createSelectRegExps);
        Collections.addAll(AllRegExps,createRegExps);
        Collections.addAll(AllRegExps,deleteRegExps);
        Collections.addAll(AllRegExps,dropRegExps);
        Collections.addAll(AllRegExps,insertSelectRegExps);
        Collections.addAll(AllRegExps,insertRegExps);
        Collections.addAll(AllRegExps,selectRegExps);
        Collections.addAll(AllRegExps,updateRegExps);
    }

    //AlterSqlParser的正则分片表达式
    public static String[] alterRegExps = {
            "(alter table)(.+)(add)",
            "(add)(.+)(;|ENDOFSQL)",
            "(alter table)(.+)(drop)",
            "(drop)(.+)(;|ENDOFSQL)",

            "(alter table)(.+)(change)",
            "(change)(.+)(;|ENDOFSQL)",
            "(alter table)(.+)(modify)",
            "(modify)(.+)(;|ENDOFSQL)",
    };
    //CreateSelectSqlParser的正则分片表达式
    public static String[] createSelectRegExps = {
            "(create table)(.+)(as|select)",
    };
    //CreateSqlParser的正则分片表达式
    public static String[] createRegExps = {
            "(create table)(.+)(like)",
            "(like)(.+)(;|ENDOFSQL)",

            "(create table)(.+)([(])",
            "([(])(.+)(;|ENDOFSQL)",
    };
    //DeleteSqlParser的正则分片表达式
    public static String[] deleteRegExps = {
            "(delete from)(.+)(where|;|ENDOFSQL)",
            "(where)(.+)(;|ENDOFSQL)",
    };
    //DropSqlParser的正则分片表达式
    public static String[] dropRegExps = {
            "(drop table)(.+)(;|ENDOFSQL)",
    };
    //InsertSelectSqlParser的正则分片表达式
    public static String[] insertSelectRegExps = {
            "(insert into)(.+)(select)",//EXP:insert into b(a, b, c) select d,e,f from b;
    };
    //InsertSqlParser的正则分片表达式
    public static String[] insertRegExps = {
            "(insert into)(.+)([(])",
            "([(])(.+)([)] values)",
            "([)] values [(])(.+)([)])",
    };
    //SelectSqlParser的正则分片表达式
    public static String[] selectRegExps = {
            "(select distinct)(.+)(from)",
            "(select)(.+)(from)",
            "(from)(.+)(where)",
            "(where)(.+)(group by)",
            "(group by)(.+)(having)",
            "(having)(.+)(order by)",
            "(order by)(.+)(limit)",
            //中间隔一个关键词
            "(from)(.+)(group by)",
            "(where)(.+)(having)",
            "(group by)(.+)(order by)",
            "(having)(.+)(limit)",
            //中间隔两个关键词
            "(from)(.+)(having)",
            "(where)(.+)(order by)",
            "(group by)(.+)(limit)",
            //中间隔三个关键词
            "(from)(.+)(order by)",
            "(where)(.+)(limit)",
            //中间隔四个关键词
            "(from)(.+)(limit)",
            //结尾
            "(select)(.+)(;|ENDOFSQL)",
            "(from)(.+)(;|ENDOFSQL)",
            "(where)(.+)(;|ENDOFSQL)",
            "(order by)(.+)(;|ENDOFSQL)",
            "(having)(.+)(;|ENDOFSQL)",
            "(group by)(.+)(;|ENDOFSQL)",
            "(limit)(.+)(;|ENDOFSQL)",
    };
    //UpdateSqlParser的正则分片表达式
    public static String[] updateRegExps = {
            "(update)(.+)(set)",
            "(set)(.+)(where|;|ENDOFSQL)",
            "(where)(.+)(;|ENDOFSQL)",
    };

}