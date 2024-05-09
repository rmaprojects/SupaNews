package com.rmaprojects.newssupabaseapp.data.source.local

import com.chibatching.kotpref.KotprefModel

object LocalUser: KotprefModel() {
    var uuid by nullableStringPref()
    var username by nullableStringPref()
    var imageUrl by nullableStringPref()
    var bio by stringPref("")
}
