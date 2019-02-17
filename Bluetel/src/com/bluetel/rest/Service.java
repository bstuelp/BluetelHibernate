package com.bluetel.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.bluetel.entity.Customer;
import com.bluetel.entity.Register;
import com.google.gson.Gson;

@Path("/meter-read")
public class Service implements Serializable, Iservice
{
	private static final long serialVersionUID = 1L;

	@GET
	@Path("/present/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getMetrics(@PathParam("id") int id)
	{
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		Session session = cfg.buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Customer c = session.get(Customer.class, id);

		t.commit();
		session.close();

		if (c == null)
		{
			return(Response.status(Response.Status.BAD_REQUEST).entity("Error: Data not found.").build());
		}
		
		return(Response.status(Response.Status.OK).entity(c).build());
	}

	@POST
	@Path("/store")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response setMeterReaders(String json)
	{
		/*Valid JSON*/
		if (!this.validJson(json))
		{
			return(Response.status(Response.Status.BAD_REQUEST).entity("Error: Invalid Json.").build());
		}
		else
		{
			/*Parse json -> Java Object (Customer)*/
			Customer customer = this.parseJsonJavaObject(json);
			
			if (customer != null)
			{
				Configuration cfg = new Configuration();
				cfg.configure("hibernate.cfg.xml");
	
				Session session = cfg.buildSessionFactory().openSession();
				Transaction t = session.beginTransaction();
	
				session.save(customer);
	
				t.commit();
				session.close();
			}
			else
			{
				return(Response.status(Response.Status.BAD_REQUEST).entity("Error Parse Json to Java Object!").build());
			}
		}

		return(Response.status(Response.Status.OK).entity("Stored!").build());
	}

	@Override
	public boolean validJson(String json)
	{
		Gson gson = new Gson();

		try
		{
			gson.fromJson(json, Object.class);
			
			return (true);
		}
		catch(com.google.gson.JsonSyntaxException ex)
		{
			return false;
		}
	}

	@Override
	public Customer parseJsonJavaObject(String strJson)
	{
		ObjectMapper mapper = new ObjectMapper();

		Customer customer = null;

		try
		{
			customer = mapper.readValue(strJson, Customer.class);

			List<Register> list = customer.getRegister();

			for (Register l: list)
			{
				l.setCustomer(customer);
			}			
		}
		catch (JsonGenerationException e)
		{
			return (null);
		}
		catch (JsonMappingException e)
		{
			return (null);
		}
		catch (IOException e)
		{
			return (null);
		}

		return (customer);
	}
}