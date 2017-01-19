package ProcessSQLStatement.SQLObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by CH on 2016/10/25.
 */
//解析sql语句形成的对象
public class SQLObject {
	public ArrayList<SQLObject> next;	//当有嵌套语句时，存储嵌套中的语句所形成的SQL
	public String tableName;	//操作的表名
	public String typeName;	//操作名
	public String field;		//操作的字段
	public HashMap<String, String> condition;	//条件

	public ArrayList<SQLObject> getNext() {
		return next;
	}

	public void setNext(ArrayList<SQLObject> next) {
		this.next = next;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName.trim();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName.trim();
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field.trim();
	}

	public HashMap<String, String> getCondition() {
		return condition;
	}

	public void setCondition(HashMap<String, String> condition) {
		this.condition = condition;
	}

	public SQLObject(){
		tableName = null;
		typeName = null;
		field = null;
		condition = new HashMap<String,String>();
		next = new ArrayList<SQLObject>();
	}
	public SQLObject trim(){
		if(tableName != null && !tableName.isEmpty()){
			tableName = tableName.trim();
		}
		if(typeName != null && !typeName.isEmpty()){
			typeName = typeName.trim();
		}
		if(field != null && !field.isEmpty()){
			field = field.trim();
		}
		Iterator iter = condition.entrySet().iterator();
		while (iter.hasNext()) {	//遍历condition
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			val = String.valueOf(val).trim();
			condition.put(String.valueOf(key),String.valueOf(val));
		}
		return this;
	}
	public String toString(){
		HashMap<String,String> hm = new HashMap();
		if(tableName != null && !tableName.isEmpty()){
			hm.put("tableName",tableName.trim());
		}
		if(typeName != null && !typeName.isEmpty()){
			hm.put("typeName",typeName.trim());
		}
		if(field != null && !field.isEmpty()){
			hm.put("field",field.trim());
		}
		Iterator iter = condition.entrySet().iterator();
		while (iter.hasNext()) {	//遍历condition
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			val = String.valueOf(val).trim();
			hm.put(String.valueOf(key),String.valueOf(val));
		}
		if(next != null){
			for(SQLObject s : next){
				if(hm.get("next") == null){
					hm.put("next",s.toString());
				}else{
					hm.put("next",hm.get("next")+s.toString());
				}
			}
		}
		return hm.toString();
	}
	public void PrintToString(){
		if(typeName != null){
			System.out.println("typeName:"+ typeName.trim());
		}
		if(tableName != null){
			System.out.println("tableName:"+ tableName.trim());
		}
		if(field != null){
			System.out.println("field:"+ field.trim());
		}
		if(condition != null){
			System.out.println("condition:"+ condition.toString().trim());
		}
		if(next != null){
			System.out.println("next.size():"+next.size());
			for(SQLObject s : next){
				s.PrintToString();
			}
		}
	}
}
