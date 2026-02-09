package net.sam.dcl.util;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.property.*;
import org.hibernate.HibernateException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.List;
import java.util.ArrayList;
import java.beans.PropertyDescriptor;

/**
 * Result transformer that allows to transform a result to
 * a user specified class which will be populated via setter
 * methods or fields matching the alias names.
 *
 * <pre>
 * List resultWithAliasedBean = s.createCriteria(Enrolment.class)
 *			.createAlias("student", "st")
 *			.createAlias("course", "co")
 *			.setProjection( Projections.projectionList()
 *					.add( Projections.property("co.description"), "courseDescription" )
 *			)
 *			.setResultTransformer( new AliasToBeanResultTransformer(StudentDTO.class) )
 *			.list();
 *
 *  StudentDTO dto = (StudentDTO)resultWithAliasedBean.get(0);
 *	</pre>
 *
 * @author max
 *
 */
public class ToBeanResultTransformer implements ResultTransformer {

	private final Class[] resultClasses;
	private List<Setter>[] setters;
	private List<Getter>[] getters;
	private PropertyAccessor[] srcPropertyAccessor;
	private PropertyAccessor[] dstPropertyAccessor;
	private ReflectionManager reflectionManager = new JavaReflectionManager();
	private boolean initialized = false;

	public ToBeanResultTransformer(Class resultClasses) {
		this(new Class[]{resultClasses});
	}
	public ToBeanResultTransformer(Class[] resultClasses) {
		if(resultClasses ==null) throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClasses = resultClasses;
		srcPropertyAccessor = new PropertyAccessor[resultClasses.length];
		dstPropertyAccessor = new PropertyAccessor[resultClasses.length];
		for (int i = 0; i < resultClasses.length; i++) {
			srcPropertyAccessor[i] = new ChainedPropertyAccessor(new PropertyAccessor[] { PropertyAccessorFactory.getPropertyAccessor(resultClasses[i],null), PropertyAccessorFactory.getPropertyAccessor("field")});

		}
		setters = new ArrayList[resultClasses.length];
		getters = new ArrayList[resultClasses.length];
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object[] result = new Object[resultClasses.length];
		int i = 0;
		try {
			if(!initialized) {
				for (; i < resultClasses.length; i++) {
					Object srcObj = tuple[i];
					PropertyDescriptor srcDescriptors[] =	PropertyUtils.getPropertyDescriptors(srcObj);
					dstPropertyAccessor[i] = new ChainedPropertyAccessor(new PropertyAccessor[] { PropertyAccessorFactory.getPropertyAccessor(srcObj.getClass(),null), PropertyAccessorFactory.getPropertyAccessor("field")});
					setters[i] = new ArrayList<Setter>();
					getters[i] = new ArrayList<Getter>();
					for (int j = 0; j < srcDescriptors.length; j++) {
						try {
							setters[i].add(srcPropertyAccessor[i].getSetter(resultClasses[i], srcDescriptors[j].getName()));
							getters[i].add(srcPropertyAccessor[i].getGetter(srcObj.getClass(), srcDescriptors[j].getName()));
						} catch (PropertyNotFoundException e) {}
					}
				}
				initialized = true;
			}
			for (i = 0; i < resultClasses.length; i++) {
				result[i] = resultClasses[i].newInstance();
				for (int j = 0; j < setters[i].size(); j++) {
					setters[i].get(j).set(result[i],getters[i].get(j).get(tuple[i]),null);
				}

			}
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClasses[i].getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClasses[i].getName());
		}
		if (result.length == 1){
			return result[0];
		}
		return result;
	}

	public List transformList(List collection) {
		return collection;
	}

}
