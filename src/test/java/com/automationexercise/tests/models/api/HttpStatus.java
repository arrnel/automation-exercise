package com.automationexercise.tests.models.api;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class HttpStatus {

    public static final int CONTINUE = 100;
    public static final int SWITCHING_PROTOCOLS = 101;
    public static final int PROCESSING = 102;
    public static final int EARLY_HINT = 103;
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NON_AUTHORITATIVE_INFORMATION = 203;
    public static final int NO_CONTENT = 204;
    public static final int RESET_CONTENT = 205;
    public static final int PARTIAL_CONTENT = 206;
    public static final int MULTI_STATUS = 207;
    public static final int MULTIPLE_CHOICES = 300;
    public static final int MOVED_PERMANENTLY = 301;
    public static final int MOVED_TEMPORARILY = 302;
    public static final int FOUND = 302;
    public static final int SEE_OTHER = 303;
    public static final int NOT_MODIFIED = 304;
    public static final int USE_PROXY = 305;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int PERMANENT_REDIRECT = 308;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int PAYMENT_REQUIRED = 402;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int NOT_ACCEPTABLE = 406;
    public static final int PROXY_AUTHENTICATION_REQUIRED = 407;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int CONFLICT = 409;
    public static final int GONE = 410;
    public static final int LENGTH_REQUIRED = 411;
    public static final int PRECONDITION_FAILED = 412;
    public static final int PAYLOAD_TOO_LARGE = 413;
    public static final int URI_TOO_LONG = 414;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int RANGE_NOT_SATISFIABLE = 416;
    public static final int EXPECTATION_FAILED = 417;
    public static final int IM_A_TEAPOT = 418;
    public static final int ENHANCE_YOUR_CALM = 420;
    public static final int MISDIRECTED_REQUEST = 421;
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int LOCKED = 423;
    public static final int FAILED_DEPENDENCY = 424;
    public static final int UPGRADE_REQUIRED = 426;
    public static final int PRECONDITION_REQUIRED = 428;
    public static final int TOO_MANY_REQUESTS = 429;
    public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
    public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;
    public static final int INSUFFICIENT_STORAGE = 507;
    public static final int LOOP_DETECTED = 508;
    public static final int NOT_EXTENDED = 510;
    public static final int NETWORK_AUTHENTICATION_REQUIRED = 511;

    public static boolean is1xxInfo(Integer statusCode) {
        return isInRange(CONTINUE, EARLY_HINT, statusCode);
    }

    public static boolean is2xxSuccess(Integer statusCode) {
        return isInRange(OK, MULTI_STATUS, statusCode);
    }

    public static boolean is3xxRedirect(Integer statusCode) {
        return isInRange(MULTIPLE_CHOICES, PERMANENT_REDIRECT, statusCode);
    }

    public static boolean is4xxClientError(Integer statusCode) {
        return isInRange(BAD_REQUEST, UNAVAILABLE_FOR_LEGAL_REASONS, statusCode);
    }

    public static boolean is5xxServerError(Integer statusCode) {
        return isInRange(INTERNAL_SERVER_ERROR, NETWORK_AUTHENTICATION_REQUIRED, statusCode);
    }

    private static boolean isInRange(int min, int max, int statusCode) {
        return (statusCode >= min && statusCode <= max);
    }

}
