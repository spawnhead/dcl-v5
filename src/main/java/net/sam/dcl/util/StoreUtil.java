package net.sam.dcl.util;

import net.sam.dcl.beans.GoodsCirculation;
import net.sam.dcl.beans.OrderExecutedProduces;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User: Dima
 * Date: Mar 22, 2005
 * Time: 3:03:41 PM
 */
public class StoreUtil
{
  public static Object getRequest(HttpServletRequest request, Class clazz)
  {
    return request.getAttribute(clazz.getName());
  }

  public static void putRequest(HttpServletRequest request, Object object)
  {
    request.setAttribute(object.getClass().getName(), object);
  }

  public static void deleteRequest(HttpServletRequest request, Class clazz)
  {
    request.setAttribute(clazz.getName(), null);
  }

  public static Object getSession(HttpServletRequest request, Class clazz)
  {
    return getSession(request, clazz.getName());
  }

  public static Object getSession(HttpServletRequest request, String key)
  {
    return getSession(request.getSession(), key);
  }

  public static Object getSession(HttpSession session, String key)
  {
    return session.getAttribute(key);
  }

  public static void putSession(HttpServletRequest request, Object object)
  {
    putSession(request, object, object.getClass().getName());
  }

  public static void putSession(HttpServletRequest request, Object object, String key)
  {
    request.getSession().setAttribute(key, object);
  }

  public static void deleteSession(HttpServletRequest request, Class clazz)
  {
    request.getSession().setAttribute(clazz.getName(), null);
  }

  public static Object getApplication(ServletContext context, Class clazz)
  {
    return context.getAttribute(clazz.getName());
  }

  public static void putApplication(ServletContext context, Object object)
  {
    context.setAttribute(object.getClass().getName(), object);
  }

  public static void deleteApplication(ServletContext context, Class clazz)
  {
    context.setAttribute(clazz.getName(), null);
  }

  public static GoodsCirculation getGoodsCirculation(HttpServletRequest request)
  {
    GoodsCirculation goodsCirculation = (GoodsCirculation) StoreUtil.getSession(request, GoodsCirculation.class.getName());
    if (goodsCirculation == null)
    {
      return null;
    }
    return goodsCirculation;
  }

  public static OrderExecutedProduces getOrderExecutedProduces(HttpServletRequest request)
  {
    OrderExecutedProduces orderExecutedProduces = (OrderExecutedProduces) StoreUtil.getSession(request, OrderExecutedProduces.class.getName());
    if (orderExecutedProduces == null)
    {
      return null;
    }
    return orderExecutedProduces;
  }
}
