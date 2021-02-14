package app.doggy.skillgem_realm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Realmをインスタンス化。
    val realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task: Task? = read()

        if (task != null) {
            titleTextView.setText(task.title)
            contentTextView.setText(task.content)
        }

        saveButton.setOnClickListener {
            val title: String = titleTextView.text.toString()
            val content: String = contentTextView.text.toString()
            save(title, content)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun read(): Task? {
        return realm.where(Task::class.java).findFirst()
    }

    fun save(title: String, content: String) {

        //既に保存されているデータを取得する。
        val task: Task? = read()

        //データベースにデータを書き込む（作成・更新・削除）
        realm.executeTransaction {
            if (task != null) {
                //タスクの更新。
                task.title = title
                task.content = content

            } else {
                //タスクの新規作成。
                //itをRealmの変数として扱える。
                val newTask = it.createObject(Task::class.java)
                newTask.title = title
                newTask.content = content

            }
        }

        //Snackbarを表示。
        //第1引数は表示するView。
        Snackbar.make(container, "保存しました！", Snackbar.LENGTH_SHORT).show()
    }
}