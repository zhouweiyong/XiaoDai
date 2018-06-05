package com.vstecs.xiaodai.base

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.hardware.SensorManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.PowerManager
import android.os.storage.StorageManager
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

val Context.accessibilityManager: AccessibilityManager get() = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
val Context.accountManager: AccountManager get() = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
val Context.activityManager: ActivityManager get() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
val Context.alarmManager: AlarmManager get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager
val Context.audioManager: AudioManager get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager
val Context.clipboardManager: ClipboardManager get() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
val Context.connectivityManager: ConnectivityManager get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
val Context.devicePolicyManager: DevicePolicyManager get() = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
val Context.downloadManager: DownloadManager get() = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
val Context.inputMethodManager: InputMethodManager get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
val Context.keyguardManager: KeyguardManager get() = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
val Context.locationManager: LocationManager get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager
val Context.nfcManager: NfcManager get() = getSystemService(Context.NFC_SERVICE) as NfcManager
val Context.notificationManager: NotificationManager get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
val Context.powerManager: PowerManager get() = getSystemService(Context.POWER_SERVICE) as PowerManager
val Context.searchManager: SearchManager get() = getSystemService(Context.SEARCH_SERVICE) as SearchManager
val Context.sensorManager: SensorManager get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager
val Context.storageManager: StorageManager get() = getSystemService(Context.STORAGE_SERVICE) as StorageManager
val Context.telephonyManager: TelephonyManager get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
val Context.uiModeManager: UiModeManager get() = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
val Context.usbManager: UsbManager get() = getSystemService(Context.USB_SERVICE) as UsbManager
val Context.wallpaperManager: WallpaperManager get() = getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager
val Context.wifiManager: WifiManager get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
val Context.wifiP2pManager: WifiP2pManager get() = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
val Context.windowManager: WindowManager get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

fun Context.color(@ColorRes colorId: Int): Int = ContextCompat.getColor(this, colorId)
fun Context.colorStateList(@ColorRes colorId: Int): ColorStateList? = ContextCompat.getColorStateList(this, colorId)
fun Context.drawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(this, drawableId)
fun Context.string(@StringRes stringId: Int): String = this.getString(stringId)
fun Context.dimen(@DimenRes dimenId: Int): Int = this.resources.getDimensionPixelSize(dimenId)
val Context.displayMetrics: DisplayMetrics get() = this.resources.displayMetrics

fun View.color(@ColorRes colorId: Int): Int = ContextCompat.getColor(context, colorId)
fun View.colorStateList(@ColorRes colorId: Int): ColorStateList? = ContextCompat.getColorStateList(context, colorId)
fun View.drawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(context, drawableId)
fun View.string(@StringRes stringId: Int): String = context.getString(stringId)
fun View.dimen(@DimenRes dimenId: Int): Int = context.resources.getDimensionPixelSize(dimenId)
val View.displayMetrics: DisplayMetrics get() = context.resources.displayMetrics

private fun Context.px(value: Number, unit: Int): Int = TypedValue.applyDimension(unit, value.toFloat(), this.displayMetrics).toInt()

fun Context.dp(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_DIP)
fun Context.sp(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_SP)
fun Context.pt(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_PT)
fun Context.inch(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_IN)
fun Context.mm(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_MM)

fun View.dp(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_DIP)
fun View.sp(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_SP)
fun View.pt(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_PT)
fun View.inch(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_IN)
fun View.mm(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_MM)

fun Activity.snack(text: CharSequence) {
    Snackbar.make(findViewById<View>(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

fun Activity.longSnack(text: CharSequence) {
    Snackbar.make(findViewById<View>(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
}

fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Activity.hideSoftInput() {
    if (currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
