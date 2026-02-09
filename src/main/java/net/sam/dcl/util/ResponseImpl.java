package net.sam.dcl.util;

import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.io.*;

/**
 * @author: DG
 * Date: Jan 27, 2006
 * Time: 10:50:36 AM
 */
public class ResponseImpl extends HttpServletResponseWrapper {
  private PrintWriter printWriter = null;
  private StringWriter stringWriter = null;
  private OutputStream stream = null;
  private ServletOutputStream servletStream = null;
  public ResponseImpl(HttpServletResponse response, StringWriter stringWriter, OutputStream stream){
    super(response);
    this.stringWriter = stringWriter;
    this.stream = stream;
  }
  public PrintWriter getWriter() throws IOException {
    if (servletStream!=null){
      throw new IllegalStateException("OutputStream already getted");
    }
    if (printWriter == null)
      printWriter = new PrintWriter(stringWriter);
    return printWriter;
  }

  public ServletOutputStream getOutputStream() throws IOException{
    if (printWriter!=null){
      throw new IllegalStateException("Writer already getted");
    }
    if (servletStream == null)
      servletStream = new ServletOutputStreamImpl(stream);
    return servletStream;
  }
  public boolean isWriterGetted(){
    return printWriter != null;
  }
}

class ServletOutputStreamImpl extends ServletOutputStream{
  private OutputStream stream = null;
  public ServletOutputStreamImpl(OutputStream stream){
    this.stream = stream;
  }
  public void write(int b) throws IOException{
    stream.write(b);
  }
}

