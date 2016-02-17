import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;

public class Test {
	final static int BUFFER_SIZE = 4096;

	public static void main(String[] args) throws Exception {
		testName();
	//	getOldData();
	}
	
	public static void getOldData() throws Exception{
		
		InputStream is = Test.class.getClassLoader().getResourceAsStream(
				"oldData.txt");
		String oldData = InputStreamTOString(is);
	//	System.out.println(oldData);
		String[] strArray =oldData.split("@"); //字符分割 
		String dataString = "";
		if (strArray.length>0) {
			dataString +="[\n";
				for ( int i = 0;i< strArray.length ;i++ ) 
				{ 
					if (!strArray[i].isEmpty()) {
						String[] root = strArray[i].split("\\|"); //字符分割 ;
						dataString +="{\n";
						dataString +="\"simpleName\":\""+root[0]+"\",\n";
						dataString +="\"name\":\""+root[1]+"\",\n";
						dataString +="\"code\":\""+root[2]+"\",\n";
						dataString +="\"allName\":\""+root[3]+"\",\n";
						dataString +="\"shortName\":\""+root[4]+"\",\n";
						dataString +="\"ID\":\""+root[5]+"\"\n"; //分割后的字符输出
						if (i<strArray.length-1) {
							dataString +="},\n";
						}else{
							dataString +="}\n";
						}
					}
				} 
				dataString +="]\n";
		};
		
		//System.out.println(dataString);
		writeLog(dataString,"D://oldData.json");
	}

	public static void testName() throws Exception {

	    File file = new File("D://oldData.json");
	    InputStream  is = new FileInputStream(file);
		String dataString = InputStreamTOString(is);
		// System.out.println(dataString);
		// jsonString 转为json数组
		List<Station> list = JSON.parseArray(dataString, Station.class);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setSimpleCode(
					list.get(i).getAllName().substring(0, 1).toUpperCase());
			// System.out.println(list.get(i).getCode().substring(0,1));
		}
		Collections.sort(list);
		Set<String> nameSet = new TreeSet<String>();
		for (int i = 0; i < list.size(); i++) {
			nameSet.add(list.get(i).getSimpleCode());
			// System.out.println(list.get(i).getSimpleCode()+"----"+list.get(i).getCode()+"==="+list.get(i).getAllName()+"---"+list.get(i).getName());
		}

		Map<String, List<Station>> tempMap = new HashMap<String, List<Station>>();

		List<String> nameList = new ArrayList<>(nameSet);

		Collections.sort(nameList, new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		for (int j = 0; j < nameList.size(); j++) {
			List<Station> newList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				if (nameList.get(j).equals(list.get(i).getSimpleCode())) {
					newList.add(list.get(i));
				}
			}
			tempMap.put(nameList.get(j), newList);
		}

		String dataString2 = "";
		dataString2 += "[\n";
		for (int i = 0; i < nameList.size(); i++) {
			dataString2 += "{\"seasonName\":\"" + nameList.get(i) + "\",\n";

			dataString2 += "\"data\":[\n";
			for (int j = 0; j < tempMap.get(nameList.get(i)).size(); j++) {
				dataString2 += "{\n";
				dataString2 += "\"ID\":\""
						+ tempMap.get(nameList.get(i)).get(j).getID() + "\",\n";
				dataString2 += "\"name\":\""
						+ tempMap.get(nameList.get(i)).get(j).getName()
						+ "\",\n";
				dataString2 += "\"simpleName\":\""
						+ tempMap.get(nameList.get(i)).get(j).getSimpleName()
						+ "\",\n";
				dataString2 += "\"shortName\":\""
						+ tempMap.get(nameList.get(i)).get(j).getShortName()
						+ "\",\n";
				dataString2 += "\"code\":\""
						+ tempMap.get(nameList.get(i)).get(j).getCode()
						+ "\",\n";
				dataString2 += "\"allName\":\""
						+ tempMap.get(nameList.get(i)).get(j).getAllName()
						+ "\"\n";

				if (j == tempMap.get(nameList.get(i)).size() - 1) {
					dataString2 += "}\n";
				} else {
					dataString2 += "},\n";
				}
			}
			dataString2 += "]\n";
			if (i == nameList.size() - 1) {
				dataString2 += "}\n";
			} else {
				dataString2 += "},\n";
			}

		}
		dataString2 += "]\n";
		System.out.println("write begin");
		writeLog(dataString2,"D://newData.json");

	}

	public static String InputStreamTOString(InputStream in) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}

	public static void writeLog(String str,String path) {
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();
			sb.append(str + "\n");
			out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
			System.out.println("write end");
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
}

class Station implements Comparable<Station> {

	String ID;
	String name;
	String simpleName;
	String shortName;
	String code;
	String allName;
	String simpleCode;

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

	public String getSimpleCode() {
		return simpleCode;
	}

	public void setSimpleCode(String simpleCode) {
		this.simpleCode = simpleCode;
	}

	@Override
	public int compareTo(Station s) {
		return this.getAllName().compareTo(s.allName);
	}

}
