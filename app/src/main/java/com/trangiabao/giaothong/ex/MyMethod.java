package com.trangiabao.giaothong.ex;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class MyMethod {

    public static String unAccent(String text) {
        String temp = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }
}
