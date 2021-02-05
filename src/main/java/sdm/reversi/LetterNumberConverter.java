package sdm.reversi;

import java.util.stream.IntStream;

public class LetterNumberConverter {

    public static int convertLettersToNumber(String letters){
        String s = new StringBuffer(letters).reverse().toString();
        return IntStream.range(0,s.length()).map(i -> (int) ((s.charAt(i)-'A'+1)*Math.pow(26,i))).sum()-1;
    }

    public static String convertNumberToLetter(int number){
        StringBuilder reversedString = new StringBuilder();
        number++;
        while(number!=0){
            reversedString.append((char) (number % 26 + 'A' - 1));
            number = number/26;
        }
        return reversedString.reverse().toString();
    }

}
