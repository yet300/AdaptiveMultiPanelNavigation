package com.common

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory

fun provideStoreFactory(): StoreFactory = LoggingStoreFactory(TimeTravelStoreFactory())