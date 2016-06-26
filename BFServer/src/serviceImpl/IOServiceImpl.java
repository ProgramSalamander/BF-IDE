package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

	@Override
	public boolean gitFile(String userId,String fileName,String code) throws RemoteException {
		File file = new File(userId+"_"+fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fileWriter = new FileWriter(file,true);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-H-m-s");
			String string = simpleDateFormat.format(Calendar.getInstance().getTime());
			fileWriter.write(string+"#"+code+"\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return false;
	}

	@Override
	public String[] readGitFile(String userId, String fileName) throws RemoteException {
		File file = new File(userId+"_"+fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		ArrayList<String> list = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line="";
			while((line=bufferedReader.readLine())!=null){
				list.add(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		String[] lists = new String[list.size()];
		lists = list.toArray(lists);
		return lists;
	}

	
	
}
