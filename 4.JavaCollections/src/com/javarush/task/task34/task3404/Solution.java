package com.javarush.task.task34.task3404;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.*;

/*
Рекурсия для мат. выражения
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recurse("sin(2*(-5+1.5*4)+28)", 0);      // expected output 0.5 6
        solution.recurse("tan(45)", 0);                   // expected output 1 1
        solution.recurse("tan(-45)", 0);                  // expected output -1 2
        solution.recurse("0.305", 0);                     // expected output 0.3 0
        solution.recurse("0.3051", 0);                    // expected output 0.31
        solution.recurse("(0.3051)", 0);                  // expected output 0.31
        solution.recurse("1+(1+(1+1)*(1+1))*(1+1)+1", 0); // expected output 12 8
        solution.recurse("tan(44+sin(89-cos(180)^2))", 0);// expected output 1 6
        solution.recurse("-2+(-2+(-2)-2*(2+2))", 0);      // expected output -14 8
        solution.recurse("sin(80+(2+(1+1))*(1+1)+2)", 0); // expected output 1 7
        solution.recurse("1+4/2/2+2^2+2*2-2^(2-1+1)", 0); // expected output 6 11
        solution.recurse("10-2^(2-1+1)", 0);              // expected output 6 4
        solution.recurse("2^10+2^(5+5)", 0);              // expected output 2048 4
        solution.recurse("1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1", 0); // expected output 72.96 8
        solution.recurse("0.000025+0.000012", 0);          // expected output 0 1
        solution.recurse("-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)", 0); // expected output -3 16
        solution.recurse("cos(3 + 19*3)", 0);              // expected output 0.5 3
        solution.recurse("2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)", 0); // expected output 8302231.36 14
        solution.recurse("(-1 + (-2))", 0);                // expected output -3 3
        solution.recurse("-sin(2*(-5+1.5*4)+28)", 0);      // expected output -0.5 7
        solution.recurse("sin(100)-sin(100)", 0);          // expected output 0 3
        solution.recurse("-(-22+22*2)", 0);                // expected output -22 4
        solution.recurse("-2^(-2)", 0);                    // expected output -0.25 3
        solution.recurse("-(-2^(-2))+2+(-(-2^(-2)))", 0);  // expected output 2.5 10
        solution.recurse("(-2)*(-2)", 0);                  // expected output 4 3
        solution.recurse("(-2)/(-2)", 0);                  // expected output 1 3
        solution.recurse("sin(-30)", 0);                   //expected output -0.5 2
        solution.recurse("cos(-30)", 0);                   //expected output 0.87 2
        solution.recurse("tan(-30)", 0);                   //expected output -0.58 2
        solution.recurse("2+8*(9/4-1.5)^(1+1)", 0);        // expected output 6.5 6
        solution.recurse("0.005", 0);                      // expected output 0.01 0
        solution.recurse("0.0049", 0);                     // expected output 0 0
        solution.recurse("0+0.304", 0);                    // expected output 0.3 1
        solution.recurse("sin(45) - cos(45)", 0);          // expected output 0 3
        solution.recurse("0/(-3)", 0);                     // expected output 0 2
    }

    public void recurse(final String expression, int countOperation) {
        String expr = expression;
        if (countOperation == 0) {
            expr = expression.replaceAll(" ", "");
            String ops = "sin|cos|tan|\\^|/|\\*|-|\\+";
            Pattern pattern = Pattern.compile(ops);
            Matcher matcher = pattern.matcher(expr);
            while (matcher.find()) {
                countOperation++;
            }
        }

        Stack<Integer> stack = new Stack<>();
        int startSubExp = 0;
        int endSubExp = 0;
        String subExp = expr;
        for (int index = 0; index < expr.length(); index++) {
            if (expr.charAt(index) == '(') {
                stack.add(index);
                startSubExp = index;
            }
            else if (expr.charAt(index) == ')') {
                endSubExp = index;
                startSubExp = stack.pop() + 1;
            }
            if (endSubExp != 0) {
                subExp = expr.substring(startSubExp, endSubExp);
                break;
            }
        }
        // Получили простое выражение без скобок, надо его вычислить и далее результат подставить в выражение
        char[] signs = {'^', '/', '*', '-', '+'};
        for (char c : signs) {
            String pattern = String.format("-?\\d+\\.?\\d*\\%s-?\\d+\\.?\\d*", c);
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(subExp);
            while (m.find()) {
                String exp = m.group();
                String a = exp.substring(0, exp.lastIndexOf(c));
                String b = exp.substring(exp.lastIndexOf(c) + 1);
                Double result = null;
                switch (c) {
                    case '^' : {
                        result = pow(Double.valueOf(a), Double.valueOf(b));
                        if (a.charAt(0) == '-') {
                            result = result * (-1);
                        }
                        break;
                    }
                    case '/' : {
                        result = Double.valueOf(a) / Double.valueOf(b);
                        break;
                    }
                    case '*' : {
                        result = Double.valueOf(a) * Double.valueOf(b);
                        break;
                    }
                    case '-' : {
                        result = Double.valueOf(a) - Double.valueOf(b);
                        break;
                    }
                    case '+' : {
                        result = Double.valueOf(a) + Double.valueOf(b);
                        break;
                    }
                }
                subExp = m.replaceFirst(result + "");
                m.reset(subExp);
            }
        }

        if (endSubExp != 0) {
            subExp = expr.substring(0, startSubExp) + subExp + expr.substring(endSubExp);
        }

        // Ищем синусы, косинусы, тангенсы
        String pattern = "(sin|cos|tan)\\((-?\\d+\\.?\\d*\\))";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(subExp);
        while (m.find()) {
            String exp = m.group();
            String op = exp.substring(0, 3);
            String a = exp.substring(exp.indexOf('(') + 1, exp.lastIndexOf(')'));
            Double result = null;
            switch (op) {
                case "sin" : {
                    result = Math.sin(toRadians(Double.valueOf(a)));
                    break;
                }
                case "cos" : {
                    result = Math.cos(toRadians(Double.valueOf(a)));
                    break;
                }
                case "tan" : {
                    result = Math.tan(toRadians(Double.valueOf(a)));
                    break;
                }
            }
            subExp = m.replaceFirst(result + "");
            m.reset(subExp);
        }

        // ищем строки вида -(-0.25) и +(+0.25)
        pattern = "([-+])\\(((\\1)\\d+\\.?\\d*\\))";
        p = Pattern.compile(pattern);
        m = p.matcher(subExp);
        while (m.find()) {
            String exp = m.group();
            String result = "+" + exp.substring(3, exp.length() - 1);
            subExp = m.replaceFirst(result);
            m.reset(subExp);
        }

        // ищем завершенные скобки (без функций)
        pattern = "(?<!\\d)\\(([-+]?\\d+\\.?\\d*\\))";
        p = Pattern.compile(pattern);
        m = p.matcher(subExp);
        while (m.find()) {
            String exp = m.group();
            String result = exp.substring(exp.indexOf('(') + 1, exp.indexOf(')'));
            subExp = m.replaceFirst(result);
            m.reset(subExp);
        }

        // ищем двойные символы + и -
        pattern = "\\+-|--|-\\+|\\+\\+";
        p = Pattern.compile(pattern);
        m = p.matcher(subExp);
        while (m.find()) {
            String exp = m.group();
            String result = "";
            switch (exp) {
                case "--":
                case "++": {
                    result = "+";
                    break;
                }
                case "+-":
                case "-+": {
                    result = "-";
                    break;
                }
            }
            subExp = m.replaceFirst(result);
            m.reset(subExp);
        }

        if (expression.equals(subExp)) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            NumberFormat format = new DecimalFormat("#.##", symbols);
            Double d = (double)Math.round(Double.valueOf(subExp)*10000) / 10000;
            System.out.println(String.format("%s %d", format.format(d), countOperation));
            return;
        }
        recurse(subExp, countOperation);
    }

    public Solution() {
        //don't delete
    }
}
