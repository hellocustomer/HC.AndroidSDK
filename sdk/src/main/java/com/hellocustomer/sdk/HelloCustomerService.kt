package com.hellocustomer.sdk

import android.content.Context
import android.net.Uri
import android.os.Handler
import com.hellocustomer.sdk.dialog.DialogType
import com.hellocustomer.sdk.dialog.HelloCustomerBottomSheetDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialogConfig
import com.hellocustomer.sdk.dialog.HelloCustomerDialogImpl
import com.hellocustomer.sdk.exception.CampaignIsOutOfProductionException
import com.hellocustomer.sdk.exception.DefaultTranslationNotFoundException
import com.hellocustomer.sdk.exception.TouchpointCampaignIsNotMobileTypeException
import com.hellocustomer.sdk.locale.UserLocaleProviderImpl
import com.hellocustomer.sdk.mapper.TouchpointMapper
import com.hellocustomer.sdk.network.LanguageDesignUrlBuilder
import com.hellocustomer.sdk.network.QuestionsUrlBuilder
import com.hellocustomer.sdk.network.dto.QuestionDto
import com.hellocustomer.sdk.network.dto.TouchpointDto
import com.hellocustomer.sdk.survey.SurveyUriBuilder
import java.util.Locale
import java.util.concurrent.ExecutorService

internal class HelloCustomerService(
    private val executorService: ExecutorService,
    private val mainThreadHandler: Handler
) {

    fun load(
        context: Context,
        sdkConfig: SdkConfiguration,
        touchpointConfig: HelloCustomerTouchpointConfig,
        onError: (Throwable) -> Unit,
        onSuccess: (HelloCustomerDialog) -> Unit
    ) {
        executorService.submit {
            val userLocale: Locale = UserLocaleProviderImpl(context).get()

            val basePath = Uri.Builder()
                .scheme(sdkConfig.baseApiScheme)
                .authority(sdkConfig.baseApiUrl)
                .appendPath(sdkConfig.apiVersion)
                .appendPath(userLocale.language)
                .appendPath("company")
                .appendPath(touchpointConfig.companyId.toString())
                .appendPath("touchpoint")
                .appendPath(touchpointConfig.touchpointId.toString())
                .build()
                .toString()

            val questionsUrlBuilder = QuestionsUrlBuilder(
                basePath = basePath,
                authorizationToken = touchpointConfig.authorizationToken
            )

            val dialogResult: Result<HelloCustomerDialog> = SdkApi.getQuestions(questionsUrlBuilder)
                .mapCatching { touchpoints: Collection<TouchpointDto> ->
                    val touchPoint = touchpoints.first()
                    if (touchPoint.campaignType != null && touchPoint.campaignType != CampaignTypeMobile) {
                        throw TouchpointCampaignIsNotMobileTypeException()
                    }
                    val question: QuestionDto = touchPoint.findByDefaultLanguage(userLocale)
                        ?: throw DefaultTranslationNotFoundException(userLocale)

                    val designsUrlBuilder = LanguageDesignUrlBuilder(
                        basePath = basePath,
                        authorizationToken = touchpointConfig.authorizationToken
                    )

                    val designDto = SdkApi.getLanguageDesigns(designsUrlBuilder)
                        .getOrThrow()
                        .first { it.languageUniqueId == question.languageUniqueId }

                    val fallbackDialogType: DialogType =
                        if (context.resources.getBoolean(R.bool.isTablet)) DialogType.CENTER else DialogType.BOTTOM

                    TouchpointMapper.toConfig(
                        config = touchpointConfig,
                        firstQuestion = question,
                        languageDesignDto = designDto,
                        fallbackDialogType = fallbackDialogType,
                        surveyUriBuilder = SurveyUriBuilder(
                            companyId = touchpointConfig.companyId,
                            touchpointId = touchpointConfig.touchpointId,
                            metadata = touchpointConfig.metadata,
                            respondentFirstName = touchpointConfig.respondentFirstName,
                            respondentLastName = touchpointConfig.respondentLastName,
                            respondentEmailAddress = touchpointConfig.respondentEmailAddress,
                            userPreferredLanguage = question.languageCulture,
                            baseApiScheme = sdkConfig.baseApiScheme,
                            baseApiUrl = sdkConfig.baseOpinionsUrl
                        )
                    )
                }
                .mapCatching { config: HelloCustomerDialogConfig ->
                    when (config.dialogType) {
                        DialogType.BOTTOM -> HelloCustomerBottomSheetDialog.newInstance(config)
                        DialogType.CENTER -> HelloCustomerDialogImpl.newInstance(config)
                    }
                }

            dialogResult
                .onSuccess {
                    mainThreadHandler.post { onSuccess(it) }
                }
                .onFailure {
                    mainThreadHandler.post { onError(it) }
                }
        }
    }

    fun checkIfTouchpointIsActive(
        context: Context,
        sdkConfig: SdkConfiguration,
        touchpointConfig: HelloCustomerTouchpointConfig,
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        return load(
            context = context,
            sdkConfig = sdkConfig,
            touchpointConfig = touchpointConfig,
            onSuccess = { onSuccess(true) },
            onError = {
                if (it is CampaignIsOutOfProductionException) {
                    onSuccess(false)
                } else {
                    onError(it)
                }
            }
        )

    }

    companion object {

        private const val CampaignTypeMobile = 4
    }
}