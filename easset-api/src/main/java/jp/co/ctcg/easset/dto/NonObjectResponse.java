package jp.co.ctcg.easset.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Primitive型（およびそのラップ型）や、オブジェクト型じゃない値のJSONレスポンス用ラッパークラス
 * @param <T>
 */
@Getter
@Setter
@ToString
public class NonObjectResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * NonObjectResponse
     * @param value value
     */
    public NonObjectResponse(T value){
        this.value = value;
    }

    /**
     * value
     */
    private T value;

}
