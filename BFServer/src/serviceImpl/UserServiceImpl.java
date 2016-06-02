package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import runner.ServerRunner;
import service.UserService;

public class UserServiceImpl implements UserService{
	
	@Override
	public boolean login(String username, String password) throws RemoteException {
		File userDataBase = new File("C:\\Users\\Ferriswheel\\workspace\\BFServer\\UserDataBase.txt");
		if(!userDataBase.exists()){
			try {
				userDataBase.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileReader fileReader = new FileReader(userDataBase);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line=bufferedReader.readLine())!=null){
				String[] l = line.split("_");
					if(l[0].equals(username)&&l[1].equals(password)){
						bufferedReader.close();
						return true;
					}
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

	@Override
	public boolean regist(String username, String password, String assurepassword) throws RemoteException {
		if(!password.equals(assurepassword)){
			JOptionPane.showMessageDialog(null, "�����������벻һ�£�");
			return false;
		}
		else {
			File userDataBase = new File("C:\\Users\\Ferriswheel\\workspace\\BFServer\\UserDataBase.txt");
			if(!userDataBase.exists()){
				try {
					userDataBase.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				FileReader fileReader = new FileReader(userDataBase);
				FileWriter fileWriter = new FileWriter(userDataBase,true);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				String s;
				while ((s =bufferedReader.readLine())!=null) {
					if(s.equals(username)){
						JOptionPane.showMessageDialog(null, "�û��Ѵ��ڣ�");
						bufferedReader.close();
						bufferedWriter.close();
						return false;
					}
				}
				bufferedWriter.write("\r\n"+username+"_"+password);
				bufferedWriter.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "ע��ʧ��(�����������û����ݿ��ļ���ʧ����)");
				return false;
			}
		}
		JOptionPane.showMessageDialog(null, "ע��ɹ���");
		return true;
	}

}
