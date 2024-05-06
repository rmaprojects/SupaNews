package com.rmaprojects.newssupabaseapp.data.source.local

import com.chibatching.kotpref.KotprefModel

object LocalUser: KotprefModel() {
    var username by nullableStringPref()
    var imageUrl by nullableStringPref()
    var bio by stringPref("")
}
