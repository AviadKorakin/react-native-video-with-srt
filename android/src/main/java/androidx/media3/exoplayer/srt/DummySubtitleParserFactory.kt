package androidx.media3.exoplayer.srt;

import androidx.media3.common.Format
import androidx.media3.common.text.Cue
import androidx.media3.common.util.UnstableApi
import androidx.media3.extractor.text.CuesWithTiming
import androidx.media3.extractor.text.SubtitleParser
@UnstableApi
class DummySubtitleParserFactory : SubtitleParser.Factory {
    override fun supportsFormat(format: Format): Boolean {
        // If you don't support subtitles, return false.
        return false
    }

    override fun getCueReplacementBehavior(format: Format): Int {
        return 0
    }

    override fun create(format: Format): SubtitleParser {
        return object : SubtitleParser {

            override fun parse(
                data: ByteArray,
                offset: Int,
                length: Int,
                outputOptions: SubtitleParser.OutputOptions,
                output: androidx.media3.common.util.Consumer<CuesWithTiming>
            ) {
                return;
                }

            override fun getCueReplacementBehavior(): Int {
                return 0;
            }
        }
        }
    }


