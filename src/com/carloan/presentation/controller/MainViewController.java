package com.carloan.presentation.controller;

import com.carloan.presentation.loader.LoaderFactory;

import javafx.fxml.FXML;

public class MainViewController extends Controller {
	public MainViewController() {}
	@FXML
	private void initialize(){
		LoaderFactory.getInstance("HomeView").load();
	}
}
