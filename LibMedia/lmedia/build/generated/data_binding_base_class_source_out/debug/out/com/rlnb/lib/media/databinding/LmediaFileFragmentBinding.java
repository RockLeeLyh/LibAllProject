// Generated by data binding compiler. Do not edit!
package com.rlnb.lib.media.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.rlnb.lib.media.R;
import com.rlnb.lib.media.ui.vm.FileVm;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LmediaFileFragmentBinding extends ViewDataBinding {
  @NonNull
  public final MaterialButton btnConfirm;

  @NonNull
  public final MaterialButton btnFolder;

  @NonNull
  public final Button btnSwitchSize;

  @NonNull
  public final ImageButton ibClose;

  @NonNull
  public final ImageView ivDropDown;

  @NonNull
  public final ImageView ivLoading;

  @NonNull
  public final RecyclerView rvFile;

  @NonNull
  public final TextView tvLoading;

  @NonNull
  public final View vBottomBg;

  @NonNull
  public final View vFolderMask;

  @NonNull
  public final View vTitleBarBg;

  @Bindable
  protected FileVm mVmFile;

  protected LmediaFileFragmentBinding(Object _bindingComponent, View _root, int _localFieldCount,
      MaterialButton btnConfirm, MaterialButton btnFolder, Button btnSwitchSize,
      ImageButton ibClose, ImageView ivDropDown, ImageView ivLoading, RecyclerView rvFile,
      TextView tvLoading, View vBottomBg, View vFolderMask, View vTitleBarBg) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnConfirm = btnConfirm;
    this.btnFolder = btnFolder;
    this.btnSwitchSize = btnSwitchSize;
    this.ibClose = ibClose;
    this.ivDropDown = ivDropDown;
    this.ivLoading = ivLoading;
    this.rvFile = rvFile;
    this.tvLoading = tvLoading;
    this.vBottomBg = vBottomBg;
    this.vFolderMask = vFolderMask;
    this.vTitleBarBg = vTitleBarBg;
  }

  public abstract void setVmFile(@Nullable FileVm vmFile);

  @Nullable
  public FileVm getVmFile() {
    return mVmFile;
  }

  @NonNull
  public static LmediaFileFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.lmedia_file_fragment, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LmediaFileFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LmediaFileFragmentBinding>inflateInternal(inflater, R.layout.lmedia_file_fragment, root, attachToRoot, component);
  }

  @NonNull
  public static LmediaFileFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.lmedia_file_fragment, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LmediaFileFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LmediaFileFragmentBinding>inflateInternal(inflater, R.layout.lmedia_file_fragment, null, false, component);
  }

  public static LmediaFileFragmentBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static LmediaFileFragmentBinding bind(@NonNull View view, @Nullable Object component) {
    return (LmediaFileFragmentBinding)bind(component, view, R.layout.lmedia_file_fragment);
  }
}
