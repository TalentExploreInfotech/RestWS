package com.persistent;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/student")
public class RestResource {
	@GET
	public void getStudent(@QueryParam("roll") String rollNum)
	{
		System.out.println("Student is retrieved::queryParam "+rollNum);
	}
	@GET
	@Path("/getRoll/{rollNo}")
	public void getStudentRoll(@PathParam("rollNo") String roll)
	{
		System.out.println("roll::"+roll+ "  is retrieved");
	}
	@POST
	@Path("/createStudent/roll::{roll}")
	public void createStudent(@PathParam("roll") String rollNum)
	{
		System.out.println("Student with roll number::"+rollNum+" is created");
	}
	@PUT
	public void updateStudent()
	{
		System.out.println("Student is updated");
	}
	@DELETE
	public void  deleteStudent(@MatrixParam("roll") String rollNum)
	{
		System.out.println("Student with roll::"+ rollNum+" is deleted");
	}
}
