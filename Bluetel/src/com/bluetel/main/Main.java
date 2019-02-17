package com.bluetel.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.bluetel.entity.Customer;
import com.bluetel.entity.Register;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Hello, Hibernate!");
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		Session session = cfg.buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		List<Register> list = new ArrayList<>();

		Customer c = new Customer();
		c.setMpxn("123");
		c.setReadDate("2019-02-17 12:55:55");
		c.setSerialNumber("999");
		c.setRegister(list);

		Register r = new Register();
		r.setRegisterId("555");
		r.setType("NIGHT");
		r.setValue("191919");
		r.setCustomer(c);
		list.add(r);

		Register r2 = new Register();
		r2.setRegisterId("55532");
		r2.setType("ANYTIME");
		r2.setValue("0231");
		r2.setCustomer(c);
		list.add(r2);

		session.save(c);

		t.commit();
		session.close();
	}
}
