package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.rlnb.lib.media.app.DataBinderMapperImpl());
  }
}
