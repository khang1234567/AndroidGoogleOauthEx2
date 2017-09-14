package jp.co.actier.sfa.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.*

import java.io.IOException

object AsyncOkHttpClient {

    private val TAG = this.javaClass.simpleName

    private val SINGLETON = OkHttpClientSingleton.newInstance()

    fun get(url: String, callback: Callback) {
        val request = Request.Builder()
                .url(url)
                .get()
                .build()
        execute(request, callback)
    }

    fun post(url: String, requestBody: RequestBody, callback: Callback){
        val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        execute(request, callback)
    }

    fun execute(request: Request, callback: Callback) {

        object: OkHttpClientAsynTask(){
            override fun doInBackground(vararg params: Object?): Boolean {
                val requestHttp = params[0] as Request
                val callbackHttp = params[1] as Callback
                val mainHandler = Handler(Looper.getMainLooper())

                SINGLETON!!.newCall(requestHttp).enqueue(object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.w(TAG, "Unknown error occurred at http request.", e)
                        mainHandler.post { callbackHttp.onFailure(null, e) }
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        val content = response.body().string()
                        val url = response.request().url()
                        mainHandler.post(Runnable {
                            if (!response.isSuccessful) {
                                Log.w("$TAG($url)", "${response.code() ?: "???"}/${response.message() ?: "-----"}")
                                callbackHttp.onFailure(response, null)
                                return@Runnable
                            }
                            try {
                                Log.d("$TAG($url)", content)
                                callbackHttp.onSuccess(response, content)
                            } catch (e: IOException) {
                                Log.d(e.message, e.toString())
                            }
                        })

                    }
                })
                return super.doInBackground(*params)
            }
        }.execute(request as Object, callback as Object)

    }

    interface Callback {

        fun onFailure(response: Response?, throwable: Throwable?)

        fun onSuccess(response: Response, content: String)
    }
}
