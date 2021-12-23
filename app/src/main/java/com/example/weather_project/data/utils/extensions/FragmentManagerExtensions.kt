package com.example.weather_project.data.utils.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.detach(containerId: Int) {
    findFragmentById(containerId)?.also {
        beginTransaction().detach(it).commit()
    }
}

fun FragmentManager.attach(fragment: Fragment, tag: String, containerId: Int) {
    if (fragment.isDetached) {
        beginTransaction().attach(fragment).commit()
    } else {
        beginTransaction().add(containerId, fragment, tag).commit()
    }
}

fun String.deleteAllSpaces(): String {
    return replace("\\s".toRegex(), "")
}