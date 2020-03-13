package com.thakurintl.uizprestashopnew.objects

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {

    //To save client details in sharedPreference
    private const val loginSharedPrefName: String = "clientDetailsPref"
    lateinit var editor: SharedPreferences.Editor
    lateinit var context: Context


    //Shared preferences fields
    private const val loginStatus = "login_status"
    private const val ID = "id"
    private const val firstName = "first_name"
    private const val lastName = "last_name"
    private const val Email = "email"
    private const val Password = "password"
    private const val Gender = "gender"

    private const val cartLength = "cart_length"

    @SuppressLint("CommitPrefEdits")
    fun putData(
        context: Context,
        login_status: Boolean,
        id: Int,
        first_name: String,
        last_name: String,
        email: String,
        password: String,
        gender: Int
    ) {

        this.context = context
        editor = context.getSharedPreferences(
            loginSharedPrefName, Context.MODE_PRIVATE
        ).edit()

        editor.putBoolean(
            loginStatus, login_status
        )
        editor.putInt(
            ID, id
        )
        editor.putString(
            firstName, first_name
        )
        editor.putString(
            lastName, last_name
        )
        editor.putString(
            Email, email
        )
        editor.putString(
            Password, password
        )
        editor.putInt(
            Gender, gender
        )

        editor.apply()
        editor.commit()

    }

    @SuppressLint("CommitPrefEdits")
    fun clear(context: Context) {
        this.context = context

        editor = context.getSharedPreferences(
            loginSharedPrefName, Context.MODE_PRIVATE
        ).edit()
        editor.clear()

        editor.apply()
        editor.commit()

    }

    fun getLoginStatus(context: Context) =
        (context.getSharedPreferences(loginSharedPrefName, Context.MODE_PRIVATE).getBoolean(
            loginStatus,
            false
        ))

    @SuppressLint("CommitPrefEdits")
    fun setCartLength(context: Context, cart_length: Int) {
        this.context = context
        editor = context.getSharedPreferences(
            loginSharedPrefName, Context.MODE_PRIVATE
        ).edit()

        editor.putInt(cartLength, cart_length)

        editor.apply()
        editor.commit()
    }

    fun getId(context: Context): Int {
        return context.getSharedPreferences(loginSharedPrefName, Context.MODE_PRIVATE).getInt(ID, 0)
    }

    fun getCartLength(context: Context): Int {
        return context.getSharedPreferences(loginSharedPrefName, Context.MODE_PRIVATE).getInt(
            cartLength, 0
        )
    }

    fun getFirstName(context: Context): String? {
        return context.getSharedPreferences(loginSharedPrefName, Context.MODE_PRIVATE)
            .getString(firstName, "")
    }

    fun getLastName(context: Context): String? {
        return context.getSharedPreferences(loginSharedPrefName, Context.MODE_PRIVATE)
            .getString(lastName, "")
    }

    fun getEmail(context: Context): String? {
        return context.getSharedPreferences(loginSharedPrefName, Context.MODE_PRIVATE)
            .getString(Email, "")
    }

    fun getGender(context: Context): Int? {
        return context.getSharedPreferences(loginSharedPrefName, Context.MODE_PRIVATE)
            .getInt(Gender, 2)
    }

}

