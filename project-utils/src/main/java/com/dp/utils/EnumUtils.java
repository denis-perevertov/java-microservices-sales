package com.dp.utils;

public interface EnumUtils {

//    static Enum<?> getValue(Enum<?> enumeration) {
//
//    }

    static <E extends Enum<E>> E getEnum(String enumName, Class<E> enumClass) {
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (Exception e) {
            return null;
        }
    }
}
