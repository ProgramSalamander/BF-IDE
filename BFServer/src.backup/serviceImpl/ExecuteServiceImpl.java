//请不要修改本文件�?
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {
	// BF数组大小
	private int BFArraySize = 128;
	// BF数组(其中存储的是字节的�??)
	private char[] BFArray = new char[BFArraySize];
	// BF指针的位�?
	private int BFPointer;
	// BF当前解释的字符在code中的位置
	private int BFPosition;
	// BF跳转指令
	private int BFjumper;

	/**
	 * 请实现该方法
	 * 
	 * @throws RemoteException,Exception
	 */
	@Override
	public String execute(String code, String param) throws RemoteException, Exception {
		// 初始�?
		BFPointer = 0;
		BFPosition = 0;
		BFjumper = 0;
		for (int i = 0; i < BFArraySize; i++) {
			BFArray[i] = 0;
		}

		// 设定返回�?
		String result = "";

		// 将BF字符串转换成字符数组
		char[] codeArray = new char[code.length()];
		for (int i = 0; i < codeArray.length; i++) {
			codeArray[i] = code.substring(i, i + 1).charAt(0);
		}

		// �?查代码正确�??
		int counter = 0;
		for (int i = 0; i < code.length(); i++) {
			if (codeArray[i] == '[') {
				counter++;
			}
			if (codeArray[i] == ']') {
				counter--;
			}
		}
		if (counter != 0) {
			throw new Exception("The code is not correct");
		}
		if(code.contains("[]")){
			throw new Exception("Dead loop");
		}

		// 将输入的数�?�转化成数组
		String[] params = new String[param.length() + 1];
		for (int i = 0; i < param.length(); i++) {
			params[i] = param.substring(i, i + 1);
		}
//		params[param.length()] = "\n";

		// 设定参数指针
		int paramPointer = 0;

		while (BFPosition < code.length()) {
			switch (codeArray[BFPosition]) {
			case '+':
				if (BFArray[BFPointer] < 127) {
					BFArray[BFPointer]++;
				}
				break;
			case '-':
				if (BFArray[BFPointer] > -128) {
					BFArray[BFPointer]--;
				}
				break;
			case '>':
				if (BFPointer < BFArraySize) {
					BFPointer++;
				}
				break;
			case '<':
				if (BFPointer > 0) {
					BFPointer--;
				}
				break;
			case ',':
				BFArray[BFPointer] = params[paramPointer].charAt(0);
				if (paramPointer < params.length) {
					paramPointer++;
				}
				break;
			case '.':
				result += BFArray[BFPointer] + "";
				break;
			case '[':
				if (BFArray[BFPointer] == 0) {
					BFjumper = 1;
					while (BFjumper != 0) {
						BFPosition++;
						if (codeArray[BFPosition] == '[') {
							BFjumper++;
						}
						if (codeArray[BFPosition] == ']') {
							BFjumper--;
						}
					}
				}
				break;
			case ']':
				if (BFArray[BFPointer] != 0) {
					BFjumper = 1;
					while (BFjumper != 0) {
						BFPosition--;
						if (codeArray[BFPosition] == '[') {
							BFjumper--;
						}
						if (codeArray[BFPosition] == ']') {
							BFjumper++;
						}
					}
				}
				break;
			default:
				break;
			}
			BFPosition++;
		}

		return result;
	}
	public int getBFArraySize() throws RemoteException{
		return BFArraySize;
	}

	public char[] getBFArray() throws RemoteException {
		return BFArray;
	}

	public int getBFPointer() throws RemoteException {
		return BFPointer;
	}

}
