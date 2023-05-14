package com.example.hill_cipher;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import javafx.scene.control.ChoiceBox;

import javafx.scene.control.Label;

import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;



import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;

import java.util.ResourceBundle;



public class HelloController implements Initializable {





    @FXML

    private Button encryptButton;

    @FXML

    private Button encryptButton1;



    @FXML

    private Button DecryptBtn;

    @FXML

    private Button decryptButton;

    @FXML

    private Label enter;



    @FXML

    private TextField encryptedTextOutput;



    @FXML

    private TextField matrix00;



    @FXML

    private TextField matrix002;



    @FXML

    private TextField matrix003;



    @FXML

    private TextField matrix01;



    @FXML

    private TextField matrix012;



    @FXML

    private TextField matrix013;



    @FXML

    private TextField matrix022;



    @FXML

    private TextField matrix023;



    @FXML

    private TextField matrix033;



    @FXML

    private TextField matrix10;



    @FXML

    private TextField matrix102;



    @FXML

    private TextField matrix103;



    @FXML

    private TextField matrix11;



    @FXML

    private TextField matrix112;



    @FXML

    private TextField matrix113;



    @FXML

    private TextField matrix122;



    @FXML

    private TextField matrix123;



    @FXML

    private TextField matrix133;



    @FXML

    private TextField matrix202;



    @FXML

    private TextField matrix203;



    @FXML

    private TextField matrix212;



    @FXML

    private TextField matrix213;



    @FXML

    private TextField matrix222;



    @FXML

    private TextField matrix223;



    @FXML

    private TextField matrix233;



    @FXML

    private TextField matrix303;



    @FXML

    private TextField matrix313;



    @FXML

    private TextField matrix323;



    @FXML

    private TextField matrix333;



    @FXML

    private GridPane matrixInput2x2;



    @FXML

    private GridPane matrixInput3x3;



    @FXML

    private GridPane matrixInput4x4;



    @FXML

    private ChoiceBox<Integer> matrixSizeChoiceBox;



    @FXML

    private TextField plainTextInput;





    ObservableList<Integer> sizes = FXCollections.observableArrayList(2, 3, 4);



    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

        matrixSizeChoiceBox.setItems(sizes);

        matrixSizeChoiceBox.setOnAction(e->{

            switch (matrixSizeChoiceBox.getValue()) {

                case 2:

                    setMatrixVisibility(false);

                    matrixInput2x2.setVisible(true);

                    break;

                case 3:

                    setMatrixVisibility(false);

                    matrixInput3x3.setVisible(true);

                    break;

                case 4:

                    setMatrixVisibility(false);

                    matrixInput4x4.setVisible(true);

                    break;

            }

        });

        setMatrixVisibility(false);

    }





    @FXML

    private void handleEncryptButton(ActionEvent event) {

        int[][] matrix = getMatrixFromInput();

        String plainText = plainTextInput.getText();

        String encryptedText = HillCipher.encrypt(plainText, matrix);

        encryptedTextOutput.setText(encryptedText);

    }





    @FXML

    private void handleDecryptButton(ActionEvent event) {

        ArrayList<ArrayList<Integer>> matrix = getArrayListFromMatrix();

        String ciphertext = plainTextInput.getText();

        String plaintext = HillCipher.decryption(ciphertext, matrix);

        encryptedTextOutput.setText(plaintext);

    }
