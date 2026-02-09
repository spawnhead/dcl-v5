package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.*;
import net.sam.dcl.form.NomenclatureActionBean;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author DG
 *         Date: 02-Dec-2007
 *         Time: 18:28:01
 */
public class NomenclatureProducesImportAction extends SmartImportAction{
	NomenclatureActionBean nomenclatureActionBean;


	public void afterLoad(IActionContext context) {
		nomenclatureActionBean = (NomenclatureActionBean) context.getRequest().getSession().getAttribute("Nomenclature");
	}

	public void afterProcess() {
	}

	protected void processRow(HSSFRow row, IActionContext context, List badRows) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			HSSFCell cell;
			DboProduce produce = new DboProduce();

			DboCategory category = (DboCategory) session.load(DboCategory.class, getCat_id_integer());
			produce.setCategory(category);
			session.save(produce);

			cell = row.getCell((short)0);
			produce.setName(getAsString(cell));
			if (StringUtil.isEmpty(produce.getName())){
				throw new Exception("Name is empty");
			}

			cell = row.getCell((short)1);
			produce.setType(getAsString(cell));

			cell = row.getCell((short)2);
			produce.setParams(getAsString(cell));

			cell = row.getCell((short)3);
			produce.setAddParams(getAsString(cell));

			cell = row.getCell((short)4);
			String unit = getAsString(cell);
			if (!StringUtil.isEmpty(unit)){
				produce.setUnit((DboUnit) session.createCriteria(DboUnit.class)
						.add(Restrictions.eq( "name", getAsString(cell)))
						.uniqueResult()
				);
				if (produce.getUnit() == null){
					throw new Exception("Wrong unit:"+unit);
				}
			} else {
				throw new Exception("Unit is empty");
			}

			cell = row.getCell((short)5);
			String cusCode = getAsString(cell);
			if (!StringUtil.isEmpty(cusCode)){
				DboCustomCode customCode = (DboCustomCode) HibernateUtil.getSessionFactory().getCurrentSession()
						.getNamedQuery("produce-last-custom-code")
						.setString("code", cusCode)
						.uniqueResult();
				if (customCode != null){
					produce.setCustomCode(customCode);
				} else {
					throw new Exception("Wrong custom code:"+cusCode);
				}
			}

			cell = row.getCell((short)6);
			String enName = getAsString(cell);
			if (!StringUtil.isEmpty(enName)){
				DboProduceLanguage pLanguage = new DboProduceLanguage(produce, DboLanguage.DAO.loadByLangCode("EN"));
				pLanguage.setName(enName);
				produce.addProduceLanguage(pLanguage);
			}

			cell = row.getCell((short)7);
			String deName = getAsString(cell);
			if (!StringUtil.isEmpty(deName)){
				DboProduceLanguage pLanguage = new DboProduceLanguage(produce, DboLanguage.DAO.loadByLangCode("DE"));
				pLanguage.setName(deName);
				produce.addProduceLanguage(pLanguage);
			}

			cell = row.getCell((short)8);
			String stufCat = getAsString(cell);
			cell = row.getCell((short)9);
			String catNumber = getAsString(cell);

			if (!StringUtil.isEmpty(stufCat)) {
				produce.addCatalogNumber(
						new DboCatalogNumber(produce,
																 (DboStuffCategory) session.createCriteria(DboStuffCategory.class)
																		 .add(Restrictions.eq("name", stufCat))
																		 .uniqueResult(),
																 catNumber)
				);
			} else {
				throw new Exception("StuffCategory is empty");
			}

			session.getTransaction().commit();
		} finally {
			if (session != null){
				session.close();
			}
		}
	}
	public Integer getCat_id_integer() {
		return StringUtil.isEmpty(nomenclatureActionBean.getCat_id()) ? null : Integer.parseInt(nomenclatureActionBean.getCat_id());
	}

}
