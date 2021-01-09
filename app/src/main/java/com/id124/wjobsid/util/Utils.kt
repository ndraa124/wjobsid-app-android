package com.id124.wjobsid.util

import android.graphics.Rect
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun formatDate(date: String): String {
            val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            df.timeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur");

            val result1 = df.parse(date)

            return result1!!.toString().reversed()
        }

        fun rangeMonth(start: String, end: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val startMonth = date.parse(start)
            val endMonth = date.parse(end)

            return "${differenceInMonths(startMonth!!, endMonth!!) + 1}"
        }

        private fun differenceInMonths(d1: Date, d2: Date): Int {
            val c1 = Calendar.getInstance()
            c1.time = d1
            val c2 = Calendar.getInstance()
            c2.time = d2
            var diff = 0
            if (c2.after(c1)) {
                while (c2.after(c1)) {
                    c1.add(Calendar.MONTH, 1)
                    if (c2.after(c1)) {
                        diff++
                    }
                }
            } else if (c2.before(c1)) {
                while (c2.before(c1)) {
                    c1.add(Calendar.MONTH, -1)
                    if (c1.before(c2)) {
                        diff--
                    }
                }
            }
            return diff
        }

        fun currencyFormat(amount: String): String? {
            val formatter = DecimalFormat("#,###")
            return formatter.format(amount.toDouble())
        }

        class BottomOffsetDecoration(private val mBottomOffset: Int) : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val dataSize = state.itemCount
                val position: Int = parent.getChildAdapterPosition(view)
                if (dataSize > 0 && position == dataSize - 1) {
                    outRect[0, 0, 0] = mBottomOffset
                } else {
                    outRect[0, 0, 0] = 0
                }
            }
        }
    }
}