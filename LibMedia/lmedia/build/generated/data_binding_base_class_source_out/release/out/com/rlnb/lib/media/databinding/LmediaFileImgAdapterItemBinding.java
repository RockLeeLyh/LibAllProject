// Generated by data binding compiler. Do not edit!
package com.rlnb.lib.media.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.rlnb.lib.media.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LmediaFileImgAdapterItemBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivCover;

  @NonNull
  public final TextView tvSize;

  @NonNull
  public final TextView tvSwitch;

  @NonNull
  public final View vSwitchMask;

  protected LmediaFileImgAdapterItemBinding(Object _bindingComponent, View _root,
      int _localFieldCount, ImageView ivCover, TextView tvSize, TextView tvSwitch,
      View vSwitchMask) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivCover = ivCover;
    this.tvSize = tvSize;
    this.tvSwitch = tvSwitch;
    this.vSwitchMask = vSwitchMask;
  }

  @NonNull
  public static LmediaFileImgAdapterItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.lmedia_file_img_adapter_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LmediaFileImgAdapterItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LmediaFileImgAdapterItemBinding>inflateInternal(inflater, R.layout.lmedia_file_img_adapter_item, root, attachToRoot, component);
  }

  @NonNull
  public static LmediaFileImgAdapterItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.lmedia_file_img_adapter_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LmediaFileImgAdapterItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LmediaFileImgAdapterItemBinding>inflateInternal(inflater, R.layout.lmedia_file_img_adapter_item, null, false, component);
  }

  public static LmediaFileImgAdapterItemBinding bind(@NonNull View view) {
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
  public static LmediaFileImgAdapterItemBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LmediaFileImgAdapterItemBinding)bind(component, view, R.layout.lmedia_file_img_adapter_item);
  }
}