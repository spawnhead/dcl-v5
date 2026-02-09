package net.sam.dcl.api;

import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.XMLResource;

import javax.servlet.ServletContext;

public final class ApiDbUtil {
  private ApiDbUtil() {}

  public static XMLResource sqlResource(ServletContext ctx) {
    return (XMLResource) StoreUtil.getApplication(ctx, XMLResource.class);
  }

  public static VDbConnection getConnection() throws Exception {
    return VDbConnectionManager.getVDbConnection();
  }
}
