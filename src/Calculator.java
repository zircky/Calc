import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Calculator {
  public static int romanToArabic(String roman) {
    HashMap<Character, Integer> romanMap = new HashMap<>();
    romanMap.put('I', 1);
    romanMap.put('V', 5);
    romanMap.put('X', 10);
    romanMap.put('L', 50);
    romanMap.put('C', 100);
    romanMap.put('D', 500);
    romanMap.put('M', 1000);

    int result = 0;
    int prevValue = 0;

    for (int i = roman.length() - 1; i >= 0; i--) {
      int currentValue = romanMap.get(roman.charAt(i));

      if (currentValue < prevValue) {
        result -= currentValue;
      } else {
        result += currentValue;
      }

      prevValue = currentValue;
    }

    return result;
  }

  public static String arabicToRoman(int number) {
    if (number <= 0 || number > 3999) {
      return "Invalid Roman Number";
    }

    String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

    StringBuilder result = new StringBuilder();

    for (int i = 0; i < arabicValues.length; i++) {
      while (number >= arabicValues[i]) {
        result.append(romanSymbols[i]);
        number -= arabicValues[i];
      }
    }

    return result.toString();
  }

  public static String calc(String input) {
    try {
      String[] parts = input.split(" ");

      if (parts.length != 3) {
        return "Неверный формат ввода.";
      }

      String operand1 = parts[0];
      char operation = parts[1].charAt(0);
      String operand2 = parts[2];

      boolean isArabic1 = Character.isDigit(operand1.charAt(0));
      boolean isArabic2 = Character.isDigit(operand2.charAt(0));

      if (isArabic1 != isArabic2) {
        throw new IllegalArgumentException("Операции с разными системами чисел не поддерживаются.");
      }

      int result;

      if (isArabic1) {
        // Если оба операнда арабские числа
        int num1 = Integer.parseInt(operand1);
        int num2 = Integer.parseInt(operand2);

        switch (operation) {
          case '+':
            result = num1 + num2;
            break;
          case '-':
            result = num1 - num2;
            break;
          case '*':
            result = num1 * num2;
            break;
          case '/':
            if (num2 == 0) {
              throw new ArithmeticException("Ошибка деления на ноль.");
            }
            result = num1 / num2;
            break;
          default:
            throw new IllegalArgumentException("Неверная операция");
        }

        return "Результат: " + result;
      } else {
        // Иначе предполагаем, что оба операнда римские числа
        int arabicNum1 = romanToArabic(operand1.toUpperCase());
        int arabicNum2 = romanToArabic(operand2.toUpperCase());

        switch (operation) {
          case '+':
            result = arabicNum1 + arabicNum2;
            break;
          case '-':
            result = arabicNum1 - arabicNum2;
            break;
          case '*':
            result = arabicNum1 * arabicNum2;
            break;
          case '/':
            if (arabicNum2 == 0) {
              throw new ArithmeticException("Ошибка деления на ноль.");
            }
            result = arabicNum1 / arabicNum2;
            break;
          default:
            throw new IllegalArgumentException("Неверная операция");
        }

        if (result <= 0) {
          throw new IllegalArgumentException("Результат работы калькулятора с римскими числами может быть только положительным.");
        }

        StringBuilder resultMessage = new StringBuilder(/*"Результат на арабском: " + result*/);

//        if (result >= 1 && result <= 10) {
//          resultMessage.append("\nВнимание: Результат находится в диапазоне от 1 до 10.");
//        }

        resultMessage.append("\nРезультат на римском: ").append(arabicToRoman(result));

        return resultMessage.toString();
      }
    } catch (NumberFormatException e) {
      return "Ошибка преобразования чисел. Пожалуйста, введите корректные числа.";
    } catch (IllegalArgumentException | ArithmeticException e) {
      return e.getMessage();
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Введите выражение в формате: число1 Операция число2 (например, VII - V или 7 + 3): ");
    String input = scanner.nextLine();

    try {
      String result = calc(input);
      System.out.println(result);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}