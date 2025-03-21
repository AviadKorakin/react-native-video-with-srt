package com.srtplayer.player;

import static androidx.media3.common.C.CONTENT_TYPE_OTHER;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;

public class SrtMediaSource {

    @UnstableApi
    public static class Factory implements MediaSource.Factory {

        private DrmSessionManagerProvider drmSessionManagerProvider;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private boolean allowChunklessPreparation = false;

        @Override
        public MediaSource.Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
            this.drmSessionManagerProvider = drmSessionManagerProvider;
            return this;
        }

        @Override
        public MediaSource.Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            return this;
        }

        @Override
        public int[] getSupportedTypes() {
            return new int[]{CONTENT_TYPE_OTHER};
        }

        @OptIn(markerClass = UnstableApi.class)
        @Override
        public MediaSource createMediaSource(MediaItem mediaItem) {
            return new ProgressiveMediaSource.Factory(
                    new SrtDataSourceFactory(),
                    new TsOnlyExtractorFactory()
            ).createMediaSource(mediaItem);
        }

        public Factory setAllowChunklessPreparation(boolean allowChunklessPreparation) {
            this.allowChunklessPreparation = allowChunklessPreparation;
            return this;
        }
    }
}