package com.bleconnect.datasource;

public class Query {

	public static String INSERT_GYRO = "INSERT INTO gyro (gx,gy,gz,createdat) VALUES (?,?,?,?)";
	public static String INSERT_ACCEL = "INSERT INTO accel (ax,ay,az,createdat) VALUES (?,?,?,?)";
	
	public static String VIEW_GYRO = "SELECT gx, gy, gz FROM gyro ORDER BY createdat desc LIMIT 8";
	public static String VIEW_ACCEL = "SELECT ax, ay, az FROM accel ORDER BY createdat desc LIMIT 8";

	public static String INSERT_LAT_LONG = "INSERT INTO latlong (latval,longval,createdat) VALUES (?,?,?)";
	public static String VIEW_LAT_LONG = "SELECT latval, longval FROM latlong ORDER BY createdat desc LIMIT 1";
	public static String VIEW_LAT_LONG_MULTIPLE = "SELECT latval, longval FROM latlong ORDER BY createdat desc LIMIT ?";
	
}
