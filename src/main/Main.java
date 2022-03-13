package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import model.Fence;
import model.FenceData;

public class Main {
	
	public static Scanner rd=new Scanner(System.in);
	public static FenceData fencesData=new FenceData();
	
	public static void main(String[] args) {
		
		fencesData.load();
		
		boolean exit=false;
		while(!exit) {
			exit=menu();
		}
	}
	
	public static boolean menu() {
		boolean exit=false;
		int selection=0;
		System.out.println("Sistema de vallas publicitarias. Seleccione:");
		System.out.println("1. Importar archivo (.cvs).");
		System.out.println("2. Añadir valla publicitaria.");
		System.out.println("3. Mostar todas las vallas.");
		System.out.println("4. Generar reporte de vallas peligorsas.");
		System.out.println("9. Exit.");
		selection=Integer.parseInt(rd.nextLine());
		
		switch(selection){
		case 1: importCVS();
			break;
		case 2:	addFence();
			break;
		case 3:	showFences();
			break;
		case 4:	createReport();
			break;
		case 9:	exit=true;
			break;
		}
		
		return exit;
	}
	public static void createReport() {
		String report=	  "===========================\n"
						+ "DANGEROUS BILLBOARD REPORT\n"
						+ "===========================\n"
						+ "The dangerous billboard are:\n";
		int cont=1;
		
		for(Fence f: fencesData.fences) {
			int area=f.getHeight()*f.getWidth();
			if(area>=200000) {
				report+=cont+". Billboard "+f.getBrand()+" with area "+area+" cm2 \n";
				cont++;
			}
		}
		String path="Reporte.txt";
		File rprt=new File(path);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(rprt);
			fos.write(report.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(report);
		
	}
	
	public static void showFences() {
		System.out.println("W      H      inUse   Brand");
		for(Fence f:fencesData.fences) {
			System.out.println(f.getWidth()+"    "+f.getHeight()+"    "+f.isInUse()+"   "+f.getBrand());
		}
		System.out.println("\nTOTAL: "+fencesData.fences.size()+" vallas.");
	}
	
	public static void addFence() {
		String data="";
		System.out.println("Ingrese la informacion de la valla a agregar en el formato indicado a continuacion:");
		System.out.println("Ancho++Alto++enUso++marca");
		System.out.println("Ancho y alto: Enteros.");
		System.out.println("En uso: booleano.");
		System.out.println("Marca: Texto.");
		data=rd.nextLine();
		String[] dataF=data.split("\\++");
		Fence nwFence=new Fence(Integer.parseInt(dataF[0]), Integer.parseInt(dataF[1]), Boolean.parseBoolean(dataF[2]), dataF[3]);
		if(!(fencesData.fences.contains(nwFence))) {
			fencesData.fences.add(nwFence);
			System.out.println("Valla agregada.");
		}else {
			System.out.println("La valla ya existe.");
		}
		fencesData.save();
	}
	
	public static void importCVS() {
		System.out.println("Ingrese la ruta del archivo a importar:");
		String path="";
		path=rd.nextLine();
		
		String received="";
		File file=new File(path);
		try {
			FileInputStream fis=new FileInputStream(file);
			InputStreamReader isr=new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(isr);
			br.readLine();
			while((received=br.readLine())!=null) {
				String[] data=received.split("\\|");
				Fence nwFence=new Fence(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Boolean.parseBoolean(data[2]), data[3]);
				if(!(fencesData.fences.contains(nwFence))) {
					fencesData.fences.add(nwFence);
				}
			}
			System.out.println("Importacion exitosa.");
			fis.close();
		} catch (IOException e) {
			System.out.println("Path incorrecto. Ingreselo nuevamente.");
		}
		fencesData.save();
	}
	

}
