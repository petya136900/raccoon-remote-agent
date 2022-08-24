package com.petya136900.racoonvpn.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

public class Tools {	
	public static final String WORK_DIR;
		
	private static final Logger LOG 
    = Logger.getLogger(Tools.class.getName());
		
	static {
		String homeDir = System.getProperty("user.home");
		if(homeDir==null||homeDir.trim().length()<1) {
			WORK_DIR = "~/.raccoon/";
		} else {
			WORK_DIR = homeDir+"/.raccoon/";
		}
		LOG.info("Work directory: "+WORK_DIR);
	}
	
	public static String getLocalIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}
	
	public static String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "localhost";
		}
	}
	
	public static File createFile(String relFileName, String data) throws IOException {
		createWorkDir();
		File newFile = new File(WORK_DIR+relFileName);
		LOG.info(String.format("Trying to create file[%s]",newFile.getAbsolutePath()));
		checkAndCreateFile(newFile);
		FileUtils.writeStringToFile(newFile, data, StandardCharsets.UTF_8, false);
		return newFile;
	}
	
	public static File createFileCustom(String pathAbsolute, String data) throws IOException {
		LOG.info(String.format("Trying to create file[%s]",pathAbsolute));
		createWorkDir();
		File newFile = new File(pathAbsolute);
		checkAndCreateFile(newFile);
		FileUtils.writeStringToFile(newFile, data, StandardCharsets.UTF_8, false);
		return newFile;
	}   

	
	private static void checkAndCreateFile(File newFile) throws IOException {
		File parents = newFile.getParentFile();
		if(!parents.exists()&&parents.mkdirs()) {}
		if(newFile.isDirectory())
			newFile.delete();
		if(!newFile.exists())
			newFile.createNewFile();
	}

	public static File createFile(String fileName) throws IOException {
		return createFile(fileName,"");
	}
	
	public static File createFileCustom(String absolutePath) throws IOException {
		return createFileCustom(absolutePath,"");
	}
	
	private static String privateReadISToString(InputStream is) {
		BufferedReader br = new BufferedReader(
			      new InputStreamReader(is, StandardCharsets.UTF_8));
		String result = br.lines()
		.collect(Collectors.joining("\n"));
		try {
			br.close();
		} catch (IOException e) {}
		return result;
	}
	
	public static File getFile(String fileName) throws IOException {
		File file = new File(WORK_DIR+fileName);
		if(file.exists())
			return file;
		throw new FileNotFoundException();
	}
	
	public static void createWorkDir() {
		File workDir = new File(WORK_DIR);
		if(workDir.isFile())
			workDir.delete();
		if(!workDir.exists())
			workDir.mkdirs();		
	}
	public static String generateToken() {
		return (UUID.randomUUID().toString()+UUID.randomUUID().toString()).replaceAll("-", "");
	}
	public static String hashSHA256(String string) {
		return DigestUtils.sha256Hex(string.getBytes());
	}
	public static String readFileToString(String relativePath) throws IOException {
		File file = getFile(relativePath);
		return privateReadISToString(new FileInputStream(file));
	}

	public static void fixCharset() {
		try {
			System.setProperty("file.encoding","UTF-8");
			Field charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null,null);
		} catch (Exception e) {
			
		}
	}
}
