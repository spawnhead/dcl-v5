package net.sam.dcl.util.classloading;

import net.sam.dcl.config.Config;
import net.sam.dcl.util.DebugRequestProcessor;

import java.io.*;
import java.net.URLClassLoader;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * @author DG
 *         Date: 11-Jun-2007
 *         Time: 19:00:00
 */
public class AlwaysLoadClassLoader extends URLClassLoader {
	static public final String reloadingPackage = "nat.sam.dcl";
	ClassLoader deligated = null;


	public AlwaysLoadClassLoader()  {
		super(new URL[]{ClassCashe.getBasePathAsUrl()});
		deligated = Thread.currentThread().getContextClassLoader();
		if (deligated == null) {
			deligated = this.getClass().getClassLoader();
		}
		while(deligated instanceof AlwaysLoadClassLoader){
			deligated = ((AlwaysLoadClassLoader)deligated).deligated;
		}
	}
	public AlwaysLoadClassLoader(ClassLoader loader){
		super(new URL[]{ClassCashe.getBasePathAsUrl()});
		deligated = loader;
	}                                                                                       

	public Class loadClass(String name) throws ClassNotFoundException {
		if (isInReloadedPackage(name)) {
			synchronized (ClassCashe.getClassesCache()) {
				if (ClassCashe.getClassesCache().containsKey(name)) {
					ClassCashe.ObjectInstance casheRecord = ClassCashe.getClassesCache().get(name);
					if (!casheRecord.isUpToDate()) {
						casheRecord.aClass = reloadClass(name);
						casheRecord.updateTimestamp();
					}
					return casheRecord.aClass;
				} else {
					//ClassCashe.ObjectInstance objectInstance = new ClassCashe.ObjectInstance(reloadClass(name));
					ClassCashe.ObjectInstance objectInstance = new ClassCashe.ObjectInstance(delegatedLoad(name));
					ClassCashe.getClassesCache().put(name, objectInstance);
					return objectInstance.aClass;
				}
			}
		} else {
			return delegatedLoad(name);
		}
	}

	public Class reloadClass(String name) throws ClassNotFoundException {
		try {
			File f = ClassCashe.getFile(name);
			InputStream inStream = new FileInputStream(f);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DebugRequestProcessor.readInputSteamOutputStream(inStream, outputStream);
			byte[] buf = outputStream.toByteArray();

			Class aClass = defineClass(name, buf, 0, buf.length);
			return aClass;
		} catch (IOException e) {
			//throw new ClassNotFoundException("Can't load:" + name, e);
			return delegatedLoad(name);
		}
	}

	private Class delegatedLoad(String name) throws ClassNotFoundException {
		return deligated.loadClass(name);
	}

	public boolean isInReloadedPackage(String name) throws ClassNotFoundException {
		//return name.matches(Config.getString("reload.packages"));
		return true;
		//return !name.matches(".*dcl\\.util.*");
	}

	public static void setLoader() {
		if (!(Thread.currentThread().getContextClassLoader() instanceof AlwaysLoadClassLoader)){
			Thread.currentThread().setContextClassLoader(new AlwaysLoadClassLoader());
		}
	}
	public static void unsetLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader() ;
		if (loader instanceof AlwaysLoadClassLoader){
			Thread.currentThread().setContextClassLoader(((AlwaysLoadClassLoader)loader).deligated);
		}
	}
}
