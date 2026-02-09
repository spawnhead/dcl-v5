package net.sam.dcl.test;

import org.apache.struts.action.ActionForward;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.taglib.table.model.PageableDataHolder;

/**
 * @author: DG
 * Date: Aug 7, 2005
 * Time: 2:46:28 PM
 */
public class TestGridAction extends DBAction implements IProcessBefore {
    class HandleActionShowForm implements ActionHandler {
      public ActionForward process(IActionContext context) throws Exception {
        return null;
      }
    }

    public ActionForward processBefore(IActionContext context) throws Exception {

      context.getActionProcessor().addActionHandler( PageableDataHolder.NEXT_PAGE, new ActionHandler() {
        public ActionForward process(IActionContext context) throws Exception {
          TestGridForm form = (TestGridForm) context.getForm();
          form.getGridCtrl2().incPage();
          setGridData(context);
          return context.getMapping().getInputForward();
        }
      });
      context.getActionProcessor().addActionHandler(PageableDataHolder.PREV_PAGE, new ActionHandler() {
        public ActionForward process(IActionContext context) throws Exception {
          TestGridForm form = (TestGridForm) context.getForm();
          form.getGridCtrl2().decPage();
          setGridData(context);
          return context.getMapping().getInputForward();
        }
      });

      TestGridForm form = (TestGridForm) context.getForm();
      VResultSet resultSet = DAOUtils.executeQuery(context, "test-sim", null);
      //form.getGrid().setFetcher(new ResultSetPagebleFetcher(resultSet));
      //context.getActionProcessor().addActionHandler(ActionControllerHelper.ACTION_SHOW_FORM, new HandleActionShowForm());

      setGridData(context);
      return null;
    }
  private void setGridData(IActionContext context) throws Exception {
    TestGridForm form = (TestGridForm) context.getForm();
    DAOUtils.fillGrid(context,form.getGridCtrl2(),"test-sim",TestGridForm.TestSim.class,null,null);
/*    TestGridForm form = (TestGridForm) context.getForm();
    VResultSet resultSet = DAOUtils.executeQuery(context, "test-sim", null);
    form.getGridCtrl2().setDataList(DAOUtils.resultSet2List(resultSet,TestGridForm.TestSim.class,null,form.getGridCtrl2().getPage(),3));
    form.getGridCtrl2().setHasNextPage(resultSet.next());*/
  }
  public ActionForward execute(IActionContext context) throws Exception {
/*    TestGridForm form  = (TestGridForm) context.getForm();
    VResultSet resultSet = executeQuery(context,"test-account",null);
    Fetcher fetcher = new ResultSetFetcher(resultSet,TestOuter.class);
    fetcher.setGridDataReceiver(form.getGrid());
    fetcher.fetch();
    //form.setGrid(new GridDataRS(resultSet,TestOuter.class));*/
    return context.getMapping().getInputForward();
  }
  }
