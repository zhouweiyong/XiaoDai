package com.vstecs.xiaodai

import android.content.Context
import com.vstecs.xiaodai.base.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dialog_repayment.view.*
import java.util.concurrent.TimeUnit

@Deprecated("该对象只是一个说明文档，请勿使用")
object ReadeMe : ILog {

    fun rxEnd() {
        // 使用 Observable<T>.end() 来订阅，会在 Observable 出现错误时不退出 app
        // 建议通过 doOnNext, doOnError, doOnComplete 等生命周期函数来获取数据或异常
        Observable.timer(2, TimeUnit.SECONDS)
            .doOnNext { debug("...") }
            .end()
    }

    fun rxBus() {
        // 使用 RxBus 来替换 EventBus
        val bus = RxBus.create<Int>()

        // 发送数据
        bus.post(12)

        // 订阅数据
        bus.toObservable()
            .doOnNext { debug("...") }
            .end()
    }

    fun json() {
        // 解析 Json
        val parsedObject: List<Int> = Json.parse("[1, 2, 3]")

        // 得到 Json 文本
        val text = Json.stringify(arrayOf<Int>())
    }

    fun showAlertDialog(context: Context) {
        // 弹出 AlertDialog
        context.alert(R.layout.dialog_repayment) { view ->
            view.vAccount.text = ".."
            view.vAccountName.text = "..."
            setPositiveButton("确定") { dialog, _ -> dialog.dismiss() }
            setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
        }
    }
}