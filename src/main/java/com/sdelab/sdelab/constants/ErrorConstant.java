package com.sdelab.sdelab.constants;

public final class ErrorConstant {

    //Status Constants

    public static final int STATUS_400 = 400;
    public static final int STATUS_503 = 503;

    //Error Constants

    public static final String ERROR_BAD_REQUEST = "Bad Request";
    public static final String ERROR_SERVICE_UNAVAILABLE = "Service Unavailable";

    //Code Constants

    public static final String ERROR_CODE_4000= "4000";
    public static final String ERROR_CODE_4001= "4001";

    //Message Constants

    public static final String ERROR_MESSAGE_INVALID_INPUT= "Invalid Input, Latitude should be between -90 to 90 and Longitude between -180 to 180";
    public static final String ERROR_MESSAGE_INTERNAL_SERVER_ERROR= "Internal Server Error";
    public static final String ERROR_MESSAGE_SERVICE_UNAVAILABLE= "External Service Unavailable, Please try again later";

    //Error Type Constants

    public static final String ERROR_TYPE_VALIDATION= "Validation Error";
    public static final String ERROR_TYPE_SERVICE_UNAVAILABLE = "Service Unavailable Error";
}
