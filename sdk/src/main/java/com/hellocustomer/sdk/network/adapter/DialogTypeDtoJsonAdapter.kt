package com.hellocustomer.sdk.network.adapter

import com.hellocustomer.sdk.network.dto.DialogTypeDto
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class DialogTypeDtoJsonAdapter {
    @ToJson
    fun toJson(type: DialogTypeDto): Long = type.code

    @FromJson
    fun fromJson(value: String): DialogTypeDto = DialogTypeDto.fromInt(value.toLong())
}