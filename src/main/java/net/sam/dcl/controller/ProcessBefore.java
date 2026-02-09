package net.sam.dcl.controller;

import org.apache.struts.action.ActionForward;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * User: Dima
 * Date: Feb 18, 2005
 * Time: 11:35:59 AM
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ProcessBefore {
}