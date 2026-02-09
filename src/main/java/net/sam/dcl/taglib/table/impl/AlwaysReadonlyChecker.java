package net.sam.dcl.taglib.table.impl;

import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;

/**
 * @author: DG
 * Date: 17.05.2006
 * Time: 16:40:56
 */
public class AlwaysReadonlyChecker implements IReadOnlyChecker {
  public boolean check(ReadOnlyCheckerContext ctx) throws Exception {
    return true;
  }
}
