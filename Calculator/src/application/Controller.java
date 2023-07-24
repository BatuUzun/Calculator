package application;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class Controller implements Initializable {

	@FXML
	GridPane gridPane;
	@FXML
	TextField answerTextField;
	private String number = "";
	private DecimalFormat df = new DecimalFormat("#,##0");
	private boolean typed = false;
	private String firstNumber;
	private String secondNumber;
	private String lastOperation;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Button[] numberButtons = new Button[10];
		Button negativeButton;
		Button timesButton;
		Button equalButton;
		Button plusButton;
		Button minusButton;
		Button divisonButton;
		Button decimalButton;
		Button clearButton;
		Button deleteButton;
		Label calculatorNameLabel;

		firstNumber = null;
		secondNumber = null;
		lastOperation = null;
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.UK));
		answerTextField.setFocusTraversable(false);
		// answerTextField.setStyle("");
		answerTextField
				.setStyle("" + "-fx-text-box-border: transparent;" + "-fx-focus-color: -fx-control-inner-background ;"
						+ "-fx-faint-focus-color: -fx-control-inner-background;");

		int row = 3;
		int column = 0;
		Font font = new Font(25);
		int width = 149;
		int height = 81;

		calculatorNameLabel = new Label("Batu" + "\nCalculator");
		calculatorNameLabel.setAlignment(Pos.CENTER);
		calculatorNameLabel.setFont(font);
		calculatorNameLabel.setMinSize(width, height);
		gridPane.add(calculatorNameLabel, 0, 0);

		for (int i = 1; i < 10; i++) {
			numberButtons[i] = new Button(String.valueOf(i));
			if (i % 3 == 1 && i != 1) {
				row--;
				column = 0;
			}

			gridPane.add(numberButtons[i], column, row);
			column++;
		}
		numberButtons[0] = new Button("0");
		gridPane.add(numberButtons[0], 1, 4);

		for (int i = 0; i < 10; i++) {
			numberButtons[i].setFont(font);
			numberButtons[i].setMinSize(width, height);
			numberButtons[i].setFocusTraversable(false);
			final Integer innerI = i;
			numberButtons[i].setOnAction(e -> setTextField(numberButtons[innerI].getText()));
		}

		negativeButton = new Button("+/-");
		negativeButton.setFont(font);
		negativeButton.setMinSize(width, height);
		negativeButton.setFocusTraversable(false);
		negativeButton.setOnAction(e -> negativeNumber());
		gridPane.add(negativeButton, 0, 4);

		timesButton = new Button("*");
		timesButton.setFont(font);
		timesButton.setMinSize(width, height);
		timesButton.setFocusTraversable(false);
		timesButton.setOnAction(e -> multiplication());
		gridPane.add(timesButton, 3, 1);

		equalButton = new Button("=");
		equalButton.setFont(font);
		equalButton.setMinSize(width, height);
		equalButton.setFocusTraversable(false);
		equalButton.setOnAction(e -> equal());
		equalButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
		gridPane.add(equalButton, 3, 4);

		plusButton = new Button("+");
		plusButton.setFont(font);
		plusButton.setMinSize(width, height);
		plusButton.setFocusTraversable(false);
		plusButton.setOnAction(e -> addition());
		gridPane.add(plusButton, 3, 3);

		minusButton = new Button("-");
		minusButton.setFont(font);
		minusButton.setMinSize(width, height);
		minusButton.setFocusTraversable(false);
		minusButton.setOnAction(e -> subtraction());
		gridPane.add(minusButton, 3, 2);

		divisonButton = new Button("÷");
		divisonButton.setFont(font);
		divisonButton.setMinSize(width, height);
		divisonButton.setFocusTraversable(false);
		divisonButton.setOnAction(e -> division());
		gridPane.add(divisonButton, 3, 0);

		decimalButton = new Button(".");
		decimalButton.setFont(font);
		decimalButton.setMinSize(width, height);
		decimalButton.setFocusTraversable(false);
		decimalButton.setOnAction(e -> addDecimal());
		gridPane.add(decimalButton, 2, 4);

		clearButton = new Button("C");
		clearButton.setFont(font);
		clearButton.setMinSize(width, height);
		clearButton.setFocusTraversable(false);
		clearButton.setOnAction(e -> clear());
		gridPane.add(clearButton, 2, 0);

		deleteButton = new Button("CE");
		deleteButton.setFont(font);
		deleteButton.setMinSize(width, height);
		deleteButton.setFocusTraversable(false);
		deleteButton.setOnAction(e -> reset());
		gridPane.add(deleteButton, 1, 0);
	}

	public void setTextField(String clickedNumber) {
		if (number.equalsIgnoreCase("-0")) {
			number = "-";
		}
		if ((number.equalsIgnoreCase("-0") || number.equalsIgnoreCase("0")) && clickedNumber.equalsIgnoreCase("0"))
			;
		else {
			number += clickedNumber;
			typed = true;
		}

		if (String.valueOf(Math.round(Double.parseDouble(number))).length() >= 15) {
			answerTextField.setText("ERROR!");
		}
		/*
		 * if(number.length() >= 16) { //answerTextField.setText("ERROR!"); }
		 */
		else {
			answerTextField.setText(number);
		}
	}

	public void addDecimal() {
		if (true) {

		}
		if (!number.contains("."))
			number += ".";
		answerTextField.setText(number);
	}

	public void negativeNumber() {

		StringBuffer nmb = new StringBuffer(answerTextField.getText());
		if (!nmb.toString().contains("-"))
			nmb.insert(0, "-");
		else
			nmb.deleteCharAt(0);
		number = nmb.toString();
		typed = true;
		firstNumber = null;
		answerTextField.setText(number);
	}

	public void addition() {
		if (lastOperation == null)
			lastOperation = "+";
		if (firstNumber == null) {
			firstNumber = answerTextField.getText();
			number = "";
			answerTextField.setText("0");

		} else {
			if (typed) {
				secondNumber = answerTextField.getText();
				number = String.valueOf(Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber));
				if (Double.parseDouble(number) % 1 == 0) {
					number = number.substring(0, number.indexOf("."));
				}
				firstNumber = number;
				answerTextField.setText(number);
				number = "";
			}

		}
		lastOperation = "+";
		typed = false;

	}

	public void subtraction() {
		if (lastOperation == null)
			lastOperation = "-";
		if (firstNumber == null) {
			firstNumber = answerTextField.getText();
			number = "";
			answerTextField.setText("0");

		} else {
			if (typed) {
				secondNumber = answerTextField.getText();
				number = String.valueOf(Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber));
				if (Double.parseDouble(number) % 1 == 0) {
					number = number.substring(0, number.indexOf("."));
				}
				firstNumber = number;
				answerTextField.setText(number);
				number = "";
			}

		}
		lastOperation = "-";
		typed = false;
	}

	public void multiplication() {
		if (lastOperation == null)
			lastOperation = "*";
		if (firstNumber == null) {
			firstNumber = answerTextField.getText();
			number = "";
			answerTextField.setText("0");

		} else {
			if (typed) {
				secondNumber = answerTextField.getText();
				number = String.valueOf(Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber));
				if (Double.parseDouble(number) % 1 == 0) {
					number = number.substring(0, number.indexOf("."));
				}
				firstNumber = number;
				answerTextField.setText(number);
				number = "";
			}

		}
		lastOperation = "*";
		typed = false;
	}

	public void division() {
		if (lastOperation == null)
			lastOperation = "/";
		if (firstNumber == null) {
			firstNumber = answerTextField.getText();
			number = "";
			answerTextField.setText("0");

		} else {
			if (typed) {
				secondNumber = answerTextField.getText();
				number = String.valueOf(Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber));
				if (Double.parseDouble(number) % 1 == 0) {
					number = number.substring(0, number.indexOf("."));
				}
				firstNumber = number;
				answerTextField.setText(number);
				number = "";
			}

		}
		lastOperation = "/";
		typed = false;
	}

	public void clear() {

		firstNumber = null;
		secondNumber = null;
		number = "";
		answerTextField.setText("0");
	}
	
	public void reset() {
		number = "";
		answerTextField.setText("0");
	}

	public void equal() {

		if (firstNumber != null || secondNumber != null) {
			switch (lastOperation) {

			case "+":

				addition();
				break;

			case "-":

				subtraction();
				break;

			case "*":

				multiplication();
				break;

			case "/":

				division();
				break;

			}
		}
	}
}
