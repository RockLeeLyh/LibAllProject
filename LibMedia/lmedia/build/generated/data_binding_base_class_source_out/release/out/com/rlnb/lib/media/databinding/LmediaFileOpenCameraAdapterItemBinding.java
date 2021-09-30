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

public abstract class LmediaFileOpenCameraAdapterItemBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivCover;

  @NonNull
  public final TextView tvTime;

  protected LmediaFileOpenCameraAdapterItemBinding(Object _bindingComponent, View _root,
      int _localFieldCount, ImageView ivCover, TextView tvTime) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivCover = ivCover;
    this.tvTime = tvTime;
  }

  @NonNull
  public static LmediaFileOpenCameraAdapterItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.lmedia_file_open_camera_adapter_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LmediaFileOpenCameraAdapterItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LmediaFileOpenCameraAdapterItemBinding>inflateInternal(inflater, R.layout.lmedia_file_open_camera_adapter_item, root, attachToRoot, component);
  }

  @NonNull
  public static LmediaFileOpenCameraAdapterItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.lmedia_file_open_camera_adapter_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LmediaFileOpenCameraAdapterItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LmediaFileOpenCameraAdapterItemBinding>inflateInternal(inflater, R.layout.lmedia_file_open_camera_adapter_item, null, false, component);
  }

  public static LmediaFileOpenCameraAdapterItemBinding bind(@NonNull View view) {
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
  public static LmediaFileOpenCameraAdapterItemBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LmediaFileOpenCameraAdapterItemBinding)bind(component, view, R.layout.lmedia_file_open_camera_adapter_item);
  }
}