package app.doggy.skillgem_realm

import io.realm.RealmObject

open class Task(
    open var title: String = "",
    open var content: String = ""
): RealmObject()