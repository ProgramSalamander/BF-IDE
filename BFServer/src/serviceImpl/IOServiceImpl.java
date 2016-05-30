package serviceImpl;

import java.io.BufferedReader;
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
		File d = new File(userId);
		if(!d.exists()){
			d.mkdirs();
		}
		File f = new File(d,userId + "_" + fileName);
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
		File d = new File(userId);
		if(!d.exists()){
			d.mkdirs();
		}
		File f = new File(d,userId + "_" + fileName);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String code = "";
		try {
			FileReader fileReader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			code = bufferedReader.readLine();
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
		
	}

	@Override
	public String readFileList(String userId) {
		File d = new File(userId);
		if(!d.exists()){
			d.mkdirs();
		}
		File[] files = d.listFiles();
		String fileList= "";
		for(int i=0;i<files.length;i++){
			if(i !=files.length-1)
				fileList += files[i].getName()+"//";
			else {
				fileList +=files[i].getName();
			}
		}
		return fileList;
	}

	
	
}
