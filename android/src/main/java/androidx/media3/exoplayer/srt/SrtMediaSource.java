package androidx.media3.exoplayer.srt;

import androidx.media3.common.C.CONTENT_TYPE_OTHER;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;



/**
 * A custom media source factory that uses {@link SrtDataSourceFactory} and {@link TsOnlyExtractorFactory}.
 */
@UnstableApi
public class SrtMediaSource {

    @UnstableApi
    public static class Factory implements MediaSource.Factory {

        private final DataSource.Factory mediaDataSourceFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private boolean allowChunklessPreparation = false;

        /**
         * Default constructor using our own SRT DataSource factory.
         */
        public Factory() {
            // Use our specialized SRT data source by default.
            this.mediaDataSourceFactory = new SrtDataSourceFactory();
        }

        /**
         * A constructor that allows injecting any DataSource.Factory (e.g., if you want to pass
         * a cached data source factory, a custom one, etc.).
         */
        public Factory(DataSource.Factory mediaDataSourceFactory) {
            this.mediaDataSourceFactory = mediaDataSourceFactory;
        }

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
            // We say it's for CONTENT_TYPE_OTHER, so that ExoPlayer knows
            // when to use this Factory.
            return new int[]{CONTENT_TYPE_OTHER};
        }

        /**
         * Actually build our MediaSource using the custom DataSource & Extractor factories.
         */
        @OptIn(markerClass =  UnstableApi.class)
        @Override
        public MediaSource createMediaSource(MediaItem mediaItem) {
            ProgressiveMediaSource.Factory progressiveFactory =
                new ProgressiveMediaSource.Factory(
                    mediaDataSourceFactory,          // The DataSource factory you specified
                    new TsOnlyExtractorFactory()     // Our custom TS extractor that uses DummySubtitleParserFactory
                );
            
            // If you need to set load error handling or DRM, do it here:
            if (drmSessionManagerProvider != null) {
                progressiveFactory.setDrmSessionManagerProvider(drmSessionManagerProvider);
            }
            if (loadErrorHandlingPolicy != null) {
                progressiveFactory.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
            }

            // ProgressiveMediaSource doesn’t directly have 'chunkless preparation,' 
            // but if you needed to do special things based on allowChunklessPreparation, 
            // you could do it here (for example, pass flags to the constructor).

            return progressiveFactory.createMediaSource(mediaItem);
        }

        public Factory setAllowChunklessPreparation(boolean allowChunklessPreparation) {
            this.allowChunklessPreparation = allowChunklessPreparation;
            return this;
        }
    }
}