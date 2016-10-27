package com.smallchill.core.intercept;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.LogKit;
import com.smallchill.core.toolbox.kit.StrKit;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * api 参数验证
 * Created by yesong on 2016/10/27 0027.
 */
public abstract class ApiValidator extends ApiInterceptor {

    protected boolean succeed = true;
    protected HttpServletRequest request;
    protected static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

    protected void addError(ErrorType errorType) {
        if (succeed) {
            this.succeed = false;
            result = Result.fail(errorType);
        } else {
            throw new RuntimeException();
        }
    }

    final public Object intercept(Invocation inv) {
        ApiValidator validator = null;
        try {
            validator = getClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        validator.request = inv.getRequest();
        try {
            validator.doValidate(inv);
        } catch (Exception ex) {
            LogKit.logNothing(ex);
        }
        if (validator.succeed) {
            return invoke();
        } else {
            return JsonKit.toJson(validator.result);
        }
    }

    /**
     * Use validateXxx method to validate the parameters of this action.
     */
    protected abstract void doValidate(Invocation inv);

    /**
     * Judge whether the two values are equal
     */
    protected void validateTwoEqual(String field1, String field2, ErrorType errorType) {
        if (Func.isAllEmpty(field1, field2)) {// 字符串为 null 或者为 "" 时返回 true
            addError(errorType);
        }
        String value1 = request.getParameter(field1);
        String value2 = request.getParameter(field2);
        if (!value1.equals(value2)) {
            addError(errorType);
        }
    }

    /**
     * Judge whether the two values are not equal
     */
    protected void validateTwoNotEqual(String field1, String field2, ErrorType errorType) {
        if (Func.isAllEmpty(field1, field2)) {// 字符串为 null 或者为 "" 时返回 true
            addError(errorType);
        }
        String value1 = request.getParameter(field1);
        String value2 = request.getParameter(field2);
        if (value1.equals(value2)) {
            addError(errorType);
        }
    }

    /**
     * Check sql
     */
    protected void validateSql(String field, ErrorType errorType) {
        if (StrKit.isBlank(field)) {// 字符串为 null 或者为 "" 时返回 true
            addError(errorType);
        }
        String sql = request.getParameter(field);
        sql = sql.toLowerCase();
        if (sql.indexOf("delete") >= 0 || sql.indexOf("update") >= 0
                || sql.indexOf("insert") >= 0 || sql.indexOf("drop") >= 0) {
            addError(errorType);
        }
    }

    /**
     * Validate the illegal characters
     */
    protected void validateStringExt(String field, ErrorType errorType) {
        if (Func.isAllEmpty(field)) {// 字符串为 null 或者为 "" 时返回 true
            addError(errorType);
        }
        String val = request.getParameter(field);
        if (val.contains("<")) {
            addError(errorType);
        }
    }

    /**
     * Validate Required. Allow space characters.
     */
    protected void validateRequired(String field, ErrorType errorType) {
        String value = request.getParameter(field);
        if (value == null || "".equals(value)) // 经测试,form表单域无输入时值为"",跳格键值为"\t",输入空格则为空格" "
            addError(errorType);
    }

    /**
     * Validate required string.
     */
    protected void validateRequiredString(String field, ErrorType errorType) {
        if (StrKit.isBlank(request.getParameter(field)))
            addError(errorType);
    }

    /**
     * Validate integer.
     */
    protected void validateInteger(String field, int min, int max,
                                   ErrorType errorType) {
        validateIntegerValue(request.getParameter(field), min, max,
                errorType);
    }

    private void validateIntegerValue(String value, int min, int max,
                                      ErrorType errorType) {
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            int temp = Integer.parseInt(value.trim());
            if (temp < min || temp > max)
                addError(errorType);
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate integer.
     */
    protected void validateInteger(String field, ErrorType errorType) {
        validateIntegerValue(request.getParameter(field), errorType);
    }

    private void validateIntegerValue(String value, ErrorType errorType) {
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            Integer.parseInt(value.trim());
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate long.
     */
    protected void validateLong(String field, long min, long max,
                                ErrorType errorType) {
        validateLongValue(request.getParameter(field), min, max, errorType);
    }

    private void validateLongValue(String value, long min, long max,
                                   ErrorType errorType) {
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            long temp = Long.parseLong(value.trim());
            if (temp < min || temp > max)
                addError(errorType);
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate long.
     */
    protected void validateLong(String field, ErrorType errorType) {
        validateLongValue(request.getParameter(field), errorType);
    }

    private void validateLongValue(String value, ErrorType errorType) {
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            Long.parseLong(value.trim());
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate double.
     */
    protected void validateDouble(String field, double min, double max,
                                  ErrorType errorType) {
        String value = request.getParameter(field);
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            double temp = Double.parseDouble(value.trim());
            if (temp < min || temp > max)
                addError(errorType);
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate double.
     */
    protected void validateDouble(String field, ErrorType errorType) {
        String value = request.getParameter(field);
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            Double.parseDouble(value.trim());
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate date. Date formate: yyyy-MM-dd
     */
    protected void validateDate(String field, ErrorType errorType) {
        String value = request.getParameter(field);
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        if (!DateKit.isValidDate(Func.format(value))) {
            addError(errorType);
        }
    }

    /**
     * Validate date.
     */
    protected void validateDate(String field, Date min, Date max,
                                ErrorType errorType) {
        String value = request.getParameter(field);
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            Date temp = DateKit.parseTime(Func.format(value));
            if (temp.before(min) || temp.after(max))
                addError(errorType);
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate date. Date formate: yyyy-MM-dd
     */
    protected void validateDate(String field, String min, String max,
                                ErrorType errorType) {
        // validateDate(field, Date.valueOf(min), Date.valueOf(max), errorKey,
        // errorType); 为了兼容 64位 JDK
        try {
            validateDate(field, DateKit.parseTime(Func.format(min)),
                    DateKit.parseTime(Func.format(max)), errorType);
        } catch (Exception e) {
            addError(errorType);
        }
    }

    /**
     * Validate equal field. Usually validate password and password again
     */
    protected void validateEqualField(String field_1, String field_2,
                                      ErrorType errorType) {
        String value_1 = request.getParameter(field_1);
        String value_2 = request.getParameter(field_2);
        if (value_1 == null || value_2 == null || (!value_1.equals(value_2)))
            addError(errorType);
    }

    /**
     * Validate equal string.
     */
    protected void validateEqualString(String s1, String s2, ErrorType errorType) {
        if (s1 == null || s2 == null || (!s1.equals(s2)))
            addError(errorType);
    }

    /**
     * Validate equal integer.
     */
    protected void validateEqualInteger(Integer i1, Integer i2,
                                        ErrorType errorType) {
        if (i1 == null || i2 == null || (i1.intValue() != i2.intValue()))
            addError(errorType);
    }

    /**
     * Validate email.
     */
    protected void validateEmail(String field, ErrorType errorType) {
        validateRegex(field, emailAddressPattern, false, errorType);
    }

    /**
     * Validate URL.
     */
    protected void validateUrl(String field, ErrorType errorType) {
        String value = request.getParameter(field);
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        try {
            value = value.trim();
            if (value.startsWith("https://"))
                value = "http://" + value.substring(8); // URL doesn't
            // understand the https
            // protocol, hack it
            new URL(value);
        } catch (MalformedURLException e) {
            addError(errorType);
        }
    }

    /**
     * Validate regular expression.
     */
    protected void validateRegex(String field, String regExpression,
                                 boolean isCaseSensitive, ErrorType errorType) {
        String value = request.getParameter(field);
        if (value == null) {
            addError(errorType);
            return;
        }
        Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression)
                : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches())
            addError(errorType);
    }

    /**
     * Validate regular expression and case sensitive.
     */
    protected void validateRegex(String field, String regExpression,
                                 ErrorType errorType) {
        validateRegex(field, regExpression, true, errorType);
    }

    /**
     * Validate string.
     */
    protected void validateString(String field, int minLen, int maxLen,
                                  ErrorType errorType) {
        validateStringValue(request.getParameter(field), minLen, maxLen,
                errorType);
    }

    private void validateStringValue(String value, int minLen, int maxLen,
                                     ErrorType errorType) {
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        if (value.length() < minLen || value.length() > maxLen)
            addError(errorType);
    }

    /**
     * validate boolean.
     */
    protected void validateBoolean(String field, ErrorType errorType) {
        validateBooleanValue(request.getParameter(field), errorType);
    }

    private void validateBooleanValue(String value, ErrorType errorType) {
        if (StrKit.isBlank(value)) {
            addError(errorType);
            return;
        }
        value = value.trim().toLowerCase();
        if ("1".equals(value) || "true".equals(value)) {
            return;
        } else if ("0".equals(value) || "false".equals(value)) {
            return;
        }
        addError(errorType);
    }
}
