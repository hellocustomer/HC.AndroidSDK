package com.hellocustomer.sdk.network.dto

internal enum class DialogTypeDto(val code: Long) {
    BOTTOM(0),
    CENTER(1),
    UNKNOWN(-1);

    companion object {
        fun fromInt(type: Long): DialogTypeDto =
            values().find { it.code == type } ?: UNKNOWN
    }
}