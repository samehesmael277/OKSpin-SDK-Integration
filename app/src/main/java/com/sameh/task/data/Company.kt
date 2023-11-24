package com.sameh.task.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class Companies {
    OKSpin,
    Roulax
}

@Parcelize
data class Company(
    val id: Int,
    val name: String = "",
    val subTitle: String = "",
    val overview: String = "",
    val website: String = "",
    val logo: Int,
    val cover: Int,
    val company: Companies
): Parcelable
