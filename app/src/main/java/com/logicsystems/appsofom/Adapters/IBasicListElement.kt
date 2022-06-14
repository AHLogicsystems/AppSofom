package com.logicsystems.appsofom.Adapters

interface IBasicListElement {
    fun getId(): Int
    fun getDescription(): String
    fun getDetailDescription(): String
    fun getDetailDescription2(): String
    fun init(Id: Int, description: String, detailDescription: String, detailDescription2: String)
}