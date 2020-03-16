package jp.co.ctcg.easset.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class CopyUtil {

	public static Object deepCopy(Object obj) {
		try {
			Class<?> cls = obj.getClass();
			Object clone = cls.getDeclaredConstructor().newInstance();

			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				if (!Modifier.isFinal(field.getModifiers())) {
					if (field.getType().isPrimitive()) {
						field.set(clone, field.get(obj));
					} else {
						field.set(clone, deepCopyObject(field.get(obj)));
					}
				}
			}

			while (true) {
				cls = cls.getSuperclass();
				if (Object.class.equals(cls)) {
					break;
				}
				Field[] sFields = cls.getDeclaredFields();
				for (int i = 0; i < sFields.length; i++) {
					Field field = sFields[i];
					if (!Modifier.isFinal(field.getModifiers())) {
						field.setAccessible(true);
						if (field.getType().isPrimitive()) {
							field.set(clone, field.get(obj));
						} else {
							field.set(clone, deepCopyObject(field.get(obj)));
						}
					}
				}
			}
			return clone;
		} catch (Exception e) {
			return null;
		}
	}

	protected static Object deepCopyObject(Object obj) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Object ret = null;

		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
			ret = ois.readObject();
		} catch (Exception e) {
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
			}
		}

		return ret;
	}

}