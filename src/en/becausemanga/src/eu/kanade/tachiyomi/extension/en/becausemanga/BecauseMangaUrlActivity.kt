package eu.kanade.tachiyomi.extension.en.becausemanga

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlin.system.exitProcess

/**
 * Accepts https://manga.becausetrigger.ga/read/manga/xyz intents
 *
 * Added due to requests from various users to allow for opening of titles when given the
 * Guya URL whilst on mobile.
 */
class BecauseMangaUrlActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pathSegments = intent?.data?.pathSegments
        if (pathSegments != null && pathSegments.size >= 3) {
            val slug = pathSegments[2]

            // Gotta do it like this since slug title != actual title
            val mainIntent = Intent().apply {
                action = "eu.kanade.tachiyomi.SEARCH"
                putExtra("query", "${BecauseManga.SLUG_PREFIX}$slug")
                putExtra("filter", packageName)
            }

            try {
                startActivity(mainIntent)
            } catch (e: ActivityNotFoundException) {
                Log.e("BecauseMangaUrlActivity", e.toString())
            }
        } else {
            Log.e("BecauseManagaUrlActivity", "Unable to parse URI from intent $intent")
        }

        finish()
        exitProcess(0)
    }
}
