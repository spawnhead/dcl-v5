package net.sam.dcl.taglib.table;

/**
 * Editable checker.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.0 $
 */
public interface IReadOnlyChecker {

    /**
     * Checker context.
     * @param ctx ctx
     * @return status
     */
    public boolean check(ReadOnlyCheckerContext ctx) throws Exception;

}
