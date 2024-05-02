package saschpe.log4k.internal

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

/**
 * Hidden initialization content provider.
 *
 * Does not provide real content but hides initialization boilerplate from the library user.
 *
 * @link https://firebase.googleblog.com/2016/12/how-does-firebase-initialize-on-android.html
 */
internal class ContextProvider : ContentProvider() {
    /**
     * Called exactly once before Application.onCreate()
     */
    override fun onCreate(): Boolean {
        applicationContext = context?.applicationContext ?: throw Exception("Need the context")
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        args: Array<out String>?,
        sortOrder: String?,
    ): Cursor? = null

    override fun update(uri: Uri, values: ContentValues?, selection: String?, args: Array<out String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, args: Array<out String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var applicationContext: Context
    }
}
