
package androidx.media3.exoplayer.srt;

import DummySubtitleParserFactory
import androidx.media3.common.util.UnstableApi
import androidx.media3.extractor.Extractor
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.extractor.ts.TsExtractor
import androidx.media3.exoplayer.srt.DummySubtitleParserFactory


@UnstableApi
class TsOnlyExtractorFactory : ExtractorsFactory {
    override fun createExtractors(): Array<Extractor> = arrayOf(
        TsExtractor(DummySubtitleParserFactory())
    )
}