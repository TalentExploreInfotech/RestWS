package com.persistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.persistent.model.Student;

@Path("/StudentJaxB")
public class RestResourceJaxB {
	private Map<Integer,Student> students= new HashMap<Integer,Student>();
	public RestResourceJaxB()
	{
		students.put(1,new Student(1,"Nishant"));
		students.put(2,new Student(2,"Niranjan"));
		students.put(3,new Student(3,"Aks"));
	}
	@GET
	@Path("/{rollNo}")
	@Produces(MediaType.TEXT_XML)
	public Student getStudent(@PathParam("rollNo") int roll)
	{
		System.out.println("student with ::"+roll +" is retreived");
		if(students.containsKey(roll))
		{
			return students.get(roll);
		}
			return new Student();
	}
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String create (@QueryParam("rollNo") int roll, @QueryParam("name") String name)
	{
		System.out.println("student  is created");
		if(students.containsKey(roll))
		{
			return "Not created";
		}
		students.put(roll, new Student(roll, name));
		return "created";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/CreateForm")
	public String createStudent (@FormParam("rollNo") int roll, @FormParam("name") String name)
	{
		System.out.println("student  is created");
		if(students.containsKey(roll))
		{
			return "Not created";
		}
		students.put(roll, new Student(roll, name));
		return "created";
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	//http://localhost:8080/RestWSElementary/StudentJaxB;rollNo=3
	public Student deleteRoll(@MatrixParam("rollNo") int roll)
	{
		Student student = new Student();
		if(students.containsKey(roll))
		{
			student = students.get(roll);
			students.remove(roll);
		}
		return student;
	}
	
}
