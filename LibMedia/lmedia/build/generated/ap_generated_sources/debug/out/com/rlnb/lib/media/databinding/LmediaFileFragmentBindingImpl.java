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
        sViewsWithIds.put(R.id.v_title_bar_bg, 5);
        sViewsWithIds.put(R.id.mbtn_close, 6);
        sViewsWithIds.put(R.id.iv_drop_down, 7);
        sViewsWithIds.put(R.id.rv_file, 8);
        sViewsWithIds.put(R.id.v_bottom_bg, 9);
        sViewsWithIds.put(R.id.btn_switch_size, 10);
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
        super(bindingComponent, root, 3
            , (com.google.android.material.button.MaterialButton) bindings[2]
            , (com.google.android.material.button.MaterialButton) bindings[1]
            , (android.widget.Button) bindings[10]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageButton) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[8]
            , (android.widget.TextView) bindings[4]
            , (android.view.View) bindings[9]
            , (android.view.View) bindings[11]
            , (android.view.View) bindings[5]
            );
        this.btnConfirm.setTag(null);
        this.btnFolder.setTag(null);
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
                mDirtyFlags = 0x10L;
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
            mDirtyFlags |= 0x8L;
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
                return onChangeVmFileIsConfirmEnabled((androidx.databinding.ObservableBoolean) object, fieldId);
            case 2 :
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
    private boolean onChangeVmFileIsConfirmEnabled(androidx.databinding.ObservableBoolean VmFileIsConfirmEnabled, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeVmFileIsLoadingShow(androidx.databinding.ObservableBoolean VmFileIsLoadingShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
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
        com.rlnb.lib.media.ui.vm.FileVm vmFile = mVmFile;
        boolean vmFileIsConfirmEnabledGet = false;
        androidx.databinding.ObservableBoolean vmFileIsConfirmEnabled = null;
        boolean vmFileIsLoadingShowGet = false;
        int vmFileIsLoadingShowViewVISIBLEViewGONE = 0;
        java.lang.String vmFileSelectBtnValueGet = null;
        androidx.databinding.ObservableBoolean vmFileIsLoadingShow = null;

        if ((dirtyFlags & 0x1fL) != 0) {


            if ((dirtyFlags & 0x19L) != 0) {

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
            if ((dirtyFlags & 0x1aL) != 0) {

                    if (vmFile != null) {
                        // read vmFile.isConfirmEnabled
                        vmFileIsConfirmEnabled = vmFile.isConfirmEnabled();
                    }
                    updateRegistration(1, vmFileIsConfirmEnabled);


                    if (vmFileIsConfirmEnabled != null) {
                        // read vmFile.isConfirmEnabled.get()
                        vmFileIsConfirmEnabledGet = vmFileIsConfirmEnabled.get();
                    }
            }
            if ((dirtyFlags & 0x1cL) != 0) {

                    if (vmFile != null) {
                        // read vmFile.isLoadingShow
                        vmFileIsLoadingShow = vmFile.isLoadingShow();
                    }
                    updateRegistration(2, vmFileIsLoadingShow);


                    if (vmFileIsLoadingShow != null) {
                        // read vmFile.isLoadingShow.get()
                        vmFileIsLoadingShowGet = vmFileIsLoadingShow.get();
                    }
                if((dirtyFlags & 0x1cL) != 0) {
                    if(vmFileIsLoadingShowGet) {
                            dirtyFlags |= 0x40L;
                    }
                    else {
                            dirtyFlags |= 0x20L;
                    }
                }


                    // read vmFile.isLoadingShow.get() ? View.VISIBLE : View.GONE
                    vmFileIsLoadingShowViewVISIBLEViewGONE = ((vmFileIsLoadingShowGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0x1aL) != 0) {
            // api target 1

            this.btnConfirm.setEnabled(vmFileIsConfirmEnabledGet);
        }
        if ((dirtyFlags & 0x19L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.btnFolder, vmFileSelectBtnValueGet);
        }
        if ((dirtyFlags & 0x1cL) != 0) {
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
        flag 1 (0x2L): vmFile.isConfirmEnabled
        flag 2 (0x3L): vmFile.isLoadingShow
        flag 3 (0x4L): vmFile
        flag 4 (0x5L): null
        flag 5 (0x6L): vmFile.isLoadingShow.get() ? View.VISIBLE : View.GONE
        flag 6 (0x7L): vmFile.isLoadingShow.get() ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}