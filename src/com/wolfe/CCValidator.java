package com.wolfe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Created by Jeremy on 10/28/2016.
 */

public class CCValidator extends JFrame {
    private JPanel rootPanel;
    private JTextField creditCardTextField;
    private JButton validateButton;
    private JButton quitButton;
    private JLabel validMessageField;

    public CCValidator() {

        super("Credit Card Validator");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ccNumber = creditCardTextField.getText();
                boolean valid = isValidCreditCard(ccNumber);
                if (valid) {
                    validMessageField.setText("Credit card number is valid");
                } else {
                    validMessageField.setText("Credit card number is NOT valid");
                }
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    public boolean isValidCreditCard(String ccNumber) {

        // covert the string input into a char array
        char[] numericChars = ccNumber.toCharArray();

        // define a new integer array with a length based on number of digits in string/char array
        int[] digits = new int[numericChars.length];

        // convert the char data to integer and populate the numbers array
        for (int i = 0; i < numericChars.length; i++) {
            digits[i] = numericChars[i] - 48;
        }

        // Visa ccCards must start with a 4 and be exactly 16 digits long
        if (digits[0] != 4 || digits.length != 16) {
            return false;
        }

        int sum = 0;
        // scan through each element of the digits array
        // starting with position 0 and proceeding to every other element, double the value of the element
        // sum up the digits, if greater then 10, sum the individual digits - e.g. if value 12, sum 1 + 2
        // starting with position 1 and proceeding to every other element, sum the element value
        for (int i = 0; i < digits.length; i++) {

            // second version of check digit algorithm - shorter and more efficient but less readable (?)
            // some ideas from http://www.freeformatter.com/credit-card-number-generator-validator.html

            if (i % 2 == 0) {  // array element position 0, 2, 4, 6, etc.

                int doubled = digits[i] * 2;

                if (doubled < 10) {
                    sum += doubled;
                }
                if (doubled >= 10) {
                    sum += doubled - 9;  // this is the equivalent of adding the two digits
                }
            } else {  // array element position 1, 3, 5, 7, 9, etc.
                sum += digits[i];
            }
        }
        return (sum % 10 == 0); // if the sum mod 10 is zero, return true, otherwise return false
    }

}
