package com.carloan.presentation.controller;

import javafx.stage.Stage;

public class Controller {
    static protected Stage stage;
    protected Controller(){}
    static {
        stage = new Stage();
    }
    public static void setStage(Stage stage){
        Controller.stage = stage;
    }
}
