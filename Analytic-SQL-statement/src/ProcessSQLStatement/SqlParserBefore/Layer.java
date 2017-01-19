package ProcessSQLStatement.SqlParserBefore;

import ProcessSQLStatement.SQLObject;
import ProcessSQLStatement.SingleSqlParserFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by CH on 2016/10/25.
 */
public class Layer {
    /*
    *处理语句
    * originalSql：为原SQL语句
    * sql:为小写后的SQL语句
    * 小写后的SQL语句更好处理，但是处理完后需要根据源SQL语句恢复例如大写表名，字段名等等
    * */
    public SQLObject nesting(String originalSql, String sql){
        SQLObject sqlObjectObj;
        int i = 0, j = 0;   //i：第一个(的下标   j：与i对应的)下标
        ArrayList<BigInteger> temp = new ArrayList<BigInteger>();   //暂时存放加密后的嵌套中的语句
        for(i = 0; i<sql.length(); i++){    //判断嵌套语句
            if (sql.charAt(i) == '(') {     //从i开始判断，当i为(时
                int num = 1;    //判断i对应的j的位置，如果嵌套中还有嵌套，则第一个)不是i所对应的
                for(j = i+1;  j<sql.length(); j++){ //j从i后一位开始判断
                    if(sql.charAt(j) == '('){   //如果先出现的不是)，而是(，则表示嵌套中还有嵌套，则num++
                        num ++;
                    }
                    if(sql.charAt(j) == ')'){   //如果是)，则表示抵消前面的一个(
                        num --;
                    }
                    if(num == 0){   //如果num抵消完了，则表示一个嵌套语句完成，则i,j位置确定
                        break;
                    }
                }
                String strtemp = sql.substring(i,j+1);  //strtemp：为嵌套语句
                //如果嵌套语句中存在下面的词语，则表示是一个有效嵌套
                if(strtemp.contains("select") || strtemp.contains("update") || strtemp.contains("delete") || strtemp.contains("insert") || strtemp.contains("create")){
                    BigInteger encryptTemp = encrypt(strtemp);  //encryptTemp：为嵌套语句加密后的语句
                    sql = sql.replace(strtemp,encryptTemp.toString()); //将加密后的语句替换加密前的语句
                    temp.add(encryptTemp);                  //将加密后的语句存入语句字典，用来解密
                    i = 0;  //如果整个语句改变，则从头开始判断
                }else{
                    i = i + 1;  //如果只是无效的语句，则不需要
                }
            }
        }
        sqlObjectObj = SingleSqlParserFactory.generateParser(originalSql,sql).getDealResult();    //处理加密后的整条语句

        //解密TableName中出现的嵌套
        replayTableName(sqlObjectObj,temp);
        //解密Condition中出现的嵌套
        replayCondition(sqlObjectObj,"where",temp);
        replayCondition(sqlObjectObj,"order by",temp);
        replayCondition(sqlObjectObj,"group by",temp);
        replayCondition(sqlObjectObj,"having",temp);

        //处理嵌套中的语句
        if(temp != null && !temp.isEmpty()) {//如果加密语句不为空，则含有嵌套语句
            for (BigInteger s : temp) {
                String decrypttemp = decrypt(s);    //解密嵌套语句
                int start = decrypttemp.indexOf("(");
                int end = decrypttemp.lastIndexOf(")");
                //处理解密后，且去掉头尾的小括号的嵌套语句，并将结果放入整条嵌套语句处理形成的对象中的next中
                originalSql = getOriSql(originalSql,decrypttemp.substring(start + 1, end));
                sqlObjectObj.next.add(nesting(originalSql,originalSql));
            }
        }
        return sqlObjectObj.trim();
    }
    /*
   * 返回原本的sql语句，而不是小写后的
   * Str：未处理前的整条sql语句
   * subStr：处理后的整条sql语句中截取的部分
   * */
    public static String getOriSql(String Str,String subStr){
        String StrLower = Str.toLowerCase();
        for(int i = 0; i<StrLower.length(); i++){
            int StrIndex = i;
            int SubStrIndex = 0;
            for(; SubStrIndex<subStr.length();){
                if(StrLower.charAt(StrIndex++) != subStr.charAt(SubStrIndex++)){
                    SubStrIndex = subStr.length()+1;
                    break;
                }
            }
            if(SubStrIndex == subStr.length()){
                return Str.substring(i,StrIndex);
            }
        }
        return subStr;
    }
    /*解密TableName中出现的嵌套
   * sqlObjectObj：加密前整条sql语句处理完后产生的sql对象
   * temp:加密后的嵌套语句字典，用来解密
   * */
    public void replayTableName(SQLObject sqlObjectObj, ArrayList<BigInteger> temp){
        //如果字典为空，则表示没有加密前的嵌套语句，则不用解密
        if(temp == null || temp.isEmpty()){
            return;
        }
        if(sqlObjectObj.tableName != null){
            for(BigInteger s : temp ){  //遍历字典
                if(sqlObjectObj.tableName.contains(s.toString())){ //如果条件语句中包含有加密字段
                    String decrypttemp = decrypt(s);    //解密
                    sqlObjectObj.tableName = sqlObjectObj.tableName.replace(s.toString(),decrypttemp);   //将解密语句替换加密语句
                }
            }
        }
    }
    /*解密Condition中出现的嵌套
    * sqlObjectObj：加密前整条sql语句处理完后产生的sql对象
    * type:Condition中的具体条件，例如，"where","order by"等等，该变量可以直接处理所有条件变量
    * temp:加密后的嵌套语句字典，用来解密
    * */
    public void replayCondition(SQLObject sqlObjectObj, String type, ArrayList<BigInteger> temp){
        //如果字典为空，则表示没有加密前的嵌套语句，则不用解密
        if(temp == null || temp.isEmpty()){
            return;
        }
        if(sqlObjectObj.condition != null){
            if(sqlObjectObj.condition.get(type) != null){ //判断所要解密的条件是否为空
                for(BigInteger s : temp ){  //遍历字典
                    if(sqlObjectObj.condition.get(type).contains(s.toString())){ //如果条件语句中包含有加密字段
                        String decrypttemp = decrypt(s);    //解密
                        sqlObjectObj.condition.put(type, sqlObjectObj.condition.get(type).replace(s.toString(),decrypttemp));   //将解密语句替换加密语句
                    }
                }
            }
        }
    }
    /**
     *解密
     *@return String 返回加密字符串
     */
    public static String decrypt(BigInteger m)
    {
        byte[]mt = m.toByteArray();//m为密文的BigInteger类型
        String str= null;
        try {
            str = (new String(mt,"GBK"));
            str=java.net.URLDecoder.decode(str,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     *加密
     *@return String 返回加密字符串
     */
    public static BigInteger encrypt(String Mtext)
    {
        BigInteger m = null;
        try {
            Mtext= URLEncoder.encode(Mtext,"GBK");
            byte ptext[]= new byte[0];//将字符串转换成byte类型数组，实质是各个字符的二进制形式
            ptext = Mtext.getBytes("GBK");
            m = new BigInteger(ptext);//二进制串转换为一个大整数
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return m;
    }
}
