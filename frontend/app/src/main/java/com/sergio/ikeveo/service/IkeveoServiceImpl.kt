package com.sergio.ikeveo.service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest

import com.sergio.ikeveo.models.Gafa

import org.json.JSONObject

class IkeveoServiceImpl : IIkeveoService {
    override fun getAll(context: Context, completionHandler: (response: ArrayList<Gafa>?) -> Unit) {
        val path = IkeveoSingleton.getInstance(context).baseUrl + "/api/gafas"
        val arrayRequest = JsonArrayRequest(
            Request.Method.GET, path, null,
            { response ->
                var gafas: ArrayList<Gafa> = ArrayList()
                for (i in 0 until response.length()) {
                    val gafa = response.getJSONObject(i)
                    val id = gafa.getInt("id")
                    val brand = gafa.getString("brand")
                    val model = gafa.getString("model")
                    gafas.add(Gafa(id, brand, model))
                }
                completionHandler(gafas)
            },
            { error ->
                completionHandler(ArrayList<Gafa>())
            })
        IkeveoSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getById(context: Context, gafaId: Int, completionHandler: (response: Gafa?) -> Unit) {
        val path = IkeveoSingleton.getInstance(context).baseUrl + "/api/gafas/" + gafaId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
            { response ->
                if(response == null) completionHandler(null)

                val id = response.getInt("id")
                val model = response.getString("model")
                val brand = response.getString("brand")

                val gafa = Gafa(id,brand,model)
                completionHandler(gafa)
            },
            { error ->
                completionHandler(null)
            })
        IkeveoSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun deleteById(context: Context, gafaId: Int, completionHandler: () -> Unit) {
        val path = IkeveoSingleton.getInstance(context).baseUrl + "/api/gafas/" + gafaId
        val objectRequest = JsonObjectRequest(Request.Method.DELETE, path, null,
            { response ->
                Log.v("La gafa", "se borró")
                completionHandler()
            },
            { error ->
                Log.v("La gafa", "dió error")
                completionHandler()
            })
        IkeveoSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun updateGafa(context: Context, gafa: Gafa, completionHandler: () -> Unit) {
        val path = IkeveoSingleton.getInstance(context).baseUrl + "/api/gafas/" + gafa.id
        val gafaJson: JSONObject = JSONObject()
        gafaJson.put("id", gafa.id.toString())
        gafaJson.put("brand", gafa.brand)
        gafaJson.put("model", gafa.model)

        val objectRequest = JsonObjectRequest(Request.Method.PUT, path, gafaJson,
            { response ->
                completionHandler()
            },
            { error ->
                completionHandler()
            })
        IkeveoSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createGafa(context: Context, gafa: Gafa, completionHandler: () -> Unit) {
        val path = IkeveoSingleton.getInstance(context).baseUrl + "/api/gafas"
        val gafaJson: JSONObject = JSONObject()
        gafaJson.put("id", gafa.id.toString())
        gafaJson.put("brand", gafa.brand)
        gafaJson.put("model", gafa.model)

        val objectRequest = JsonObjectRequest(Request.Method.POST, path, gafaJson,
            { response -> completionHandler() },
            { error -> completionHandler() })
        IkeveoSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

}