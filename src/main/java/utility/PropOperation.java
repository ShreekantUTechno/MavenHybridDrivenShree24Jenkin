package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropOperation {
	private Properties prop;
	
	public PropOperation(String filePath) {
		File file=new File(filePath);
		try {
			FileInputStream fileinput=new FileInputStream(file);
			prop=new Properties();
			prop.load(fileinput);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String getValue(String key) {
		return prop.getProperty(key);
	}
}
