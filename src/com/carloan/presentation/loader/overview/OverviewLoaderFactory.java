package com.carloan.presentation.loader.overview;

public enum OverviewLoaderFactory {
	INTANCE;
	public static OverviewLoader getInstance(){
		return new ModelOverviewLoader();
	}
}
