package com.thakurintl.uizprestashopnew.objects

import com.thakurintl.uizprestashopnew.dataclass.ProductListModel
import com.thakurintl.uizprestashopnew.modelclass.CombinationListModel
import java.math.BigDecimal
import java.math.RoundingMode

object UtilityObjects {

    var totalCartAmount = 0.0

    const val API_KEY = "YEU8K8BYIQC789XX9J5I3H3856PEH95T"
    const val SERVER_COOKIE_KEY = "4genhaszdb8z0n8gfsigf3bzjwoswfijopusgwic7ve3od6kyxnz4hau"

    //These objects saved for later use
    var savedCombination: CombinationListModel? = null
    var savedProducts: ProductListModel? = null


    fun truncateDecimal(value: Double, pointofTruncate: Int): BigDecimal {
        return if (value > 0) {
            BigDecimal(value.toString()).setScale(
                pointofTruncate,
                BigDecimal.ROUND_FLOOR
            )
        } else {
            BigDecimal(value.toString()).setScale(pointofTruncate, RoundingMode.CEILING)
        }
    }

    fun truncateProductName(name: String): String {
        val stringBuilder = StringBuilder()

        for (i in name.indices) {
            val c = name[i]
            if (c.toString() == "-") {
                break
            }
            stringBuilder.append(c)
        }
        return stringBuilder.toString()
    }

}