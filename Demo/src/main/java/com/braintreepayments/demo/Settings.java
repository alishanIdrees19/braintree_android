package com.braintreepayments.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    private static final String ENVIRONMENT = "environment";

    private static final String PRODUCTION_BASE_SERVER_URL = "https://executive-sample-merchant.herokuapp.com";
    private static final String PRODUCTION_TOKENIZATION_KEY = "production_t2wns2y2_dfy45jdj3dxkmz5m";

    private static final String SANDBOX_BASE_SERVER_URL = "https://braintree-sample-merchant.herokuapp.com";
    private static final String SANDBOX_TOKENIZATION_KEY = "sandbox_tmxhyf7d_dcpspy2brwdjr3qn";

    private static final String SANDBOX_INDIA_BASE_SERVER_URL = "https://braintree-india-2fa-merchant.herokuapp.com/";

    static final String LOCAL_PAYMENTS_TOKENIZATION_KEY = "sandbox_f252zhq7_hh4cpc39zq4rgjcg";
    static final String PAYPAL_2FA_TOKENIZATION_KEY = "sandbox_bn8fp75g_f38w7q9kcr3zcspd";

    private static SharedPreferences sSharedPreferences;

    public static SharedPreferences getPreferences(Context context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        }

        return sSharedPreferences;
    }

    public static String getEnvironment(Context context) {
        return getPreferences(context).getString(ENVIRONMENT, "sandbox");
    }

    public static void setEnvironment(Context context, String environment) {
        getPreferences(context)
                .edit()
                .putString(ENVIRONMENT, environment)
                .apply();

        DemoApplication.resetApiClient();
    }

    public static String getSandboxUrl() {
        return SANDBOX_BASE_SERVER_URL;
    }

    public static String getEnvironmentUrl(Context context) {
        String environment = getEnvironment(context);
        if ("sandbox".equals(environment)) {
            return SANDBOX_BASE_SERVER_URL;
        } else if ("sandbox-india".equals(environment)) {
            return SANDBOX_INDIA_BASE_SERVER_URL;
        } else if ("production".equals(environment)) {
            return PRODUCTION_BASE_SERVER_URL;
        } else {
            return "";
        }
    }

    public static String getCustomerId(Context context) {
        return getPreferences(context).getString("customer", null);
    }

    public static String getMerchantAccountId(Context context) {
        return getPreferences(context).getString("merchant_account", null);
    }

    public static boolean shouldCollectDeviceData(Context context) {
        return getPreferences(context).getBoolean("collect_device_data", false);
    }

    public static String getThreeDSecureMerchantAccountId(Context context) {
        if (isThreeDSecureEnabled(context) && "production".equals(getEnvironment(context))) {
            return "test_AIB";
        } else {
            return null;
        }
    }

    public static String getUnionPayMerchantAccountId(Context context) {
        if ("sandbox".equals(getEnvironment(context))) {
            return "fake_switch_usd";
        } else {
            return null;
        }
    }

    public static boolean useTokenizationKey(Context context) {
        return getPreferences(context).getBoolean("tokenization_key", false);
    }

    public static String getTokenizationKey(Context context) {
        String environment = getEnvironment(context);

        if ("sandbox".equals(environment)) {
            String type = getPreferences(context).getString("tokenization_key_type", "default_tokenization_key");
            if (context.getString(R.string.local_payments_tokenization_key).equals(type)) {
                return LOCAL_PAYMENTS_TOKENIZATION_KEY;
            } else {
                return SANDBOX_TOKENIZATION_KEY;
            }
        } else if ("sandbox-india".equals(environment)) {
            return PAYPAL_2FA_TOKENIZATION_KEY;
        } else if ("production".equals(environment)) {
            return PRODUCTION_TOKENIZATION_KEY;
        } else {
            return null;
        }
    }

    public static boolean areGooglePaymentPrepaidCardsAllowed(Context context) {
        return getPreferences(context).getBoolean("google_payment_allow_prepaid_cards", true);
    }

    public static boolean isGooglePaymentShippingAddressRequired(Context context) {
        return getPreferences(context).getBoolean("google_payment_require_shipping_address", false);
    }

    public static boolean isGooglePaymentBillingAddressRequired(Context context) {
        return getPreferences(context).getBoolean("google_payment_require_billing_address", false);
    }

    public static boolean isGooglePaymentPhoneNumberRequired(Context context) {
        return getPreferences(context).getBoolean("google_payment_require_phone_number", false);
    }

    public static boolean isGooglePaymentEmailRequired(Context context) {
        return getPreferences(context).getBoolean("google_payment_require_email", false);
    }

    public static String getGooglePaymentCurrency(Context context) {
        return getPreferences(context).getString("google_payment_currency", "USD");
    }

    public static String getGooglePaymentMerchantId(Context context) {
        return getPreferences(context).getString("google_payment_merchant_id", "18278000977346790994");
    }

    public static List<String> getGooglePaymentAllowedCountriesForShipping(Context context) {
        String[] preference = getPreferences(context).getString("google_payment_allowed_countries_for_shipping", "US")
                .split(",");
        List<String> countries = new ArrayList<>();
        for(String country : preference) {
            countries.add(country.trim());
        }

        return countries;
    }

    public static String getPayPalIntentType(Context context) {
        return getPreferences(context).getString("paypal_intent_type", context.getString(R.string.paypal_intent_authorize));
    }

    public static String getPayPalDisplayName(Context context) {
        return getPreferences(context).getString("paypal_display_name", null);
    }

    public static String getPayPalLandingPageType(Context context) {
        return getPreferences(context).getString("paypal_landing_page_type", context.getString(R.string.none));
    }

    public static boolean isPayPalUseractionCommitEnabled(Context context) {
        return getPreferences(context).getBoolean("paypal_useraction_commit", false);
    }

    public static boolean isPayPalCreditOffered(Context context) {
        return getPreferences(context).getBoolean("paypal_credit_offered", false);
    }

    public static boolean isPayPalSignatureVerificationDisabled(Context context) {
        return getPreferences(context).getBoolean("paypal_disable_signature_verification", true);
    }

    public static boolean usePayPalAddressOverride(Context context) {
        return getPreferences(context).getBoolean("paypal_address_override", true);
    }

    public static boolean useHardcodedPayPalConfiguration(Context context) {
        return getPreferences(context).getBoolean("paypal_use_hardcoded_configuration", false);
    }

    public static boolean isThreeDSecureEnabled(Context context) {
        return getPreferences(context).getBoolean("enable_three_d_secure", false);
    }

    public static boolean isThreeDSecureRequired(Context context) {
        return getPreferences(context).getBoolean("require_three_d_secure", true);
    }

    public static boolean vaultVenmo(Context context) {
        return getPreferences(context).getBoolean("vault_venmo", true);
    }

    public static boolean isAmexRewardsBalanceEnabled(Context context) {
        return getPreferences(context).getBoolean("amex_rewards_balance", false);
    }
}
