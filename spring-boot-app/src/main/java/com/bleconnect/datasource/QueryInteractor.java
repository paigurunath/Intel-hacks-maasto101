package com.bleconnect.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class QueryInteractor {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private boolean flag = false;
	 private boolean toput=false;
	public ArrayList getArrayListOnIn(String pQryStmt, HashMap valueMap) {
		ArrayList lo_ResAList = new ArrayList();
		ResultSet lo_ResultSet1      = null;
		logger.debug("inside getArrayListOnIn() ");
		logger.debug("Query String "+pQryStmt);
		DBConnectionManager moDbConn=null;
		Connection conlocal = null;
		PreparedStatement lo_PrepareStmt = null;
		try {
			moDbConn = DBConnectionManager.getDbCon();
			conlocal = moDbConn.conn;
			//moDbConn.setAutoCommit(false);
			Statement stmt = conlocal.createStatement();
			ResultSet rs = stmt.executeQuery(pQryStmt);
			if(rs != null )
			{
				Object[] listMetaObj = null;
				ResultSetMetaData lo_RsMeta      = null;
				int lo_ColCount     = 0;
				int l_RowCount      = 0;
				int i=0;
				lo_ResultSet1 	 = rs;
				lo_RsMeta        = rs.getMetaData();
				lo_ColCount      = lo_RsMeta.getColumnCount();
				// Getting Meta Data for the Query
				listMetaObj = new Object[lo_ColCount];
				for ( i=0; i<lo_ColCount ; i++)	{
					listMetaObj[i] = lo_RsMeta.getColumnLabel(i+1);
				}
				lo_ResAList.add(l_RowCount++, listMetaObj.toString());

				// Getting DataPart
				while (lo_ResultSet1.next())	{
					Object[] listColumnObj = new Object[lo_ColCount];
					for ( i=0; i<lo_ColCount; i++ )   {
						listColumnObj[i] = lo_ResultSet1.getObject(i+1);
						//               logger.debug("Resuleset Value"+listColumnObj[i].toString());
					}
					lo_ResAList.add(l_RowCount++, listColumnObj);
				}
				logger.debug("Fetch from database");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.fatal("Failed to fetch from database");
		}
		finally
		{

			DBUtility.closeResultSet(lo_ResultSet1);
			//DBUtility.closeConnection(moDbConn);

		}
		return lo_ResAList;
	}

	public ArrayList getArrayList(String pQryStmt, HashMap valueMap) {

		ArrayList lo_ResAList = new ArrayList();
		ResultSet lo_ResultSet1      = null;
		PreparedStatement lo_PrepareStmt1 = null;
		PreparedStatement lo_PrepareStmt2 = null;
		logger.debug("inside getArrayList() ");
		logger.debug("Query String "+pQryStmt);
		DBConnectionManager moDbConn=null;
		Connection conlocal = null;
		PreparedStatement lo_PrepareStmt = null;
		try {

			moDbConn = DBConnectionManager.getDbCon();
			conlocal = moDbConn.conn;
			lo_PrepareStmt1 = conlocal.prepareStatement(pQryStmt);
		
			if(valueMap != null )
			{
				for (Iterator itr = valueMap.keySet().iterator(); itr.hasNext(); ) {
					Object key = itr.next();
					if (key instanceof Integer) {
						logger.info("HashMap key for the query="+ key.toString());
						Object value = valueMap.get(key);
						lo_PrepareStmt1.setObject(((Integer) key).intValue(), value);
						logger.debug("HashMap Value for the query "+value.toString());
					}
				}
				logger.debug("Hash MAp Retrieved");
			}
			logger.debug("Query String Before execute "+pQryStmt);
			System.out.println("Query String Before execute "+pQryStmt);
			int flag1= lo_PrepareStmt1.executeUpdate();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.fatal("Failed to fetch from database");
		}
		finally
		{

			DBUtility.closeResultSet(lo_ResultSet1);
			DBUtility.closePreparedStatement(lo_PrepareStmt1);
			//DBUtility.closeConnection(moDbConn);


		}
		return lo_ResAList;
	}
	
	
	public ArrayList getArrayListOnCondition(String pQryStmt, HashMap valueMap) {

		ArrayList lo_ResAList = new ArrayList();
		ResultSet lo_ResultSet1      = null;
		PreparedStatement lo_PrepareStmt1 = null;
		PreparedStatement lo_PrepareStmt2 = null;
		logger.debug("inside getArrayList() ");
		logger.debug("Query String "+pQryStmt);
		DBConnectionManager moDbConn=null;
		Connection conlocal=null;
		PreparedStatement lo_PrepareStmt = null;
		try {

			moDbConn = DBConnectionManager.getDbCon();
			conlocal = moDbConn.conn;
			//moDbConn.setAutoCommit(false);

			lo_PrepareStmt1 = conlocal.prepareStatement(pQryStmt);
			

			if(valueMap != null )
			{
				for (Iterator itr = valueMap.keySet().iterator(); itr.hasNext(); ) {
					Object key = itr.next();
					if (key instanceof Integer) {
						logger.info("HashMap key for the query="+ key.toString());
						Object value = valueMap.get(key);
						lo_PrepareStmt1.setObject(((Integer) key).intValue(), value);
						logger.debug("HashMap Value for the query "+value.toString());
					}
				}
				logger.debug("Hash MAp Retrieved");
			}
			logger.debug("Query String Before execute "+pQryStmt);
			ResultSet rs = lo_PrepareStmt1.executeQuery();
			logger.debug("The value of flag = " + flag);
			if(rs != null)
			{
				Object[] listMetaObj = null;
				ResultSetMetaData lo_RsMeta      = null;
				int lo_ColCount     = 0;
				int l_RowCount      = 0;
				int i=0;

				lo_ResultSet1     = lo_PrepareStmt1.getResultSet();
				lo_RsMeta        = lo_ResultSet1.getMetaData();
				lo_ColCount      = lo_RsMeta.getColumnCount();

				// Getting Meta Data for the Query
				listMetaObj = new Object[lo_ColCount];
				for ( i=0; i<lo_ColCount ; i++)	{
					listMetaObj[i] = lo_RsMeta.getColumnLabel(i+1);
				}
				lo_ResAList.add(l_RowCount++, listMetaObj.toString());

				// Getting DataPart
				while (lo_ResultSet1.next())	{
					Object[] listColumnObj = new Object[lo_ColCount];
					for ( i=0; i<lo_ColCount; i++ )   {
						listColumnObj[i] = lo_ResultSet1.getObject(i+1);
						//               logger.debug("Resuleset Value"+listColumnObj[i].toString());
					}
					lo_ResAList.add(l_RowCount++, listColumnObj);
				}
				logger.debug("Fetch from database");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.fatal("Failed to fetch from database");
		}
		finally
		{

			DBUtility.closeResultSet(lo_ResultSet1);
			DBUtility.closePreparedStatement(lo_PrepareStmt1);
			//DBUtility.closeConnection(moDbConn);


		}
		return lo_ResAList;
	}
	public ArrayList getList(String pQryStmt) {

		ArrayList lo_ResAList = new ArrayList();
		ResultSet lo_ResultSet1      = null;
		PreparedStatement lo_PrepareStmt1 = null;
		logger.debug("inside getList() ");
		logger.debug("Query String "+pQryStmt);
		DBConnectionManager moDbConn=null;
		Connection conlocal = null;
		PreparedStatement lo_PrepareStmt = null;
		try {

			moDbConn = DBConnectionManager.getDbCon();
			conlocal = moDbConn.conn;

			//moDbConn.setAutoCommit(false);

			lo_PrepareStmt1 = conlocal.prepareStatement(pQryStmt);
		
			logger.debug("Query String Before execute "+pQryStmt);
			boolean flag1= lo_PrepareStmt1.execute();
			flag=flag1;
			logger.debug("The value of flag = " + flag);
			if(flag1)
			{
				Object[] listMetaObj = null;
				ResultSetMetaData lo_RsMeta      = null;
				int lo_ColCount     = 0;
				int l_RowCount      = 0;
				int i=0;

				lo_ResultSet1     = lo_PrepareStmt1.getResultSet();
				lo_RsMeta        = lo_ResultSet1.getMetaData();
				lo_ColCount      = lo_RsMeta.getColumnCount();

				// Getting Meta Data for the Query
				listMetaObj = new Object[lo_ColCount];
				for ( i=0; i<lo_ColCount ; i++)	{
					listMetaObj[i] = lo_RsMeta.getColumnLabel(i+1);
				}
				lo_ResAList.add(l_RowCount++, listMetaObj);

				// Getting DataPart
				while (lo_ResultSet1.next())	{
					Object[] listColumnObj = new Object[lo_ColCount];
					for ( i=0; i<lo_ColCount; i++ )   {
						listColumnObj[i] = lo_ResultSet1.getObject(i+1);
						//               logger.debug("Resuleset Value"+listColumnObj[i].toString());
					}
					lo_ResAList.add(l_RowCount++, listColumnObj);
				}
				logger.debug("Fetch from database");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.fatal("Failed to fetch from database");
		}
		finally
		{

			DBUtility.closeResultSet(lo_ResultSet1);
			DBUtility.closePreparedStatement(lo_PrepareStmt1);
			//DBUtility.closeConnection(moDbConn);


		}
		return lo_ResAList;
	}
	
}
