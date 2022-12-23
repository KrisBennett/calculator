import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input:");
        String expression = scanner.nextLine();
        String totalResult = calc(expression);
        System.out.println("Output: \n" + totalResult);
    }

    public static String calc(String input) throws Exception {
        int result = 0;
        boolean numbersAreRomanNumerics = false;
        Main romanNumeric = new Main();
        String[] element = input.split(" ");
        if (element.length != 3) {
            throw new Exception();
        }

        Integer firstNumber = 0;
        Integer secondNumber = 0;

        try {
            firstNumber = Integer.valueOf(element[0]);
            secondNumber = Integer.valueOf(element[2]);
        } catch (NumberFormatException e) {
            try {
                firstNumber = romanNumeric.romanToArabicConverter(element[0]);
                secondNumber = romanNumeric.romanToArabicConverter(element[2]);
                numbersAreRomanNumerics = true;
            } catch (NumberFormatException e1) {
            }
        }

        if ((firstNumber < 1) || (firstNumber > 10) || (secondNumber < 1) || (secondNumber > 10)) {
            throw new Exception();
        }

        String operation = element[1];
        switch (operation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = firstNumber / secondNumber;
                break;
            default:
                throw new Exception();
        }

        String totalResult;

        if (numbersAreRomanNumerics) {
            if (result < 0) {
            throw new Exception();
        } else {
            totalResult = romanNumeric.arabicToRomanConverter(result);
        }
        } else  {
             totalResult = String.valueOf(result);
        }
        return totalResult;
    }

    enum RomanNumeral {
        I(1),
        IV(4),
        V(5),
        IX(9),
        X(10),
        XL(40),
        L(50),
        XC(90),
        C(100);


        int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }


        static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    Integer romanToArabicConverter(String element) {
        String romanNumeral = element.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException();
        }

        return result;
    }

    String arabicToRomanConverter(int number) {
        if ((number <= 0) || (number > 100)) {
            throw new IllegalArgumentException();
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    }







