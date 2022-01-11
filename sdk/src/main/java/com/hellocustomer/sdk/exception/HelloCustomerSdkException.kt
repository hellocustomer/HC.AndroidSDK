package com.hellocustomer.sdk.exception

import com.hellocustomer.sdk.network.CampaignIsOutOfProductionApiError
import java.util.Locale


public sealed class HelloCustomerSdkException(message: String) : RuntimeException(message)

public data class HelloCustomerHttpException internal constructor(
    public val requestMethod: String,
    public val uri: String,
    public val code: Int,
    public val content: String
) : HelloCustomerSdkException("Exception was thrown during http request")

public class DefaultTranslationNotFoundException internal constructor(locale: Locale)
    : HelloCustomerSdkException("Cannot find default translation for $locale locale")

public class TouchpointCampaignIsNotMobileTypeException: HelloCustomerSdkException("Provided campaign is not mobile type")

public class ApiErrorMessageException(message: String): HelloCustomerSdkException(message)

public class CampaignIsOutOfProductionException(): HelloCustomerSdkException(CampaignIsOutOfProductionApiError)

public class UnauthorizedException: HelloCustomerSdkException("Unauthorized")