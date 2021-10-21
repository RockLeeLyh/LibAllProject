package com.rlnb.lib.media.enum

import com.rlnb.lib.media.util.MediaStaticVariable

/**
 * 類型
 * https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/media/java/android/media/MediaFile.java
 * @author RL
 * @version 2021/9/29
 */
enum class MediaTypeEnum(val typeName: String, val suffixes: Set<String>) {
    // 圖片
    JPEG("${MediaStaticVariable.IMAGE_PREFIX}/jpeg", mutableSetOf("jpg", "jpeg").toSet()),
    PNG("${MediaStaticVariable.IMAGE_PREFIX}/png", mutableSetOf("png").toSet()),
    GIF("${MediaStaticVariable.IMAGE_PREFIX}/gif", mutableSetOf("gif").toSet()),
    BMP("${MediaStaticVariable.IMAGE_PREFIX}/x-ms-bmp", mutableSetOf("bmp").toSet()),
    WEBP("${MediaStaticVariable.IMAGE_PREFIX}/webp", mutableSetOf("webp").toSet()),

    // 視頻
    MPEG("${MediaStaticVariable.VIDEO_PREFIX}/mpeg", mutableSetOf("mpeg","mpg").toSet()),
    MP4("${MediaStaticVariable.VIDEO_PREFIX}/mp4", mutableSetOf("m4v","mp4").toSet()),
    AVI("${MediaStaticVariable.VIDEO_PREFIX}/avi", mutableSetOf("avi").toSet()),
    QUICKTIME("${MediaStaticVariable.VIDEO_PREFIX}/quicktime", mutableSetOf("mov").toSet()),
    THREEGPP("${MediaStaticVariable.VIDEO_PREFIX}/3gpp2", mutableSetOf("3g2","3gpp2").toSet()),
    MKV("${MediaStaticVariable.VIDEO_PREFIX}/x-matroska", mutableSetOf("mkv").toSet()),
    WEBM("${MediaStaticVariable.VIDEO_PREFIX}/webm", mutableSetOf("webm").toSet()),
    TS("${MediaStaticVariable.VIDEO_PREFIX}/mp2ts", mutableSetOf("ts").toSet()),
}