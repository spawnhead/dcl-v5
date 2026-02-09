package net.sam.dcl.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User: Dima
 * Date: Oct 14, 2004
 * Time: 12:34:13 PM
 */
public class XMLResource {
  protected static Log log = LogFactory.getLog(XMLResource.class);
  private List files = new ArrayList();
  private Map entries = new HashMap();
  private Object mutex = new Object();
  public XMLResource() {

  }
  public XMLResource(String file) {
    addFile(file);
  }
  public void addFile(String file){
    synchronized (mutex) {
      files.add(file);
    }
  }
  public String get(String key) throws Exception {
    synchronized (mutex) {
      String str = (String) entries.get(key);
      if ( null == str )
      {
        throw new Exception("Не найден ресурс с идентификатором " + key);
      }
      VarStringParser parser = new VarStringParser(new CallbackImpl(this));
      try {
        str = parser.parse(str);
      } catch (Exception e) {}
      return str;
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
        Map tmpMap = load(file);
        entries.putAll(tmpMap);
      }
    }
  }
  public void reload(){
    synchronized (mutex) {
      clear();
      load();
    }
  }
  protected Map load(String file) {
    class MyHandler extends DefaultHandler{
      private Map map = new HashMap();
      private String key=null;
      private StringBuffer value=null;
      public Map getMap() {
        return map;
      }
      public void setMap(Map map) {
        this.map = map;
      }
      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("entry")){
          key = attributes.getValue("id");
        }
      }
      public void characters(char ch[], int start, int length) throws SAXException {
        if (key!=null){
          if (value == null) value = new StringBuffer();
          value.append(ch, start, length);
        }
      }
      public void endElement(String uri, String localName, String qName) throws SAXException {
        if (key!= null){
          map.put(key, value.toString());
          key = null;
          value = null;
        }
      }
      public void error(SAXParseException e) throws SAXException {
        log.error(e.getMessage(),e);
        super.fatalError(e);
      }
      public void fatalError(SAXParseException e) throws SAXException {
        log.fatal(e.getMessage(), e);
        super.fatalError(e);
      }
      public void warning(SAXParseException e) throws SAXException {
        log.warn(e.getMessage(), e);
        super.warning(e);
      }
    }
    MyHandler myHandler = new MyHandler();
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setValidating(true);
      SAXParser parser = factory.newSAXParser();
      parser.getXMLReader().setFeature("http://xml.org/sax/features/validation",false);
      parser.parse(new File(file), myHandler);
		} catch (Exception e) {
      log.error("Error while parsing:"+file,e);
    }
    return myHandler.getMap();
  }
  public static void main(String[] args) {
    XMLResource xmlResource = new XMLResource(ClassLoader.getSystemResource("resources/sql-resources.xml").getFile() );
    xmlResource.load();
    System.out.println("loaded");
  }
  public static class CallbackImpl implements VarStringParser.Callback {
    XMLResource xmlResource;
    public CallbackImpl(XMLResource xmlResource) {
      this.xmlResource = xmlResource;
    }
    public String process(String var) throws Exception {
      return xmlResource.get(var);
    }
  }

}
