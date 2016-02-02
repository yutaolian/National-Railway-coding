
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import com.alibaba.fastjson.JSON;


public class Test {
	final static int BUFFER_SIZE = 4096;  
	  
	public static void main(String[] args) throws Exception {
		
		testName();
	}
	public static void testName() throws Exception {

		InputStream is=Test.class.getClassLoader().getResourceAsStream("station.json"); 
		String dataString = InputStreamTOString(is, "UTF-8");
		System.out.println(dataString); 
		//jsonString 转为json数组
	    List<Station> list = JSON.parseArray(dataString, Station.class);  
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getName());
		}
	}
	public static String InputStreamTOString(InputStream in,String encoding) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
        data = null;  
        return new String(outStream.toByteArray(),"UTF-8");  
    }  
}

class Station{
	
	String ID;
	String name;
	String simpleName;
	String shortName;
	String code;
	String allName;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAllName() {
		return allName;
	}
	public void setAllName(String allName) {
		this.allName = allName;
	}
	

}
