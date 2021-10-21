package com.rlnb.lib.media.ui.vm

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 * @author RL
 * @version 2021/9/29
 */
class FileVm : ViewModel() {
    /** 确认按钮是否可点，true - 可点 */
    var isConfirmEnabled = ObservableBoolean(false)
    /** 是否顯示確認按鈕，true - 顯示 */
    var isConfirmBtnShow = ObservableBoolean(false)

    /** 是否显示加载提示，true - 显示 */
    var isLoadingShow = ObservableBoolean(true)
    /** 文件夹下拉按钮文本 */
    var selectBtnValue = ObservableField<String>()

    /** 是否显示文件夹列表㵂照层（弹出下拉文件夹框），true - 显示 */
    var isShowFolderMask = MutableLiveData(false)
    /** 選中文件個數 */
    var selectFileSize = ObservableInt(0)
    /** 最大選中文件個數 */
    var selectMaxFileSize = ObservableInt(0)
}