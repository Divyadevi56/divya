package com.cg.donor.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		ResultSet resultSet=null;
		Statement st=null;
		//System.out.println("i sm here");
		DonorBean donorBean=new DonorBean();
		st=connection.createStatement();
		resultSet=st.executeQuery("select * from donor_details where donor_id="+donorId+"");
		while(resultSet.next())
		{
			
		donorBean.setDonorId(resultSet.getString(1));
		donorBean.setDonorName(resultSet.getString(2));
		donorBean.setAddress(resultSet.getString(3));
		donorBean.setPhoneNumber(resultSet.getString(4));
		donorBean.setDonationDate(resultSet.getDate(5));
		donorBean.setDonationAmount(resultSet.getInt(6));
		//System.out.println(donorbean);
		}
		
		
		return donorBean;

	}

    @Override
	public List retriveAll() throws DonorException, ClassNotFoundException, IOException, SQLException {
    	Connection con=DBConnection.getConnection();
		int donorCount = 0;
		
		PreparedStatement ps=null;
		ResultSet resultset = null;
		
		List<DonorBean> donorList=new ArrayList<DonorBean>();
		try
		{
			ps=con.prepareStatement("SELECT * FROM donor_details");
			resultset=ps.executeQuery();
			
			while(resultset.next())
			{	
				DonorBean bean=new DonorBean();
				bean.setDonorId(resultset.getString(1));
				bean.setDonorName(resultset.getString(2));
				bean.setAddress(resultset.getString(3));
				bean.setPhoneNumber(resultset.getString(4));
				bean.setDonationDate(resultset.getDate(5));
				bean.setDonationAmount(resultset.getDouble(6));
				donorList.add(bean);
				
				donorCount++;
			}			
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new DonorException("Tehnical problem occured. Refer log");
		}
		
		finally
		{
			try 
			{
				resultset.close();
				ps.close();
				con.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				throw new DonorException("Error in closing db connection");

			}
		}
		
		if( donorCount == 0)
			return null;
		else
			return donorList;
	
}
}