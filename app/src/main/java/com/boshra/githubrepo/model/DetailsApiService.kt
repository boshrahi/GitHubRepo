package com.boshra.githubrepo.model

import com.boshra.githubrepo.dataModel.Details

interface DetailsApiService {
    fun onDetailsFetched(result: Details)
    fun onDetailsDataFetchError(networkResponse: String)
}