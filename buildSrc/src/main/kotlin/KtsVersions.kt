object KtsVersions {

    const val compileSdkVersion = 30
    const val buildToolsVersion ="30.0.0"
    const val minSdkVersion = 19
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"
    const val multiDexEnabled = true


    /** 簽名常量 */
    object Signing {
        /** 簽名文件相對路徑 */
        const val storeFile = "jks/rllib.jks"
        /** 密碼 */
        const val storePassword = "rllib888"
        /** 別名 */
        const val keyAlias = "RlLib"
        /** 別名密碼 */
        const val keyPassword = "rllib888"
    }
}