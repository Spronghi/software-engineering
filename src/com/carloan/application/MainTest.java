package com.carloan.application;


import com.carloan.service.location.LocationService;

public class MainTest {

	public static void main(String[] args) {
		LocationService.getAll().stream().forEach(p->System.out.println(p.toString()));
	}
}
