package br.com.ph.letras.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Music {
	private final String titulo;
	private final String autor;
	private final String letra;
	
	public Music(URL fromUrl) throws Exception {
		String html = Loader.getPage(fromUrl, new File("C:\\Users\\igreja\\Documents\\ms.txt"));
		titulo = getTitulo(html.substring(0));
		autor = getAutor(html.substring(0));
		letra = getLetra(html.substring(0));
	}

	private String getAutor(String html) throws IOException, ParserConfigurationException, SAXException {
		String index = "<div class=\"cnt-head_title\">";
		String a1 = html.substring(html.indexOf(index) + index.length());
		html = a1.substring(0, a1.indexOf("</div>"));
		html = html.substring(html.indexOf("<h2>"), html.indexOf("</h2>") + 5);
		
		OutputStream output = new FileOutputStream(new File("C:\\Users\\igreja\\Documents\\autor.xml"));
		output.write(html.getBytes());
		output.close();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docb = dbf.newDocumentBuilder();
		Document doc = docb.parse(new File("C:\\Users\\igreja\\Documents\\autor.xml"));
		Element ancor = (Element) doc.getElementsByTagName("a").item(0);
		return ancor.getTextContent();
	}

	private String getTitulo(String html) {
		String index = "<div class=\"cnt-head_title\">";
		String a1 = html.substring(html.indexOf(index) + index.length());
		html = a1.substring(0, a1.indexOf("</div>"));
		return html.substring(html.indexOf("<h1>") + 4, html.indexOf("</h1>"));
	}

	private String getLetra(String html) {
		String index = "<div class=\"cnt-letra p402_premium\">";
		String str1 = html.substring(html.indexOf(index)+index.length());
		html = str1.substring(0, str1.indexOf("</div>"));
		
		while(html.indexOf("<p>") >= 0) {
			String a1 = html.substring(0, html.indexOf("<p>"));
			String a2 = html.substring(html.indexOf("<p>")+3);
			html = a1 + a2;
		}
		
		while(html.indexOf("</p>") >= 0) {
			String a1 = html.substring(0, html.indexOf("</p>")) + "\n\n";
			String a2 = html.substring(html.indexOf("</p>")+4);
			html = a1 + a2;
		}
		
		while(html.indexOf("<br/>") >= 0) {
			String a1 = html.substring(0, html.indexOf("<br/>")) + "\n";
			String a2 = html.substring(html.indexOf("<br/>")+5);
			html = a1 + a2;
		}
		
		return html.trim();
	}
	
	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getLetra() {
		return letra;
	}
	
	@Override
	public String toString() {
		return "Titulo: "+titulo+"\n\n"+
				"Autor: "+autor+"\n\n"+
				letra;
	}
}
