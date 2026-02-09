package net.sam.dcl.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;


/**
 * User: Dima
 * Date: Oct 14, 2004
 * Time: 12:34:13 PM
 */
public class TextResource {
  protected static Log log = LogFactory.getLog(TextResource.class);
  private List files = new ArrayList();
  private List<String> entries = new ArrayList<String>();
  private Object mutex = new Object();
	private Random randomGenerator = new Random();
	public TextResource() {

  }
  public TextResource(String file) {
    addFile(file);
  }
  public void addFile(String file){
    synchronized (mutex) {
      files.add(file);
    }
  }
  public String get(int idx){
    synchronized (mutex) {
      String str = (String) entries.get(idx);
      return str;
    }
  }
  public String getRandom(){
    synchronized (mutex) {
			int size = entries.size();
			if (size != 0) {
				int idx = Math.abs(randomGenerator.nextInt() % size);
				return (String) entries.get(idx);
			}
      return "";
    }
  }
  public void clear() {
    synchronized(mutex){
      entries.clear();
    }
  }
  public void load() {
    synchronized(mutex){
      for (int i = 0; i < files.size(); i++) {
        String file = (String) files.get(i);
        List<String> list = load(file);
        entries.addAll(list);
      }
    }
  }
  public void reload(){
    synchronized (mutex) {
      clear();
      load();
    }
  }
  protected List<String> load(String file) {
		List<String> ret = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Cp1251"));
			String line = null;
			while ((line = reader.readLine())!=null){
				ret.add(line);
			}
		} catch (Exception e) {
      log.error("Error while parsing:"+file,e);
    }
    return ret;
  }
  public static void main(String[] args) {
    TextResource xmlResource = new TextResource(ClassLoader.getSystemResource("resources/sql-resources.xml").getFile() );
    //XMLResource xmlResource = new XMLResource("D:\\Projects\\am2\\www\\WEB-INF\\sql-resources.xml");
    xmlResource.load();
    System.out.println("loaded");
  }

}