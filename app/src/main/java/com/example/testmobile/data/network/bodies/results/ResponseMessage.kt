package com.example.testmobile.data.network.bodies.results

data class ResponseMessage (val status : String, val totalResults : Int, val articles : List<Article>)