package com.bleconnect.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bleconnect.dao.BLEDao;
import com.fasterxml.jackson.databind.JsonNode;

@CrossOrigin
@RestController
@EnableAutoConfiguration
public class BLEController {

	BLEDao tdo = new BLEDao();
	
	@RequestMapping("/")
	String home1() {
		return "Hello World Gurunath from Cloud Foundry : m.gurunathpai!";
	}
	
	@RequestMapping(value="/accel", method=RequestMethod.POST)
	public String saveAccelData(@RequestBody JsonNode jsonNode ) {
		try {
			System.out.println("accel data : " + jsonNode);
			HashMap<Integer, String> hm = new HashMap<Integer, String>();
			hm.put(new Integer(1), jsonNode.get("accelx").toString());
			hm.put(new Integer(2), jsonNode.get("accely").toString());
			hm.put(new Integer(3), jsonNode.get("accelz").toString());
			hm.put(new Integer(4), new Timestamp(new Date().getTime()).toString());
			tdo.insertAccel(hm);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
	
	@RequestMapping(value="/gyro", method=RequestMethod.POST)
	public String saveGyroData(@RequestBody JsonNode jsonNode ) {
		try {
			System.out.println("gyro data : " + jsonNode);
			HashMap<Integer, String> hm = new HashMap<Integer, String>();
			hm.put(new Integer(1), jsonNode.get("gyrox").toString());
			hm.put(new Integer(2), jsonNode.get("gyroy").toString());
			hm.put(new Integer(3), jsonNode.get("gyroz").toString());
			hm.put(new Integer(4), new Timestamp(new Date().getTime()).toString());
			tdo.insertGyro(hm);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
	
	@RequestMapping(value="/saveLatLong", method=RequestMethod.POST)
	public String saveLatLong(@RequestBody JsonNode jsonNode ) {
		try {
			System.out.println("lat long : " + jsonNode);
			HashMap<Integer, String> hm = new HashMap<Integer, String>();
			hm.put(new Integer(1), jsonNode.get("latval").toString());
			hm.put(new Integer(2), jsonNode.get("longval").toString());
			hm.put(new Integer(3), new Timestamp(new Date().getTime()).toString());
			tdo.saveLatlong(hm);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
	
	@RequestMapping(value="/getLatLong", method=RequestMethod.GET)
	public @ResponseBody String getLatLong() {
		try {
			return tdo.getLatLongVal().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/getLatLongMultiple", method=RequestMethod.POST)
	public @ResponseBody String getLatLongMultiple(@RequestBody JsonNode jsonNode) {
		try {
			System.out.println("lat long : " + jsonNode);
			HashMap<Integer, String> hm = new HashMap<Integer, String>();
			hm.put(new Integer(1), jsonNode.get("limit").toString());
			
			return tdo.getLatLongValMultiple(hm).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/getGyroAccel", method=RequestMethod.GET)
	public @ResponseBody String getAccelGyroData() {
		try {
			return tdo.mergeAccelGyro().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
