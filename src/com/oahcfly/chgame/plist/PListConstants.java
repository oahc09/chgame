
package com.oahcfly.chgame.plist;

/**
 * 
 * <pre>
 * <!ENTITY % plistObject "(array | data | date | dict | real | integer | string | true | false )" >
 *<!ELEMENT plist %plistObject;>
 *<!ATTLIST plist version CDATA "1.0" >
 *
 *<!-- Collections -->
 *<!ELEMENT array (%plistObject;)*>
 *<!ELEMENT dict (key, %plistObject;)*>
 *<!ELEMENT key (#PCDATA)>
 *
 *<!--- Primitive types -->
 *<!ELEMENT string (#PCDATA)>
 *<!ELEMENT data (#PCDATA)> <!-- Contents interpreted as Base-64 encoded -->
 *<!ELEMENT date (#PCDATA)> <!-- Contents should conform to a subset of ISO 8601 (in particular, YYYY '-' MM '-' DD 'T' HH ':' MM ':' SS 'Z'.  Smaller units may be omitted with a loss of precision) -->
 *
 *<!-- Numerical primitives -->
 *<!ELEMENT true EMPTY>  <!-- Boolean constant true -->
 *<!ELEMENT false EMPTY> <!-- Boolean constant false -->
 *<!ELEMENT real (#PCDATA)> <!-- Contents should represent a floating point number matching ("+" | "-")? d+ ("."d*)? ("E" ("+" | "-") d+)? where d is a digit 0-9.  -->
 *<!ELEMENT integer (#PCDATA)> <!-- Contents should represent a (possibly signed) integer number in base 10 -->
 * 
 * 
 * date: 2014-11-12
 * </pre>
 * @author caohao
 */
public class PListConstants {

    public static final java.lang.String TAG_PLIST = "plist";

    public static final java.lang.String TAG_DICT = "dict";

    public static final java.lang.String TAG_PLIST_ARRAY = "array";

    public static final java.lang.String TAG_KEY = "key";

    public static final java.lang.String TAG_INTEGER = "integer";

    public static final java.lang.String TAG_STRING = "string";

    public static final java.lang.String TAG_REAL = "real";

    public static final java.lang.String TAG_DATE = "date";

    public static final java.lang.String TAG_BOOL_TRUE = "true";

    public static final java.lang.String TAG_BOOL_FALSE = "false";

    public static final java.lang.String TAG_DATA = "data";

    public static final String DICT_FRAME = "frame";

    public static final String DICT_OFFSET = "offset";

    public static final String DICT_ROTATED = "rotated";

    public static final String DICT_SOURCE_SIZE = "sourceSize";

    public static final String DICT_SOURCE_RECT = "sourceColorRect";

}
