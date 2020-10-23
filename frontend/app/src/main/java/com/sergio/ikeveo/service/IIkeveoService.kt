package com.sergio.ikeveo.service

import android.content.Context
import com.sergio.ikeveo.models.Gafa

interface IIkeveoService {
    fun getAll(context: Context, completionHandler: (response: ArrayList<Gafa>?) -> Unit)

    fun getById(context: Context, gafaId: Int, completionHandler: (response: Gafa?) -> Unit)

    fun deleteById(context: Context, gafaId: Int, completionHandler: () -> Unit)

    fun updateGafa(context: Context, gafa: Gafa, completionHandler: () -> Unit)

    fun createGafa(context: Context, gafa: Gafa, completionHandler: () -> Unit)
}