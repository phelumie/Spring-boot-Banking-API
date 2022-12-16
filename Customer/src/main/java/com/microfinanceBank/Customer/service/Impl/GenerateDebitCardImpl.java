package com.microfinanceBank.Customer.service.Impl;

import com.microfinanceBank.Customer.service.GenerateDebitCard;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateDebitCardImpl implements GenerateDebitCard {

    private Random random = new Random(System.currentTimeMillis());

    /**
     * Generates a random valid credit card number. For more information about
     * the credit card number generation algorithms and credit card numbers
     * refer to <a
     * href="http://euro.ecom.cmu.edu/resources/elibrary/everycc.htm">Everything
     * you ever wanted to know about CC's</a>, <a
     * href="http://www.darkcoding.net/credit-card/">Graham King's blog</a>, and
     * <a href="http://codytaylor.org/2009/11/this-is-how-credit-card-numbers-are-generated.html"
     * >This is How Credit Card Numbers Are Generated</a>
     *
     * @param bin
     *            The bank identification number, a set digits at the start of the credit card
     *            number, used to identify the bank that is issuing the credit card.
     * @param length
     *            The total length (i.e. including the BIN) of the credit card number.
     * @return
     *            A randomly generated, valid, credit card number.
     */
    @Override
    public String generateCardNumber(String bin, int length) {

        // The number of random digits that we need to generate is equal to the
        // total length of the card number minus the start digits given by the
        // user, minus the check digit at the end.
        int randomNumberLength = length - (bin.length() + 1);

        StringBuilder builder = new StringBuilder(bin);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = this.random.nextInt(10);
            builder.append(digit);
        }

        // Do the Luhn algorithm to generate the check digit.
        int checkDigit = this.getCheckDigit(builder.toString());
        System.out.println("checkDigit "+checkDigit);
        builder.append(checkDigit);

        return builder.toString();
    }

    @Override
    public int generateCvvNumber() {
        return random.nextInt(999);
    }

    /**
     * Generates the check digit required to make the given credit card number
     * valid (i.e. pass the Luhn check)
     *
     * @param number
     *            The credit card number for which to generate the check digit.
     * @return The check digit required to make the given credit card number
     *         valid.
     */
    private int getCheckDigit(String number) {

        // Get the sum of all the digits, however we need to replace the value
        // of the first digit, and every other digit, with the same digit
        // multiplied by 2. If this multiplication yields a number greater
        // than 9, then add the two digits together to get a single digit
        // number.
        //
        // The digits we need to replace will be those in an even position for
        // card numbers whose length is an even number, or those is an odd
        // position for card numbers whose length is an odd number. This is
        // because the Luhn algorithm reverses the card number, and doubles
        // every other number starting from the second number from the last
        // position.
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        // The check digit is the number required to make the sum a multiple of
        // 10.
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }

}