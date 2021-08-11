package io.github.thang86.weathertest.customview

import java.lang.reflect.Method


/**
 *
 *    CallBack.kt
 *
 *    Created by ThangTX on 11/08/2021
 *
 */
class CallBack {
    private var methodName: String? = null
    private var scope: Any? = null

    /*
        Sending Third Parameter. For Internet Check.
     */
    fun CallBack(scope: Any?, methodName: String?) {
        this.methodName = methodName
        this.scope = scope
    }


    operator fun invoke(vararg parameters: Any): Any? {
        val method: Method =
            scope!!.javaClass.getMethod(methodName, *getParameterClasses(*parameters))
        return method.invoke(scope, parameters)
    }

    private fun getParameterClasses(vararg parameters: Any): Array<Class<*>?> {
        val classes: Array<Class<*>?> = arrayOfNulls(parameters.size)
        for (i in classes.indices) {
            classes[i] = parameters[i].javaClass
        }
        return classes
    }
}