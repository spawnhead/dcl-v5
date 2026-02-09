package net.sam.dcl.db;

import java.util.Hashtable;

/**
 * The storage of opened connections .
 */

public class VDbConnectionStorage extends Hashtable {

	public VDbConnectionStorage() {
		super();
	}
	/**
	 * Removes an ojbject from the dictionary.
	 */
	/*
		public Object remove( final Object key)
		{

		 new Thread()
								{
									public void run ()
									{
										//  Remove connection from dictionary
										//  Key is unique
										VDbConnection conn = (VDbConnection)VDbConnectionStorage.super.remove(key);

										if( conn != null )
										{
											//to db tracer
										}
									}
								}.start();

		 return null;
		}*/
/**
 * Puts an object to the dictionary
 */
	/*
		public   Object put( final Object key , final Object value)
		{
		 new Thread()
								{
									public void run ()
									{
										// Put connection into dictionary of active objects
										// Key is unique
										VDbConnection conn = (VDbConnection)VDbConnectionStorage.super.get(key);
										if( conn == null )
										{
											VDbConnectionStorage.super.put(key,value);
											// to db tracer
										}
										else
										{
										 // to db tracer > duplicate connection
										}
									}
								}.start();
		 return null;
		}*/
}
