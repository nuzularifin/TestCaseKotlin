package com.nuzul.cleantestapp.core.utils

import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T>(
    @SerializedName("success") var status: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: List<T>? = null
)

data class WrappedResponse<T>(
    @SerializedName("success") var status: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: T? = null
)