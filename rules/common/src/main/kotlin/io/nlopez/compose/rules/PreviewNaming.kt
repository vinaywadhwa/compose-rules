// Copyright 2023 Nacho Lopez
// SPDX-License-Identifier: Apache-2.0
package io.nlopez.compose.rules

import io.nlopez.compose.core.ComposeKtConfig
import io.nlopez.compose.core.ComposeKtVisitor
import io.nlopez.compose.core.Emitter
import io.nlopez.compose.core.util.isPreview
import org.jetbrains.kotlin.psi.KtFunction

class PreviewNaming : ComposeKtVisitor {

    override fun visitComposable(
        function: KtFunction,
        autoCorrect: Boolean,
        emitter: Emitter,
        config: ComposeKtConfig,
    ) {
        if (!function.isPreview) return
        val name = function.name ?: return
        if (name.endsWith("Preview")) return

        emitter.report(function, PreviewSuffix, true)
    }

    companion object {
        val PreviewSuffix = """
            Composables annotated with @Preview should end in "Preview".

            See https://mrmans0n.github.io/compose-rules/rules/#preview-composables-naming for more information.
        """.trimIndent()
    }
}
