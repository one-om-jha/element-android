/*
 * Copyright (c) 2022 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.features.onboarding.ftueauth

import android.content.Context
import androidx.annotation.AttrRes
import im.vector.app.R
import im.vector.app.core.epoxy.charsequence.EpoxyCharSequence
import im.vector.app.core.epoxy.charsequence.toEpoxyCharSequence
import im.vector.app.core.resources.LocaleProvider
import im.vector.app.core.resources.StringProvider
import im.vector.app.core.resources.isEnglishSpeaking
import im.vector.app.features.themes.ThemeUtils
import me.gujun.android.span.span
import javax.inject.Inject

class SplashCarouselStateFactory @Inject constructor(
        private val context: Context,
        private val stringProvider: StringProvider,
        private val localeProvider: LocaleProvider,
) {

    fun create() = SplashCarouselState(listOf(
            SplashCarouselState.Item(
                    R.string.ftue_auth_carousel_1_title.colorTerminatingFullStop(R.attr.colorAccent),
                    R.string.ftue_auth_carousel_body_secure,
                    R.drawable.ic_splash_conversations,
                    R.drawable.bg_carousel_page_1
            ),
            SplashCarouselState.Item(
                    R.string.ftue_auth_carousel_2_title.colorTerminatingFullStop(R.attr.colorAccent),
                    R.string.ftue_auth_carousel_body_control,
                    R.drawable.ic_splash_control,
                    R.drawable.bg_carousel_page_2
            ),
            SplashCarouselState.Item(
                    R.string.ftue_auth_carousel_3_title.colorTerminatingFullStop(R.attr.colorAccent),
                    R.string.ftue_auth_carousel_body_encrypted,
                    R.drawable.ic_splash_secure,
                    R.drawable.bg_carousel_page_3
            ),
            SplashCarouselState.Item(
                    collaborationTitle().colorTerminatingFullStop(R.attr.colorAccent),
                    R.string.ftue_auth_carousel_body_workplace,
                    R.drawable.ic_splash_collaboration,
                    R.drawable.bg_carousel_page_4
            )
    ))

    private fun collaborationTitle(): Int {
        return when {
            localeProvider.isEnglishSpeaking() -> R.string.cut_the_slack_from_teams
            else                               -> R.string.ftue_auth_carousel_title_messaging
        }
    }

    private fun Int.colorTerminatingFullStop(@AttrRes color: Int): EpoxyCharSequence {
        val string = stringProvider.getString(this)
        val fullStop = "."
        val charSequence = if (string.endsWith(fullStop)) {
            span {
                +string.removeSuffix(fullStop)
                span(fullStop) {
                    textColor = ThemeUtils.getColor(context, color)
                }
            }
        } else {
            string
        }
        return charSequence.toEpoxyCharSequence()
    }
}
