package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        return getTotalAmountForMap(denominations);
    }

    public int getTotalAmountForMap(Map<Integer, Integer> map) {
        return map.entrySet()
                .stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
    }

    public boolean hasMoney() {
        return getTotalAmount() > 0;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> bestResultMap = new HashMap<>();
        bestResultMap = foundBanknotes(expectedAmount, getBanknoteMap(), bestResultMap);
        if (bestResultMap.isEmpty()) {
            throw new NotEnoughMoneyException();
        }
        bestResultMap.entrySet()
                .forEach(e -> denominations.put(e.getKey(), denominations.get(e.getKey()) - e.getValue()));
        return bestResultMap;
    }

    private SortedMap<Integer, Integer> getBanknoteMap() {
        SortedMap<Integer, Integer> sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll(denominations);
        return sortedMap;
    }

    private Map<Integer, Integer> foundBanknotes(int expectedAmount, SortedMap<Integer, Integer> banknoteMap, Map<Integer, Integer> bestResult) {
        if (banknoteMap != null && !banknoteMap.isEmpty() && expectedAmount <= getTotalAmountForMap(banknoteMap)) {
            Map<Integer, Integer> result = new HashMap<>();
            int answerCount = 0;
            int expectAmTemp = expectedAmount;
            Map<Integer, Integer> banknoteMapTemp = new TreeMap<>(banknoteMap);
            for (int nominal : banknoteMapTemp.keySet()) {
                if (expectAmTemp == 0) break;
                int countBanknote = expectAmTemp / nominal;
                if (countBanknote <= banknoteMapTemp.get(nominal)) {
                    answerCount += countBanknote;
                    expectAmTemp %= nominal;
                } else {
                    countBanknote = banknoteMapTemp.get(nominal);
                    answerCount += countBanknote;
                    expectAmTemp -= countBanknote * nominal;
                }
                if (countBanknote > 0) {
                    banknoteMapTemp.put(nominal, banknoteMapTemp.get(nominal) - countBanknote);
                    result.put(nominal, countBanknote); //возможно здесь добавить к имеющемуся
                }
            }
            if (expectAmTemp == 0 && answerCount > 0) {
                if (bestResult.isEmpty() || answerCount < getBestAnswer(bestResult)) {
                    bestResult = result;
                }
            }
            bestResult = foundBanknotes(expectedAmount, getTailMap(banknoteMap), bestResult);
        }
        return bestResult;
    }

    private int getBestAnswer(Map<Integer, Integer> bestResult) {
        return bestResult.values()
                .stream()
                .mapToInt(value -> value)
                .sum();
    }

    private SortedMap<Integer, Integer> getTailMap(SortedMap<Integer, Integer> banknoteMap) {
        if (banknoteMap.size() < 2) {
            return null;
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = banknoteMap.entrySet().iterator();
        Integer secondKey = banknoteMap.lastKey();
        for (int i = 0; i < 2 && iterator.hasNext(); i++) {
            secondKey = iterator.next().getKey();
        }
        return banknoteMap.tailMap(secondKey);
    }
}
