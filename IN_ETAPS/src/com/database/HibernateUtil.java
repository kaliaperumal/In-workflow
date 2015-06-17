package com.database;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil 
{
	private static SessionFactory sessionFactory=null;	
	public static SessionFactory getSessionFactory() throws HibernateException
	{
		if(sessionFactory==null)
		{
			System.out.println("SessionFactory is null.........");
			sessionFactory=new Configuration().configure().buildSessionFactory();
			System.out.println("After Build SessionFactory...........");
		}
		return sessionFactory;		
	}
	public static void setSessionFactory(SessionFactory sessionFactory)
	{
		HibernateUtil.sessionFactory=sessionFactory;
	}
}