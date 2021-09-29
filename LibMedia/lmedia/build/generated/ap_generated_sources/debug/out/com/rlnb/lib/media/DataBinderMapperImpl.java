package com.rlnb.lib.media;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.rlnb.lib.media.databinding.LmediaFileFragmentBindingImpl;
import com.rlnb.lib.media.databinding.LmediaFileImgAdapterItemBindingImpl;
import com.rlnb.lib.media.databinding.LmediaFileOpenCameraAdapterItemBindingImpl;
import com.rlnb.lib.media.databinding.LmediaFileVideoAdapterItemBindingImpl;
import com.rlnb.lib.media.databinding.LmediaFolderAdapterItemBindingImpl;
import com.rlnb.lib.media.databinding.LmediaFolderFragmentBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_LMEDIAFILEFRAGMENT = 1;

  private static final int LAYOUT_LMEDIAFILEIMGADAPTERITEM = 2;

  private static final int LAYOUT_LMEDIAFILEOPENCAMERAADAPTERITEM = 3;

  private static final int LAYOUT_LMEDIAFILEVIDEOADAPTERITEM = 4;

  private static final int LAYOUT_LMEDIAFOLDERADAPTERITEM = 5;

  private static final int LAYOUT_LMEDIAFOLDERFRAGMENT = 6;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(6);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.rlnb.lib.media.R.layout.lmedia_file_fragment, LAYOUT_LMEDIAFILEFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.rlnb.lib.media.R.layout.lmedia_file_img_adapter_item, LAYOUT_LMEDIAFILEIMGADAPTERITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.rlnb.lib.media.R.layout.lmedia_file_open_camera_adapter_item, LAYOUT_LMEDIAFILEOPENCAMERAADAPTERITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.rlnb.lib.media.R.layout.lmedia_file_video_adapter_item, LAYOUT_LMEDIAFILEVIDEOADAPTERITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.rlnb.lib.media.R.layout.lmedia_folder_adapter_item, LAYOUT_LMEDIAFOLDERADAPTERITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.rlnb.lib.media.R.layout.lmedia_folder_fragment, LAYOUT_LMEDIAFOLDERFRAGMENT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_LMEDIAFILEFRAGMENT: {
          if ("layout/lmedia_file_fragment_0".equals(tag)) {
            return new LmediaFileFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for lmedia_file_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_LMEDIAFILEIMGADAPTERITEM: {
          if ("layout/lmedia_file_img_adapter_item_0".equals(tag)) {
            return new LmediaFileImgAdapterItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for lmedia_file_img_adapter_item is invalid. Received: " + tag);
        }
        case  LAYOUT_LMEDIAFILEOPENCAMERAADAPTERITEM: {
          if ("layout/lmedia_file_open_camera_adapter_item_0".equals(tag)) {
            return new LmediaFileOpenCameraAdapterItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for lmedia_file_open_camera_adapter_item is invalid. Received: " + tag);
        }
        case  LAYOUT_LMEDIAFILEVIDEOADAPTERITEM: {
          if ("layout/lmedia_file_video_adapter_item_0".equals(tag)) {
            return new LmediaFileVideoAdapterItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for lmedia_file_video_adapter_item is invalid. Received: " + tag);
        }
        case  LAYOUT_LMEDIAFOLDERADAPTERITEM: {
          if ("layout/lmedia_folder_adapter_item_0".equals(tag)) {
            return new LmediaFolderAdapterItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for lmedia_folder_adapter_item is invalid. Received: " + tag);
        }
        case  LAYOUT_LMEDIAFOLDERFRAGMENT: {
          if ("layout/lmedia_folder_fragment_0".equals(tag)) {
            return new LmediaFolderFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for lmedia_folder_fragment is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "vmFile");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(6);

    static {
      sKeys.put("layout/lmedia_file_fragment_0", com.rlnb.lib.media.R.layout.lmedia_file_fragment);
      sKeys.put("layout/lmedia_file_img_adapter_item_0", com.rlnb.lib.media.R.layout.lmedia_file_img_adapter_item);
      sKeys.put("layout/lmedia_file_open_camera_adapter_item_0", com.rlnb.lib.media.R.layout.lmedia_file_open_camera_adapter_item);
      sKeys.put("layout/lmedia_file_video_adapter_item_0", com.rlnb.lib.media.R.layout.lmedia_file_video_adapter_item);
      sKeys.put("layout/lmedia_folder_adapter_item_0", com.rlnb.lib.media.R.layout.lmedia_folder_adapter_item);
      sKeys.put("layout/lmedia_folder_fragment_0", com.rlnb.lib.media.R.layout.lmedia_folder_fragment);
    }
  }
}
