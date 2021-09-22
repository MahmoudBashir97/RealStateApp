package com.mahmoudbashir.androidunittestingapplying

import android.content.Context

class ResourceComparer {
    fun isEqual(context:Context,resId:Int,st:String):Boolean{
        return context.getString(resId) == st
    }
}