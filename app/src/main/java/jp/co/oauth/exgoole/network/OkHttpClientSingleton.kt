package jp.co.actier.sfa.network

import android.util.Log
import okhttp3.OkHttpClient

/**
 * Created by kandodin on 2017/09/13.
 */
class OkHttpClientSingleton{
    companion object {
        var okHttpClient: OkHttpClient? = null

        fun newInstance(): OkHttpClient? {
            if(okHttpClient == null) {
                okHttpClient  = OkHttpClient()
            }
            return okHttpClient
        }
    }
}