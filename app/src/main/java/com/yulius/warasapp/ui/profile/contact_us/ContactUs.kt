package com.yulius.warasapp.ui.profile.contact_us

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    var name: String,
    var email: String,
    var photo: Int
) : Parcelable
