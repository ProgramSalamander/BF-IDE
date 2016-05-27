package serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File f = new File(userId + "_" + fileName);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		File f = new File(userId + "_" + fileName);
		String code = "";
		try {
			FileReader fileReader = new FileReader(f);
			char[] buf = new char[16];
			int i =0;
			while((i =fileReader.read(buf,0,buf.length))!=-1){
				fileReader.read(buf,0,i);
				code +=new String(buf);
			}
			fileReader.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return code;
		
	}

	@Override
	public String readFileList(String userId) {
		// TODO Auto-generated method stub
		return "OK";
	}

	
	
}
