package jp.co.ctcg.easset.rest.conv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RESTパラメータの日付フォーマット指定アノテーション
 * 当アノテーションを指定すると、RESTパラメータが自動的に日付文字列→日付型オブジェクトに変換される
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface DateFormat {
	/**
	* デフォルトの日付フォーマット
	*/
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

	String value() default DEFAULT_FORMAT;
}