//鏈嶅姟鍣║serService鐨凷tub锛屽唴�?�圭浉鍚�?
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public boolean regist(String username,String password,String assurepassword) throws RemoteException;
	public boolean login(String username, String password) throws RemoteException;

	public boolean logout(String username) throws RemoteException;
}
