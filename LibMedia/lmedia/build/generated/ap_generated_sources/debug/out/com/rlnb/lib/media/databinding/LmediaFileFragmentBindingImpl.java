package com.rlnb.lib.media.databinding;
import com.rlnb.lib.media.R;
import com.rlnb.lib.media.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LmediaFileFragmentBindingImpl extends LmediaFileFragmentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.v_title_bar_bg, 6);
        sViewsWithIds.put(R.id.ib_close, 7);
        sViewsWithIds.put(R.id.iv_drop_down, 8);
        sViewsWithIds.put(R.id.rv_file, 9);
        sViewsWithIds.put(R.id.v_bottom_bg, 10);
        sViewsWithIds.put(R.id.v_folder_mask, 11);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LmediaFileFragmentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private LmediaFileFragmentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6
            , (com.google.android.material.button.MaterialButton) bindings[2]
            , (com.google.android.material.button.MaterialButton) bindings[1]
            , (android.widget.Button) bindings[3]
            , (android.widget.ImageButton) bindings[7]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[4]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (android.widget.TextView) bindings[5]
            , (android.view.View) bindings[10]
            , (android.view.View) bindings[11]
            , (android.view.View) bindings[6]
            );
        this.btnConfirm.setTag(null);
        this.btnFolder.setTag(null);
        this.btnSwitchSize.setTag(null);
        this.ivLoading.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvLoading.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.vmFile == variableId) {
            setVmFile((com.rlnb.lib.media.ui.vm.FileVm) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setVmFile(@Nullable com.rlnb.lib.media.ui.vm.FileVm VmFile) {
        this.mVmFile = VmFile;
        synchronized(this) {
            mDirtyFlags |= 0x40L;
        }
        notifyPropertyChanged(BR.vmFile);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeVmFileSelectBtnValue((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeVmFileSelectMaxFileSize((androidx.databinding.ObservableInt) object, fieldId);
            case 2 :
                return onChangeVmFileIsConfirmEnabled((androidx.databinding.ObservableBoolean) object, fieldId);
            case 3 :
                return onChangeVmFileSelectFileSize((androidx.databinding.ObservableInt) object, fieldId);
            case 4 :
                return onChangeVmFileIsConfirmBtnShow((androidx.databinding.ObservableBoolean) object, fieldId);
            case 5 :
                return onChangeVmFileIsLoadingShow((androidx.databinding.ObservableBoolean) object, fieldId);
        }
        return false;
    }
    private boolean onChangeVmFileSelectBtnValue(androidx.databinding.ObservableField<java.lang.String> VmFileSelectBtnValue, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmFileSelectMaxFileSize(androidx.databinding.ObservableInt VmFileSelectMaxFileSize, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmFileIsConfirmEnabled(androidx.databinding.ObservableBoolean VmFileIsConfirmEnabled, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmFileSelectFileSize(androidx.databinding.ObservableInt VmFileSelectFileSize, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmFileIsConfirmBtnShow(androidx.databinding.ObservableBoolean VmFileIsConfirmBtnShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmFileIsLoadingShow(androidx.databinding.ObservableBoolean VmFileIsLoadingShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        androidx.databinding.ObservableField<java.lang.String> vmFileSelectBtnValue = null;
        androidx.databinding.ObservableInt vmFileSelectMaxFileSize = null;
        com.rlnb.lib.media.ui.vm.FileVm vmFile = mVmFile;
        boolean vmFileIsConfirmEnabledGet = false;
        androidx.databinding.ObservableBoolean vmFileIsConfirmEnabled = null;
        androidx.databinding.ObservableInt vmFileSelectFileSize = null;
        int vmFileSelectMaxFileSizeGet = 0;
        int vmFileIsConfirmBtnShowViewVISIBLEViewINVISIBLE = 0;
        int vmFileSelectFileSizeGet = 0;
        boolean vmFileIsConfirmBtnShowGet = false;
        androidx.databinding.ObservableBoolean vmFileIsConfirmBtnShow = null;
        java.lang.String btnSwitchSizeAndroidStringLmediaSwitchBtnVmFileSelectFileSizeVmFileSelectMaxFileSize = null;
        boolean vmFileIsLoadingShowGet = false;
        int vmFileIsLoadingShowViewVISIBLEViewGONE = 0;
        java.lang.String vmFileSelectBtnValueGet = null;
        androidx.databinding.ObservableBoolean vmFileIsLoadingShow = null;

        if ((dirtyFlags & 0xffL) != 0) {


            if ((dirtyFlags & 0xc1L) != 0) {

                    if (vmFile != null) {
                        // read vmFile.selectBtnValue
                        vmFileSelectBtnValue = vmFile.getSelectBtnValue();
                    }
                    updateRegistration(0, vmFileSelectBtnValue);


                    if (vmFileSelectBtnValue != null) {
                        // read vmFile.selectBtnValue.get()
                        vmFileSelectBtnValueGet = vmFileSelectBtnValue.get();
                    }
            }
            if ((dirtyFlags & 0xcaL) != 0) {

                    if (vmFile != null) {
                        // read vmFile.selectMaxFileSize
                        vmFileSelectMaxFileSize = vmFile.getSelectMaxFileSize();
                        // read vmFile.selectFileSize
                        vmFileSelectFileSize = vmFile.getSelectFileSize();
                    }
                    updateRegistration(1, vmFileSelectMaxFileSize);
                    updateRegistration(3, vmFileSelectFileSize);


                    if (vmFileSelectMaxFileSize != null) {
                        // read vmFile.selectMaxFileSize.get()
                        vmFileSelectMaxFileSizeGet = vmFileSelectMaxFileSize.get();
                    }
                    if (vmFileSelectFileSize != null) {
                        // read vmFile.selectFileSize.get()
                        vmFileSelectFileSizeGet = vmFileSelectFileSize.get();
                    }


                    // read @android:string/lmedia_switch_btn
                    btnSwitchSizeAndroidStringLmediaSwitchBtnVmFileSelectFileSizeVmFileSelectMaxFileSize = btnSwitchSize.getResources().getString(R.string.lmedia_switch_btn, vmFileSelectFileSizeGet, vmFileSelectMaxFileSizeGet);
            }
            if ((dirtyFlags & 0xc4L) != 0) {

                    if (vmFile != null) {
                        // read vmFile.isConfirmEnabled
                        vmFileIsConfirmEnabled = vmFile.isConfirmEnabled();
                    }
                    updateRegistration(2, vmFileIsConfirmEnabled);


                    if (vmFileIsConfirmEnabled != null) {
                        // read vmFile.isConfirmEnabled.get()
                        vmFileIsConfirmEnabledGet = vmFileIsConfirmEnabled.get();
                    }
            }
            if ((dirtyFlags & 0xd0L) != 0) {

                    if (vmFile != null) {
                        // read vmFile.isConfirmBtnShow
                        vmFileIsConfirmBtnShow = vmFile.isConfirmBtnShow();
                    }
                    updateRegistration(4, vmFileIsConfirmBtnShow);


                    if (vmFileIsConfirmBtnShow != null) {
                        // read vmFile.isConfirmBtnShow.get()
                        vmFileIsConfirmBtnShowGet = vmFileIsConfirmBtnShow.get();
                    }
                if((dirtyFlags & 0xd0L) != 0) {
                    if(vmFileIsConfirmBtnShowGet) {
                            dirtyFlags |= 0x200L;
                    }
                    else {
                            dirtyFlags |= 0x100L;
                    }
                }


                    // read vmFile.isConfirmBtnShow.get() ? View.VISIBLE : View.INVISIBLE
                    vmFileIsConfirmBtnShowViewVISIBLEViewINVISIBLE = ((vmFileIsConfirmBtnShowGet) ? (android.view.View.VISIBLE) : (android.view.View.INVISIBLE));
            }
            if ((dirtyFlags & 0xe0L) != 0) {

                    if (vmFile != null) {
                        // read vmFile.isLoadingShow
                        vmFileIsLoadingShow = vmFile.isLoadingShow();
                    }
                    updateRegistration(5, vmFileIsLoadingShow);


                    if (vmFileIsLoadingShow != null) {
                        // read vmFile.isLoadingShow.get()
                        vmFileIsLoadingShowGet = vmFileIsLoadingShow.get();
                    }
                if((dirtyFlags & 0xe0L) != 0) {
                    if(vmFileIsLoadingShowGet) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }


                    // read vmFile.isLoadingShow.get() ? View.VISIBLE : View.GONE
                    vmFileIsLoadingShowViewVISIBLEViewGONE = ((vmFileIsLoadingShowGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0xc4L) != 0) {
            // api target 1

            this.btnConfirm.setEnabled(vmFileIsConfirmEnabledGet);
        }
        if ((dirtyFlags & 0xd0L) != 0) {
            // api target 1

            this.btnConfirm.setVisibility(vmFileIsConfirmBtnShowViewVISIBLEViewINVISIBLE);
        }
        if ((dirtyFlags & 0xc1L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.btnFolder, vmFileSelectBtnValueGet);
        }
        if ((dirtyFlags & 0xcaL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.btnSwitchSize, btnSwitchSizeAndroidStringLmediaSwitchBtnVmFileSelectFileSizeVmFileSelectMaxFileSize);
        }
        if ((dirtyFlags & 0xe0L) != 0) {
            // api target 1

            this.ivLoading.setVisibility(vmFileIsLoadingShowViewVISIBLEViewGONE);
            this.tvLoading.setVisibility(vmFileIsLoadingShowViewVISIBLEViewGONE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): vmFile.selectBtnValue
        flag 1 (0x2L): vmFile.selectMaxFileSize
        flag 2 (0x3L): vmFile.isConfirmEnabled
        flag 3 (0x4L): vmFile.selectFileSize
        flag 4 (0x5L): vmFile.isConfirmBtnShow
        flag 5 (0x6L): vmFile.isLoadingShow
        flag 6 (0x7L): vmFile
        flag 7 (0x8L): null
        flag 8 (0x9L): vmFile.isConfirmBtnShow.get() ? View.VISIBLE : View.INVISIBLE
        flag 9 (0xaL): vmFile.isConfirmBtnShow.get() ? View.VISIBLE : View.INVISIBLE
        flag 10 (0xbL): vmFile.isLoadingShow.get() ? View.VISIBLE : View.GONE
        flag 11 (0xcL): vmFile.isLoadingShow.get() ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}