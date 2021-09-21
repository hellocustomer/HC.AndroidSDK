package com.hellocustomer.sdk.survey

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class SurveyUriBuilder(
    private val companyId: String,
    private val touchpointId: String,
    private val metadata: Map<String, String>,
    private val respondentFirstName: String?,
    private val respondentLastName: String?,
    private val respondentEmailAddress: String?,
    private val userPreferredLanguage: String,
    private val baseApiScheme: String,
    private val baseApiUrl: String,
) : Parcelable {

    fun build(userScore: Int): String = Uri.Builder()
        .scheme(baseApiScheme)
        .authority(baseApiUrl)
        .appendPath(userPreferredLanguage)
        .appendPath("AskAnywhereCampaign")
        .appendPath(companyId)
        .appendPath(touchpointId)
        .apply {
            metadata.forEach { (key, value) -> appendQueryParameter("entry.metadata[$key]", value) }
            respondentFirstName?.let { appendQueryParameter("entry.respondent.firstname", it) }
            respondentLastName?.let { appendQueryParameter("entry.respondent.lastname", it) }
            respondentEmailAddress?.let { appendQueryParameter("entry.respondent.email", it) }
            appendQueryParameter("entry.score", userScore.toString())
        }
        .build()
        .toString()
}