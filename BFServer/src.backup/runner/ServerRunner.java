package runner;

import java.io.File;
import java.io.IOException;

import rmi.RemoteHelper;

public class ServerRunner {
	public ServerRunner() {
		
		new RemoteHelper();
	}
	
	public static void main(String[] args) {
		new ServerRunner();
	}
}
