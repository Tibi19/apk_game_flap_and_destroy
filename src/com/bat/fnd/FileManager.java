package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileManager {
	
	public static void saveInt(int n, String path){
    	FileHandle file = Gdx.files.local(path);
    	file.writeString(String.valueOf(n), false);
    }
	
	public static int loadInt(String path){
    	FileHandle file = Gdx.files.local(path);
    	return Integer.valueOf(file.readString());
    }
	
}
