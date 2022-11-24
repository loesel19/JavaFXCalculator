package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    //string array to hold our current expression
    String[] expression = {"n", "n", "n"};
    //boolean variable to know if we are in the middle of creating an expression
    //default val of true so that our 0 that is in the textField by default will be overwritten when the
    //first number button is hit
    boolean resetText = false;

    @FXML
    private TextField txtCalc;
    //had to set focus transversable property on sceneBuilder to false so that the default text was not highlighted
    //when the program started running

    @FXML
    void getCalculation(ActionEvent event) {
        //simply call the calculation method
calculate();
    }

    //I know that this method is above the 30 line limit, but there are only like 25-30 lines of code
    //and the rest are comments since i had a lot of issues with error handling for users putting in the
    //negative sign multiple times in a row and a few other things
    @FXML
    void getNegative(ActionEvent event) {
//we have to see whether or not we have to put the negative number in the expression string or
        //put it in the text field
        //also need to make sure that we dont have to reset textField, and if we should do it resest it
        if (resetText == true){
            txtCalc.clear();
            resetText = false;
        }
        if (isExpression()){
            //we have to be able to change a value that is already negative to a positive value
            //so just check to see if there is a negative in the expression
            if(txtCalc.getText().contains("-")){
                //if there is a number after the string we have to extract the number from the string
                //without taking the "-", if there is not a number we just reset it to an empty string
                //if the length of the string is > 1 than there is a number or decimal in there
                if (txtCalc.getText().length() > 1){
                    String[] str = txtCalc.getText().split("-");
                    txtCalc.setText(str[1]);
                }else{
                    //no number se we can just reset the textField to blank
                    txtCalc.setText("");
                }

            }else{
                //need to add negative in
                txtCalc.setText("-" + txtCalc.getText());
            }


        }else{
            //same logic as the above if for displaying a positive number instead of a number with
            //two - when the user trys to get the negative of a negative number
            if (txtCalc.getText().contains("-")){
                //need to make sure that the text in the textField is longer than just the "-"
                if (txtCalc.getText().length() > 1){
                    String[] str = txtCalc.getText().split("-");
                    expression[0] = str[1];
                    txtCalc.setText(expression[0]);
                }else{
                    //we have just the negative in the textField and the array
                    //so we just need to reset both of those to blank string
                    txtCalc.setText("");
                    expression[0] = "n";
                }

            }else{
                //no negative is in the textField and array so just add one
                txtCalc.setText("-" + txtCalc.getText());
                expression[0] = "-" + expression[0];
            }

        }

    }

    @FXML
    void getPercentage(ActionEvent event) {
        //because the round method contains all the logic for output and writing to the string
        //simply call that with our current number / 100
round(Double.parseDouble(txtCalc.getText()) / 100);
    }

    @FXML
    void setAdd(ActionEvent event) {
//just have to see if we have a full expression first
        if(isExpression()){
            //if we already have an expression in the array we need to calculate the first result
    calculate();
    //now we pretty much do the same logic as we will do in the else statement
    expression[0] = txtCalc.getText();
    expression[1] = "+";
    //show the result in the textField
    txtCalc.setText(expression[0]);
    //set the reset boolean to true so that we can clear the textField when the next number is input after
            //an operator button "+,-,X,/" are hit
    resetText = true;

        }else{
            //we have to put the number into the first spot in the array
            //i had a logic error here that sent an empty string into the expression[0] spot when one of the
            //"+ , -, X, /" buttons are hit before a number is input so i had to fix it
            //the != "" is to error handle for that issue
            if (txtCalc.getText() != ""){
                expression[0] = txtCalc.getText();
                //make sure the number is rounded
                //now we put the + in the string and reset the txt field
                expression[1] = "+";
                txtCalc.setText("");
            }else
                System.out.println("no number has been input");


        }
    }

    @FXML
    void setSubtract(ActionEvent event) {
        //same logic as setAdd
        if(isExpression()){
            //if we already have an expression in the array we need to calculate the first result
            calculate();
            //now we pretty much do the same logic as we will do in the else statement
            expression[0] = txtCalc.getText();
            expression[1] = "-";
            //show the result in the textField
            txtCalc.setText(expression[0]);
            //reset boolean to true
            resetText = true;
        }else{
            //copy and pasted from the addition setter with the  + changed to a minus
            if (txtCalc.getText() != ""){
                expression[0] = txtCalc.getText();
                //make sure the number is rounded
                //now we put the + in the string and reset the txt field
                expression[1] = "-";
                txtCalc.setText("");
            }else
                System.out.println("no number has been input");
        }
    }

    @FXML
    void setDivision(ActionEvent event) {
//same logic as setAdd
        if(isExpression()){
            //if we already have an expression in the array we need to calculate the first result
            calculate();
            //now we pretty much do the same logic as we will do in the else statement
            expression[0] = txtCalc.getText();
            expression[1] = "/";
            //show the result in the textField
            txtCalc.setText(expression[0]);
            //reset boolean to true
            resetText = true;
        }else{
            //again copied from above
            if (txtCalc.getText() != ""){
                expression[0] = txtCalc.getText();
                //make sure the number is rounded
                //now we put the + in the string and reset the txt field
                expression[1] = "/";
                txtCalc.setText("");
            }else
                System.out.println("no number has been input");
        }
    }

    @FXML
    void setMultiply(ActionEvent event) {
//same logic as setAdd
        if(isExpression()){
            //if we already have an expression in the array we need to calculate the first result
            calculate();
            //now we pretty much do the same logic as we will do in the else statement
            expression[0] = txtCalc.getText();
            expression[1] = "X";
            //show the result in the textField
            txtCalc.setText(expression[0]);
            //reset boolean to true
            resetText = true;
        }else{
            //again copied from above
            if (txtCalc.getText() != ""){
                expression[0] = txtCalc.getText();
                //make sure the number is rounded
                //now we put the + in the string and reset the txt field
                expression[1] = "X";
                txtCalc.setText("");
            }else
                System.out.println("no number has been input");
        }
    }

    @FXML
    void setAllClear(ActionEvent event) {
//just clear the text field and our expression string
        txtCalc.setText("");
        expression = new String[]{"n", "n", "n"};
        //set our reset variable to false
        resetText = false;
        //we also reset our boolean variable that lets us know if we have have a full expression
    }

    @FXML
    void setDecimal(ActionEvent event) {
        //first we have to see if it is going to be added to the first or second number
        //we also need to see if we have to reset the textField and reset it if we need to
        if (resetText == true){
            txtCalc.clear();
            resetText = false;
        }
if(isExpression()){
    //see if there is already a decimal in the number
    if(expression[2].contains(".")){
        //do not place another decimal
        System.out.println("already has decimal");
    }else{
        //put a decimal in the text field and string
        expression[2] = expression[2] + ".";
        txtCalc.setText(txtCalc.getText() + ".");
    }
}else{
    //we do the same thing but in the 1st array position
    if(expression[0].contains(".")){
        //do not place another decimal
        System.out.println("already has decimal");
    }else{
        //put a decimal in the text field and string
        expression[0] = expression[2] + ".";
        txtCalc.setText(txtCalc.getText() + ".");
    }
}
    }

    @FXML
    void setNumber(ActionEvent event) {
        //this is the easiest place to do the logic for clearing the textField after a result has been displayed
        //so that after a user input '2 + 2 +' it displays '4', but when the next number is hit it will reset the text
        //in the textField instead of reading '4nextInput'
        //use boolean field to see if we need to reset the text
        if(resetText == true){
            //clear the text field
            txtCalc.clear();
            //need to display a number
            //get the button that gave us the number
            Object node = event.getSource();
            Button btnNum = (Button) node;
            txtCalc.setText(txtCalc.getText() + btnNum.getText());
            //set the reset text field to false
            //because we do not want to reset the text on the next numeric input
            resetText = false;
        }else {
            //need to display a number
            //get the button that gave us the number
            Object node = event.getSource();
            Button btnNum = (Button) node;
            txtCalc.setText(txtCalc.getText() + btnNum.getText());
        }
    }

    //method to see if number is whole
    public boolean isWhole(double num){
        boolean whole;
        //if a number mod 1 returns 0 it is a whole number
        if (num % 1 == 0) {
            whole = true;
        } else
            whole = false;
        return whole;
    }
    //need a method to check and see if we are on the first number or not
    public boolean isExpression(){
        if (expression[1].contains("n")){
            return false;
        }else
            return true;
    }
    //need a method to round a number
    //im going to have the method set the rounded value to the textField and string so that I do not have
    //to implement a ton of clunky code around this method in other methods, because each method that needs to round
    //will first have to see if the number is whole
    public void round(double num){
        //if we already have an expression we know that we just have to change the txtField
        if(isExpression()){
            if(isWhole(num)){
                //its just a whole number so we cast it as an int
        txtCalc.setText((int)num + "");
            }else{
                //round the number
                txtCalc.setText(((double)Math.round(num * 100)/100) + "");
            }
        }else{
            //we have to round the value in both the txtField and the array
            //but its still the same logic as the if statement
            if(isWhole(num)){
                //its just a whole number so we cast it as an int
                txtCalc.setText((int)num + "");
                expression[0] = String.valueOf((int)num);
            }else{
                //round the number, typecasted as double so it goes to two decimal places
                txtCalc.setText(((double)Math.round(num * 100)/100) + "");
                expression[0] = String.valueOf((double)(Math.round(num * 100)/100));
            }
        }

    }
    //the next four methods will do the mathematical functions on two numbers
    public double add(){
        //add the two numbers
        double sum = Double.parseDouble(expression[0]) + Double.parseDouble(txtCalc.getText());
        //set the textField to the sum
        txtCalc.setText(sum + "");
        //now we have to reset the elements of our array
        //we need to first see
        expression = new String[]{String.valueOf(sum), "n","n"};
        System.out.println(sum);
        return sum;
    }
    //next three are just copy and pasted with the operand changed in the in the first statement
    public double subtract(){
        //add the two numbers
        double sum = Double.parseDouble(expression[0]) - Double.parseDouble(txtCalc.getText());
        //set the textField to the sum
        txtCalc.setText(sum + "");
        //now we have to reset the elements of our array
        expression = new String[]{String.valueOf(sum), "n","n"};
        System.out.println(sum);
        return sum;
    }
    public double multiply(){
        //add the two numbers
        double sum = Double.parseDouble(expression[0]) * Double.parseDouble(txtCalc.getText());
        //set the textField to the sum
        txtCalc.setText(sum + "");
        //now we have to reset the elements of our array
        expression = new String[]{String.valueOf(sum), "n","n"};
        System.out.println(sum);
        return sum;
    }
    public double divide(){
        //add the two numbers
        double sum = Double.parseDouble(expression[0]) / Double.parseDouble(txtCalc.getText());
        //set the textField to the sum
        txtCalc.setText(sum + "");
        //now we have to reset the elements of our array
        expression = new String[]{String.valueOf(sum), "n","n"};
        System.out.println(sum);
        return sum;
    }
    //will be used to choose which mathematical function we will do
    public void calculate(){
        //pretty much just see what kind of expression we are doing if there is an expression to do
        //if there is not one to do we do nothing
        if(isExpression()){
            //see what the value of expression[1] is and executing the coressponding method
            if(expression[1] == "+"){
                //nothing else to do than run add() because it will take care of the textField and array
                round(add());
            }
            if(expression[1] == "-"){
                round(subtract());
            }
            if(expression[1] == "X"){
                round(multiply());
            }
            if(expression[1] == "/"){
                round(divide());
            }
        }else //we cant do the math
            System.out.println("nothing to do, not an expression");
    }

}
//      ---** END PROJECT CODE **---
//----------*****scene builder generated controller objects*****----------
//dont need these
/*
@FXML
    private Button btnAC;

    @FXML
    private Button btnPercent;

    @FXML
    private Button btnNegative;

    @FXML
    private Button btnDivision;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSubtract;

    @FXML
    private Button btnMultiply;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnSeven;

    @FXML
    private Button btnEight;

    @FXML
    private Button btnNine;

    @FXML
    private Button btnFour;

    @FXML
    private Button btnFive;

    @FXML
    private Button btnSix;

    @FXML
    private Button btnOne;

    @FXML
    private Button btnTwo;

    @FXML
    private Button btnThree;

    @FXML
    private Button btnZero;

    @FXML
    private Button btnDecimal;
 */