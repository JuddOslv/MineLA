package com.wisemen.hhb.module.app.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class UnzipTest {
	public void zip(String zipFileName, String inputFile) throws Exception {
		zip(zipFileName, new File(inputFile));
	}

	public void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		zip(out, inputFile, "");
		System.out.println("zip done");
		out.close();
	}

	public void unzip(String zipFileName, String outputDirectory) throws Exception {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
		ZipEntry z;
		while ((z = in.getNextEntry()) != null) {
			System.out.println("unziping " + z.getName());
			if (z.isDirectory()) {
				String name = z.getName();
				name = name.substring(0, name.length() - 1);
				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();
				System.out.println("mkdir " + outputDirectory + File.separator
						+ name);
			} else {
				File f = new File(outputDirectory + File.separator
						+ z.getName());
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1)
					out.write(b);
				out.close();
			}
		}
		in.close();
	}

	public void zip(ZipOutputStream out, File f, String base) throws Exception {
		System.out.println("Zipping  " + f.getName());
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1)
				out.write(b);
			in.close();
		}

	}
	
	public static boolean unZip(String zipFilePath, String outputDirectory) throws IOException{
		
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry zEntry;
		
		while((zEntry = zis.getNextEntry()) != null){
			System.out.println("unziping " + zEntry.getName());
			if(zEntry.isDirectory()){
				String name = zEntry.getName();
				name = name.substring(0, name.length()-1);
				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();
				System.out.println("mkdir " + outputDirectory + File.separator + name);
			}else{
				File f = new File(outputDirectory + File.separator + zEntry.getName());
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int buff;
				while((buff = zis.read()) != -1){
					out.write(buff);
				}
			}
			zis.closeEntry();
			zis.close();
		}
			
			
			
		return false;
	}

	public static void main(String[] args) {
		try {
			UnzipTest t = new UnzipTest();
//			t.zip("D:/zip.zip", "D:/zip");
//			t.unzip("E:/sentenceReadRecord.zip", "E:/sentenceReadRecord");
			t.unZip("E:/sentenceReadRecord.zip", "E:/sentenceReadRecord");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}
