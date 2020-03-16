package jp.co.ctcg.easset.rest;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON文字列⇔Javaオブジェクトのマッピング用ユーティリティクラス
 */
public final class MappingUtils {
    private static final SimpleDateFormat jsonDateFormat; //JSONの日付項目文字列の標準フォーマット
    private MappingUtils() {}

    static {
        jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        jsonDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * JSON文字列をJavaクラスデータにマッピング
     * @param json JSON文字列
     * @param type マッピングクラスの型
     * @param <T> マッピングクラス
     * @return jsonパラメータの値が設定されたtypeクラスのインスタンス
     */
    public static <T> T fromJson(String json, Class<T> type) {
        if(json == null) return null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JavaクラスデータをJSON文字列にマッピング
     * @param obj マッピングするObject
     * @return json文字列
     */
    public static String toJson(Object obj) {
        if(obj == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON文字列をJavaクラスデータにマッピング
     * @param json JSON文字列
     * @param typeReference マッピングクラスの型参照
     * @param <T> マッピングクラス
     * @return jsonパラメータの値が設定されたtypeクラスのインスタンス
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if(json == null) return null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSONの日付項目文字列の標準フォーマットを取得する
     */
    public static DateFormat getJsonDateFormat() {
        return jsonDateFormat;
    }
}