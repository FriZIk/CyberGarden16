package com.crafsed.sas.data

import java.util.Date

data class ListData(
    val date: Date,
    val pairs: List<PairDescription>,
)