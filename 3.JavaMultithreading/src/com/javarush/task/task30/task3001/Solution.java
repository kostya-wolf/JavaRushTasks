package com.javarush.task.task30.task3001;

import java.math.BigInteger;

import static com.javarush.task.task30.task3001.NumerationSystemType.*;

/*
Конвертер систем счислений
*/
public class Solution {
    public static void main(String[] args) {
        Number number = new Number(_10, "6");
        Number result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._2);
        System.out.println(result);    //expected 110

        number = new Number(_16, "6df");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._8);
        System.out.println(result);    //expected 3337

        number = new Number(_16, "abcdefabcdef");
        result = convertNumberToOtherNumerationSystem(number, _16);
        System.out.println(result);    //expected abcdefabcdef

        number = new Number(_16, "g");
        result = convertNumberToOtherNumerationSystem(number, _16);
        System.out.println(result);    //expected abcdefabcdef
    }

    public static Number convertNumberToOtherNumerationSystem(Number number, NumerationSystem expectedNumerationSystem) {
        BigInteger bi = new BigInteger(number.getDigit(), number.getNumerationSystem().getNumerationSystemIntValue());
        return new Number(expectedNumerationSystem, bi.toString(expectedNumerationSystem.getNumerationSystemIntValue()));
    }
}
