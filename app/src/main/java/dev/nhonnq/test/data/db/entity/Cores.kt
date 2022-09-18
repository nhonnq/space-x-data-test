package dev.nhonnq.test.data.db.entity

import com.google.gson.annotations.SerializedName


data class Cores(

    @SerializedName("core") var core: String? = null,
    @SerializedName("flight") var flight: String? = null,
    @SerializedName("gridfins") var gridfins: String? = null,
    @SerializedName("legs") var legs: String? = null,
    @SerializedName("reused") var reused: String? = null,
    @SerializedName("landing_attempt") var landingAttempt: String? = null,
    @SerializedName("landing_success") var landingSuccess: String? = null,
    @SerializedName("landing_type") var landingType: String? = null,
    @SerializedName("landpad") var landpad: String? = null

)