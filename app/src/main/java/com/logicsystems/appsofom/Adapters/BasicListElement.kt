package com.logicsystems.appsofom.Adapters




class BasicListElement : IBasicListElement {
    var ID: Int = 0
    var Descripcion: String = ""
    var DetailDescripcion: String = ""
    var DetailDescripcion2: String = ""

    override fun getId(): Int {
        return this.ID
    }

    override fun getDescription(): String {
        return this.Descripcion
    }

    override fun getDetailDescription(): String {
        return this.DetailDescripcion
    }

    override fun getDetailDescription2(): String {
        return this.DetailDescripcion2
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.ID = Id
        this.Descripcion = description
        this.DetailDescripcion = detailDescription
        this.DetailDescripcion2 = detailDescription2
    }
}