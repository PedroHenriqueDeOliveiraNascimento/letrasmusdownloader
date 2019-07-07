package br.com.ph.letras.downloader;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class Loader {
	public static String getPage(URL fromUrl, File toFile) throws Exception {
		InputStream input = fromUrl.openStream();
		byte[] buffer = new byte[1024];
		String result = "";
		int request = input.read(buffer);
		
		while (request != -1) {
			result += new String(buffer, "UTF-8");
			request = input.read(buffer);
		}
		
		OutputStream output = new FileOutputStream(toFile);
		output.write(result.getBytes());
		output.close();
		
		return result;
	}
}
