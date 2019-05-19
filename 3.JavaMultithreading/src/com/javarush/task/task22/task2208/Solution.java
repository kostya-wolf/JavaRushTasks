package com.javarush.task.task22.task2208;

import java.util.HashMap;
import java.util.Map;

/* 
Формируем WHERE
*/
public class Solution {
    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("name", null);
        params.put("country", null);
        params.put("city", null);
        params.put("age", null);
        String WHERE = getQuery(params);
        System.out.println(WHERE);
    }
    public static String getQuery(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<String, String> pair: params.entrySet()) {
            if (pair.getValue() != null){
                if (sb.length()>0) sb.append(" and ");
                sb.append(pair.getKey());
                sb.append(" = '");
                sb.append(pair.getValue());
                sb.append("'");
            }
        }
        return sb.toString();
    }
}
