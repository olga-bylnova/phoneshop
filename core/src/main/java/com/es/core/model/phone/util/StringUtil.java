package com.es.core.model.phone.util;

import java.util.regex.Pattern;

public class StringUtil {
    public static final String FIND_ALL_SQL = """    
            SELECT * FROM (SELECT * FROM phones
            JOIN stocks on stocks.phoneId = phones.id
            AND stocks.stock > 0
            AND phones.price IS NOT NULL
            OFFSET ? LIMIT ?) as phones_partial
            """;
    public static final String FIND_ALL_WITH_FILTER_SQL = """    
            SELECT * FROM (SELECT * FROM phones
            JOIN stocks on stocks.phoneId = phones.id
            AND stocks.stock > 0
            AND phones.price IS NOT NULL) as phones_partial
            """;
    public static final String JOIN_COLORS_TABLE_SQL = """
            SELECT * FROM phones
            LEFT JOIN phone2color on phones.id = phone2color.phoneId
            LEFT JOIN colors on colors.id = phone2color.colorId
            WHERE phones.id = ?;
            """;
    public static final String ORDER_BY_SQL = """
            ORDER BY %s %s
            """;
    public static final String OFFSET_LIMIT_SQL = """
            OFFSET ? LIMIT ?
            """;
    public static final String FILTER_WITH_STRING_PARAMETER_SQL = """
            LOWER(brand) LIKE \'%%%s%%\'
            OR LOWER(model) LIKE \'%%%s%%\'
            """;
    public static final String FILTER_WITH_NUMBER_PARAMETER_SQL = """
            brand LIKE \'%%%s%%\'
            OR model LIKE \'%%%s%%\'
            OR displaySizeInches = %s
            OR price = %s
            """;
    public static final Pattern PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static final String SAVE_SQL = """
            INSERT INTO phones
            (brand, model, price, displaySizeInches,
            weightGr, lengthMm, widthMm, heightMm, announced,
            deviceType, os, displayResolution, pixelDensity, displayTechnology,
            backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb,
            batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning,
            imageUrl, description) values
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
            ?, ?, ?, ?, ?, ?)
            """;
    public static final String GET_STOCK_BY_PHONE_ID = """
            SELECT stock FROM stocks
            WHERE phoneId = ?
            """;
    public static final String GET_COUNT_FIND_ALL_SQL = """    
            SELECT count(1) FROM (SELECT * FROM phones
            JOIN stocks on stocks.phoneId = phones.id
            AND stocks.stock > 0
            AND phones.price IS NOT NULL) as phones_partial
            """;
}
