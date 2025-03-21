package com.srtplayer.player

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource


@UnstableApi
class SrtDataSourceFactory :
    DataSource.Factory {
    override fun createDataSource(): DataSource {
        return SrtDataSource()
    }
}