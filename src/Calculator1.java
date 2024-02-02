import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator1 {
  private static final Map<String, Integer> romanNumerals = new HashMap<>();
  static {
    romanNumerals.put("I", 1);
    romanNumerals.put("II", 2);
    romanNumerals.put("III", 3);
    romanNumerals.put("IV", 4);
    romanNumerals.put("V", 5);
    romanNumerals.put("VI", 6);
    romanNumerals.put("VII", 7);
    romanNumerals.put("VIII", 8);
    romanNumerals.put("IX", 9);
    romanNumerals.put("X", 10);
    romanNumerals.put("XI", 11);
    romanNumerals.put("XII", 12);
    romanNumerals.put("XIII", 13);
    romanNumerals.put("XIV", 14);
    romanNumerals.put("XV", 15);
    romanNumerals.put("XVI", 16);
    romanNumerals.put("XVII", 17);
    romanNumerals.put("XVIII", 18);
    romanNumerals.put("XIX", 19);
    romanNumerals.put("XX", 20);
  }

  public static String calc(String input) {
    try {
      String[] elements = input.split(" ");

      if (elements.length != 3 || (isRoman(elements[0]) && !isRoman(elements[2])) || (!isRoman(elements[0]) && isRoman(elements[2]))) {
        throw new IllegalArgumentException("Неправильное использование систем счисления");
      }

      int operand1 = parseOperand(elements[0]);
      String operator = elements[1];
      int operand2 = parseOperand(elements[2]);

      int result = performOperation(operand1, operator, operand2);

      if (isRoman(elements[0])) {
        return toRoman(result);
      } else {
        return String.valueOf(result);
      }
    } catch (Exception e) {
      return "Ошибка: " + e.getMessage();
    }
  }

  private static int parseOperand(String operand) {
    if (isRoman(operand)) {
      return romanNumerals.get(operand);
    } else {
      int value = Integer.parseInt(operand);
      if (value < 1 || value > 10) {
        throw new IllegalArgumentException("Число должно быть от 1 до 10 включительно");
      }
      return value;
    }
  }

  private static int performOperation(int operand1, String operator, int operand2) {
    switch (operator) {
      case "+":
        return operand1 + operand2;
      case "-":
        return operand1 - operand2;
      case "*":
        return operand1 * operand2;
      case "/":
        if (operand2 == 0) {
          throw new ArithmeticException("Деление на ноль запрещено");
        }
        return operand1 / operand2;
      default:
        throw new IllegalArgumentException("Недопустимая арифметическая операция");
    }
  }

  private static boolean isRoman(String operand) {
    return romanNumerals.containsKey(operand);
  }

  private static String toRoman(int number) {
    if (number <= 0) {
      throw new IllegalArgumentException("Результат римских чисел не может быть отрицательным или нулевым");
    }

    for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
      if (entry.getValue().equals(number)) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("Недопустимый результат для римских чисел");
  }

  public static void main(String[] args) {
    System.out.println("Введите выражение (например, 3 + 4 или I + V): ");
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();
    String result = calc(input);
    System.out.println(result);
  }
}
