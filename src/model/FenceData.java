package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;

public class FenceData {
	public ArrayList<Fence> fences=new ArrayList<>();
	
	public void save() {
		try {
			Gson gson=new Gson();
			String json=gson.toJson(this);
			File file=new File("data.json");
			FileOutputStream fos=new FileOutputStream(file);
			fos.write(json.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			FileInputStream fis=new FileInputStream(new File("data.json"));			
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String json="";
			String line;
			while((line=br.readLine())!=null) {
				json+=line;
			}
			Gson gson=new Gson();
			FenceData fencesData=gson.fromJson(json,FenceData.class);
			fences=fencesData.fences;
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
