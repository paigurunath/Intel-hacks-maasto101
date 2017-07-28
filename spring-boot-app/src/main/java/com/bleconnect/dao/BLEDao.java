package com.bleconnect.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import com.bleconnect.datasource.Query;
import com.bleconnect.datasource.QueryInteractor;
import com.google.gson.JsonObject;

public class BLEDao {

	QueryInteractor qi = null;
 	
 	public String insertGyro(HashMap values) {
 		
 		try {
 			qi = new QueryInteractor();
 			qi.getArrayList(Query.INSERT_GYRO, values);
 			
 			return "Data saved successfully";
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		return null;
 	}
 	
 	public String insertAccel(HashMap values) {
 		
 		try {
 			qi = new QueryInteractor();
 			qi.getArrayList(Query.INSERT_ACCEL, values);
 			
 			return "Data saved successfully";
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		return null;
 	}
 	
 	public String saveLatlong(HashMap values) {
 		
 		try {
 			qi = new QueryInteractor();
 			qi.getArrayList(Query.INSERT_LAT_LONG, values);
 			
 			return "SUCCESS";
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		return null;
 	}

 	public String viewData() {
 		
 		try {
 			qi = new QueryInteractor();
 			List<Object> listObject = qi.getList(Query.VIEW_GYRO);
 			
 			Object[] obj = (Object[])listObject.get(1);
 			System.out.println(obj[0].toString()+ ","+obj[1].toString()+ "," + obj[2].toString() + "--" + obj.length);
 			return obj[0].toString();
 			
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		return null;
 	}
 	
 	public JSONArray getLatLongValMultiple(HashMap values) {
 		try {
 			qi = new QueryInteractor();
 			List<Object> listObject = qi.getArrayListOnIn(Query.VIEW_LAT_LONG_MULTIPLE, values);
 			
 			int listSize = listObject.size();
 			
 			JSONArray jsonarray = new JSONArray();
 			JSONArray jsonarraySub;
 			for(int k=1; k<listSize; k++) {
 				jsonarraySub = new JSONArray();
 				 
 				Object[] obj = (Object[])listObject.get(k);
 				jsonarraySub.put(new Double(obj[0].toString()));
 				jsonarraySub.put(new Double(obj[1].toString()));
 				
 				jsonarray.put(jsonarraySub);
 				
 			}
 			return jsonarray;
 			
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		return null;
 	}
		
 	public JSONArray getLatLongVal() {
 		
 		try {
 			JSONArray jsonarray = new JSONArray();
 			qi = new QueryInteractor();
 			List<Object> listObjectGyro = qi.getList(Query.VIEW_LAT_LONG);
 			
 			Object[] obj = (Object[])listObjectGyro.get(1);
 			jsonarray.put(new Double(obj[0].toString()));
 			jsonarray.put(new Double(obj[1].toString()));
 			
 			return jsonarray;
 			
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		return null;
 	}

 	public JSONArray mergeAccelGyro() {
 		
 		try {
 			qi = new QueryInteractor();
 			List<Object> listObjectGyro = qi.getList(Query.VIEW_GYRO);
 			
 			List<Object> listObjectAccel = qi.getList(Query.VIEW_ACCEL);
 			
 			JSONArray jsonarray = new JSONArray();
 			JSONArray jsonarraySub;
 			for(int k=1; k<listObjectAccel.size(); k++) {
 				 jsonarraySub = new JSONArray();
 				 
 				Object[] obj = (Object[])listObjectAccel.get(k);
 				jsonarraySub.put(new Double(obj[0].toString()));
 				jsonarraySub.put(new Double(obj[1].toString()));
 				jsonarraySub.put(new Double(obj[2].toString()));
 				
 				Object[] obj1 = (Object[])listObjectGyro.get(k);
 				jsonarraySub.put(new Double(obj1[0].toString()));
 				jsonarraySub.put(new Double(obj1[1].toString()));
 				jsonarraySub.put(new Double(obj1[2].toString()));
 				
 				jsonarray.put(jsonarraySub);
 				
 			}
 			return jsonarray;
 			
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		return null;
 	}
}
