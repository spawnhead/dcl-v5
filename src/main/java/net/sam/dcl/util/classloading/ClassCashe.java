package net.sam.dcl.util.classloading;

import net.sam.dcl.util.classloading.AlwaysLoadClassLoader;
import net.sam.dcl.util.DebugRequestProcessor;

import javax.management.ObjectInstance;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * @author DG
 *         Date: 11-Jun-2007
 *         Time: 18:54:17
 */
public class ClassCashe {
	static Map<String, ObjectInstance> classesCache = new HashMap();
 	static String basePath;
	static public void init(String path){
		basePath = path;
	}

	public static Map<String, ObjectInstance> getClassesCache() {
		return classesCache;
	}

	static public class ObjectInstance {
		Object instance;
		Class aClass;
		long timestamp;


		public ObjectInstance(Class aClass) {
			this.aClass = aClass;
			updateTimestamp();
		}
		public boolean isUpToDate(){
			int idx = aClass.getName().indexOf('$');
			if (idx !=-1){
				String pClassName = aClass.getName().substring(0,idx);
				return timestamp > classesCache.get(pClassName).timestamp;	
			}
			return timestamp == getFile(aClass.getName()).lastModified();
		}
		public void updateTimestamp(){
			timestamp = getFile(aClass.getName()).lastModified();
		}
		public Object getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
			if (instance == null){
				instance = aClass.newInstance();
			}
			return instance;
		}
		public void reloadClassAndInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
			instance = new AlwaysLoadClassLoader().loadClass(aClass.getName()).newInstance();
		}
	}
	static public File getFile(String name) {
	 return new File(basePath, name.replace('.', File.separatorChar) + ".class");
 }

	static public void destroy(){
		synchronized (classesCache) {
				Iterator obj = classesCache.values().iterator();
				while (obj.hasNext()) {
					ObjectInstance object = (ObjectInstance) obj.next();
					object.instance = null;
				}
				classesCache.clear();
		}
	}

	public static String getBasePath() {
		return basePath;
	}
	public static URL getBasePathAsUrl() {
		try {
			return new File(ClassCashe.getBasePath()).toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
