package jp.co.ctcg.easset.rest.conv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;

/**
 * 日付型のRESTパラメータコンバーター
 */
public class DateParamConverter implements ParamConverter<Date> {
	/**
	 * 日付フォーマット
	 */
	private DateFormat customDateFormat;

	/**
	 * 日付フォーマットの設定
	 * @param customDateFormat 日付フォーマット
	 */
	public void setCustomDateFormat(DateFormat customDateFormat) {
		this.customDateFormat = customDateFormat;
	}

	/**
	 * 文字列からオブジェクト取得
	 * @param string 日付文字列
	 */
	@Override
	public Date fromString(String string) {
		if (Optional.ofNullable(string).orElse("null").equals("null")) return null;

		String format = DateFormat.DEFAULT_FORMAT;
		if (customDateFormat != null) {
			format = customDateFormat.value();
		}

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		try {
			return simpleDateFormat.parse(string);
		} catch (ParseException ex) {
			throw new WebApplicationException(ex);
		}
	}

	/**
	 * オブジェクトから文字列表現取得
	 * @param date 日付
	 */
	@Override
	public String toString(Date date) {
		return new SimpleDateFormat((this.customDateFormat == null ? DateFormat.DEFAULT_FORMAT : this.customDateFormat.value())).format(date);
	}
}