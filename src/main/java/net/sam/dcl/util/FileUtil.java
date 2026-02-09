package net.sam.dcl.util;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author DG
 *         Date: 15-Jul-2008
 *         Time: 21:33:35
 */
public class FileUtil {
	public static final void safeClose(InputStream is){
		if (is!=null){
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	public static final void safeClose(OutputStream os){
		if (os!=null){
			try {
				os.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
