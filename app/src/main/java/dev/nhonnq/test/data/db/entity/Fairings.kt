package dev.nhonnq.test.data.db.entity

import com.google.gson.annotations.SerializedName

data class Fairings(
    @SerializedName("reused") var reused: String? = null,
    @SerializedName("recovery_attempt") var recoveryAttempt: String? = null,
    @SerializedName("recovered") var recovered: String? = null,
    @SerializedName("ships") var ships: ArrayList<String> = arrayListOf()
)