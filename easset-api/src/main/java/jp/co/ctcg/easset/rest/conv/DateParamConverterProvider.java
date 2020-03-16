package jp.co.ctcg.easset.rest.conv;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * RESTの日付文字列パラメータをDate型として受け取れるようにするコンバータ
 */
@Provider
public class DateParamConverterProvider implements ParamConverterProvider {
	/**
	 * コンバータ取得
	 * @param rawType the raw type of the object to be converted.
	 * @param genericType the type of object to be converted. 
	 * @param annotations an array of the annotations associated with the convertible parameter instance.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType, final Annotation[] annotations) {
		ParamConverter<T> ret = null;
		if (Date.class.equals(rawType)) {
			final DateParamConverter dateParameterConverter = new DateParamConverter();

			for (Annotation annotation : annotations) {
				if (DateFormat.class.equals(annotation.annotationType())) {
					dateParameterConverter.setCustomDateFormat((DateFormat) annotation);
				}
			}
			ret = (ParamConverter<T>) dateParameterConverter;
		}
		return ret;
	}
}