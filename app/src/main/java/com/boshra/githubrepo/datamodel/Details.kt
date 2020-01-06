package com.boshra.githubrepo.dataModel

data class Details(var stargazers_count: Int?= 0,
                   var watchers_count: Int?= 0,
                   var forks_count: Int?= 0,
                   var open_issues: Int?= 0,
                   var owner: Owner? = null) {

}