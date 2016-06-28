package core.utils;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Logger log = Logger.getLogger(ValidationUtils.class);

    /**
     * BigDecimal regex.
     */
    private static final String BIG_DECIMAL_REGEX = "^\\d{1,10}(\\.\\d{1,2})?$";

    private static final String ALL_BIG_DECIMAL_REGEX = "^(-?\\d{1,10})||(-?\\d{1,10}\\.\\d{1,2})$";

    public static final String ACCOUNT_REGEX = "^[a-zA-Z]+[a-zA-Z0-9]*$";

    /**
     * Positive Integer regex.
     * <p/>
     * It includes positive number and zero
     */
    private static final String POSITIVE_INTEGER_REGEX = "^\\d{1,10}$";

    /**
     * Telephone regex.
     */
    private static final String TELEPHONE_REGEX = "^0?1\\d{10}$";

    /**
     * Number decimal
     */
    private static final String NUMBER_DECIMAL = "^(\\d{1,10}\\.\\d{1,2}|\\d{1,10})$";

    private static final String[] VALID_PICTURE_TYPES = new String[]{".JPG", ".PNG", ".JPEG", ".BMP", ".GIF"};

    private static final String[] NOT_CONVENT_TYPES = new String[]{".BMP", ".GIF"};

    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    private static final String TIME_REGEX = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9])$";

    private static final String ACCURATE_DATE_REGEX = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

    private static final String POSITIVE_BIG_DECIMAL_REGEX = "^([1-9]\\d{0,9})||(\\d{1,10}\\.\\d{1,2})$";

    private static final String PERCENTAGE_NUMBER_REGEX = "^((\\d|[1-9]\\d)(\\.\\d+)?|100(\\.[0]+)?)$";

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty() || value.equals("null");
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static final String ALL_NUMBER_REGEX = "[0-9]*";

    /**
     * Check BigDecimal format.
     * Available values list:
     * <ul>
     * <li>12344</li>
     * <li>123.44</li>
     * <li>122323.443</li>
     * </ul>
     *
     * @param bigDecimalText BigDecimal Text
     * @return True is BigDecimal text,otherwise not
     */
    public static boolean isBigDecimal(String bigDecimalText) {
        return !isEmpty(bigDecimalText) && bigDecimalText.matches(BIG_DECIMAL_REGEX);
    }


    public static boolean isPrice(String priceText) {
        return isEmpty(priceText) || (priceText.matches(BIG_DECIMAL_REGEX) && priceText.matches(NUMBER_DECIMAL));
    }

    /**
     * Scenario 1: the text is empty, return true
     * <p/>
     * Scenario 2: the text is not empty and the format of text is BigDecimal, return true
     * <p/>
     * Scenario 3: the text is not empty and the format of text is not BigDecimal, return false
     *
     * @param text test
     * @return boolean
     */
    public static boolean isPriceBigDecimal(String text) {
        return isPrice(text);
    }

    /**
     * Check whether the text is positive number, including zero.
     *
     * @param numberText Need pattern integer text
     * @return True is Integer,otherwise false
     */
    public static boolean isPositiveNumber(String numberText) {
        return !isEmpty(numberText) && numberText.matches(POSITIVE_INTEGER_REGEX);
    }

    public static boolean isValidPictureTypes(String suffix) {
        for (String validPictureType : VALID_PICTURE_TYPES) {
            if (suffix.equalsIgnoreCase(validPictureType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTelephone(String text) {
        return !isEmpty(text) && text.matches(TELEPHONE_REGEX);
    }

    public static boolean isValidDate(String sDate) {
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(DATE_REGEX);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(ACCURATE_DATE_REGEX);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean isValidTime(String sTime) {
        return !isEmpty(sTime) && sTime.matches(TIME_REGEX);
    }

    public static boolean isValidDateTime(String dateTime) {
        if (!isEmpty(dateTime) && dateTime.length() == 16) {
            String date = dateTime.substring(0, 10);
            String time = dateTime.substring(11);
            if (ValidationUtils.isValidDate(date) && ValidationUtils.isValidTime(time)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllNumber(String value) {
        return !isEmpty(value) && value.matches(ALL_BIG_DECIMAL_REGEX);
    }

    public static boolean isPositiveBigDecimal(String value) {
        return !isEmpty(value) && value.matches(POSITIVE_BIG_DECIMAL_REGEX);
    }

    public static boolean isPercentageNumber(String value) {
        return !isEmpty(value) && value.matches(PERCENTAGE_NUMBER_REGEX);
    }

    public static boolean isNotConventType(String suffix) {
        for (String notConventType : NOT_CONVENT_TYPES) {
            if (notConventType.equalsIgnoreCase(suffix)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidAccount(String account) {
        return !isEmpty(account) && account.matches(ACCOUNT_REGEX);
    }

    public static boolean isNumber(String value){
        return !isEmpty(value) && value.matches(ALL_NUMBER_REGEX);
    }

    /**
     * 判断集合为空
     *
     * @param collection
     */
    public static boolean isNullCollection(Collection<?> collection) {
        if (collection != null && collection.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断集合不为空
     *
     * @param collection
     */
    public static boolean isNotNullCollection(Collection<?> collection) {
        if (collection != null && collection.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断Map为空
     *
     * @param map
     */
    public static boolean isNullMap(Map<?, ?> map) {
        if (map != null && map.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断Map不为空
     *
     * @param map
     */
    public static boolean isNotNullMap(Map<?, ?> map) {
        if (map != null && map.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否是一个对象
     *
     * @param object 待检查对象
     * @return 若为true则表示是一个空对象
     */
    public static boolean isNullObject(Object object) {
        try {
            return null == object || object.equals("") || object.equals("null");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNotNullObject(Object object) {
        return !isNullObject(object);
    }

    /**
     * 判断是否为空
     *
     * @param object
     * @return 若对象不为空 返回true
     */
    public static boolean isNotBlank(Object object) {
        try {
            if (object == null) {
                return false;
            } else if ("null".equals(object)
                    || "null".equals(String.valueOf(object).trim())) {
                return false;
            } else {
                String inputStr = String.valueOf(object);
                inputStr = inputStr.replaceAll(" ", "");
                if ("".equals(inputStr)) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 如果String为空 则返回“ ”
     * @param input
     * @return
     */
    public static String removeNullString(Object input) {
        String inputStr = "";

        if (input == null)
            inputStr = "";
        else if (input.equals("null"))
            inputStr = "";
        else if (input == "")
            inputStr = "";
        else {
            inputStr = (String) input;
            inputStr = inputStr.replaceAll(" ", "");
        }
        return inputStr;
    }

    /**
     * 如果Object为空，则返回i
     * @param obj
     * @param i
     * @return
     */
    public static int dealNullInt(Object obj, int i) {
        int values = 0;
        try {
            if (obj != null) {
                String tmp = obj.toString();
                values = Integer.parseInt(tmp);
            } else {
                values = i;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            values = i;
        }
        return values;
    }
}
