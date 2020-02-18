package com.boshra.githubrepo.dataModel

data class Details(var id: Int? = null,
                   var name: String?="Not Available",
                   var full_name: String?="Not Available",
                   var description: String?="Not Available",
                   var stargazers_count: Int?= 0,
                   var watchers_count: Int?= 0,
                   var forks_count: Int?= 0,
                   var open_issues: Int?= 0,
                   var owner: Owner? = null)