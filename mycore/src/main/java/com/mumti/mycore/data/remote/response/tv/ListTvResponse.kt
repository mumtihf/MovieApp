package com.mumti.mycore.data.remote.response.tv

import com.google.gson.annotations.SerializedName

data class ListTvResponse(

        @field:SerializedName("results")
        val tv: List<TvResponse>
)
