package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

/* 
Кроссворд
*/
public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        List<Word> allWords = detectAllWords(crossword, "home", "same");
        for (Word word: allWords)
        {
            System.out.println(word.toString());
        }
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> wordList = new ArrayList<>();
        for (String slovo: words)
        {
            wordList.add(findWord(slovo, crossword));
        }
        return wordList;
    }

    private static Word findWord(String slovo, int[][] crossword)
    {
        Word w = new Word(slovo);
        for (int i=0; i<crossword.length; i++){
            for (int j=0; j<crossword[0].length; j++){
                if (slovo.charAt(0) == crossword[i][j]){
                    if (slovo.length() == 1) {
                        w.setStartPoint(j, i);
                        w.setEndPoint(j, i);
                        return w;
                    }
                    Naideno naideno = Naideno.gdeNaideno(crossword, i , j, slovo.toCharArray());
                    if (naideno.isNaideno) {
                        w.setStartPoint(j, i);
                        w.setEndPoint(naideno.endX, naideno.endY);
                        naideno.clearNaideno();
                        return w;
                    }
                }
            }
        }
        return w;
    }

    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }

    private static class Naideno
    {
        public static int endX;
        public static int endY;
        public static boolean isNaideno;

        public void clearNaideno()
        {
            this.endX = 0;
            this.endY = 0;
            this.isNaideno = false;
        }

        private Naideno()
        {}

        public static Naideno gdeNaideno(int[][] crossword, int i, int j, char[] slovo)
        {
            Naideno naideno = new Naideno();
            int endX = 0;
            int endY = 0;
            boolean isNaideno = false;

            isNaideno = go("levo", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            isNaideno = go("pravo", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            isNaideno = go("verh", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            isNaideno = go("niz", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            isNaideno = go("lv", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            isNaideno = go("pv", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            isNaideno = go("ln", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            isNaideno = go("pn", crossword, i, j, slovo);
            if (isNaideno) {
                return naideno;
            }
            return naideno;
        }

        private static boolean go(String way, int[][] crossword, int i, int j, char[] slovo)
        {
            switch (way)
            {
                case "levo":
                {
                    int j2 = j;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        j2--;
                        if (j2 < 0) break;
                        if (slovo[x + 1] == crossword[i][j2])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i;
                                endX = j2;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;

                case "pravo":
                {
                    int j2 = j;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        j2++;
                        if (j2 > crossword[i].length-1) break;
                        if (slovo[x + 1] == crossword[i][j2])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i;
                                endX = j2;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;

                case "verh":
                {
                    int i2 = i;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        i2--;
                        if (i2 < 0) break;
                        if (slovo[x + 1] == crossword[i2][j])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i2;
                                endX = j;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;

                case "niz":
                {
                    int i2 = i;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        i2++;
                        if (i2 > crossword.length-1) break;
                        if (slovo[x + 1] == crossword[i2][j])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i2;
                                endX = j;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;


                case "lv":
                {
                    int i2 = i;
                    int j2 = j;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        i2--;
                        j2--;
                        if (i2 < 0 || j2 < 0) break;
                        if (slovo[x + 1] == crossword[i2][j2])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i2;
                                endX = j2;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;


                case "pv":
                {
                    int i2 = i;
                    int j2 = j;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        i2--;
                        j2++;
                        if (i2 < 0 || j2 > crossword[i].length-1) break;
                        if (slovo[x + 1] == crossword[i2][j2])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i2;
                                endX = j2;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;



                case "ln":
                {
                    int i2 = i;
                    int j2 = j;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        i2++;
                        j2--;
                        if (i2 > crossword.length-1 || j2 < 0) break;
                        if (slovo[x + 1] == crossword[i2][j2])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i2;
                                endX = j2;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;

                case "pn":
                {
                    int i2 = i;
                    int j2 = j;
                    for (int x = 0; x < slovo.length - 1; x++)
                    {
                        i2++;
                        j2++;
                        if (i2 > crossword.length-1 || j2 > crossword[i].length-1) break;
                        if (slovo[x + 1] == crossword[i2][j2])
                        {
                            if (x == slovo.length - 2)
                            {
                                endY = i2;
                                endX = j2;
                                isNaideno = true;
                                return true;
                            }
                        } else break;
                    }
                }
                break;

            }
            return false;
        }


    }
}

