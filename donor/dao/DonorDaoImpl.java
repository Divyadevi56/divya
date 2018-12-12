package com.cg.donor.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.cg.donor.bean.DonorBean;
import com.cg.donor.exception.DonorException;
import com.cg.donor.util.DBConnection;

public class DonorDaoImpl implements IDonorDAO{

	@Override
	public String addDonor(DonorBean donor) throws DonorException, ClassNotFoundException, IOException, SQLException {
		Connection connection=DBConnection.getConnection();
	
	PreparedStatement pst=null;
	//PreparedStatement pst1=null;
	ResultSet resultSet=null;
	String donorId=null;
	int queryResult=0;
	try {
	 pst=connection.prepareStatement("insert into Donor_Details values(donorId_sequence.nextVal,?,?,?,SYSDATE,?)");
	 pst.setString(1,donor.getDonorName());
	 pst.setString(2,donor.getAddress());
	 pst.setString(3,donor.getPhoneNumber());
	 pst.setInt(4,(int) donor.getDonationAmount());
	 pst.executeUpdate();
	 Statement st=null;
	 st=connection.createStatement();
	 
	resultSet=st.executeQuery("select max(donor_Id) from Donor_Details");
	while(resultSet.next())
	{
		
		/*System.out.println(resultSet.getString(1));
		System.out.println(resultSet.getString(2));
		System.out.println(resultSet.getString(3));
		System.out.println(resultSet.getString(4));
		System.out.println(resultSet.getDate(5));
		System.out.println(resultSet.getInt(6));*/
		donorId=resultSet.getString(1);
		
	}
	
	 
	 
	}catch(SQLException sql)
	{
		System.out.println(sql);
	}
	
		return donorId;
	}

	@Override
	public DonorBean viewDonorDetails(String donorId) throws DonorException, ClassNotFoundException, IOException, SQLException {
		
		Connection connection=DBConnection.getConnection();
		 Statement st=null;
		 ResultSet resultSet=null;
		 st=connection.createStatement();
		 
		resultSet=st.executeQuery("select * from Donor_Details");
		while(resultSet.next())
		{
			System.out.println(resultSet.getString(1));
			System.out.println(resultSet.getString(2));
			System.out.println(resultSet.getString(3));
			System.out.println(resultSet.getString(4));
			System.out.println(resultSet.getDate(5));
			System.out.println(resultSet.getInt(6));
			//donorId=resultSet.getString(1);
			
		}
		return null;
	}

	@Override
	public List retriveAll() throws DonorException {
		// TODO Auto-generated method stub
		return null;
	}

}
