package com.intellij.settingsSync.jba.auth

import com.intellij.openapi.application.ex.ApplicationManagerEx
import com.intellij.openapi.diagnostic.logger
import com.intellij.settingsSync.SettingsSyncEvents
import com.intellij.settingsSync.auth.SettingsSyncAuthService
import com.intellij.settingsSync.communicator.SettingsSyncUserData
import com.intellij.ui.JBAccountInfoService

internal class JBAAuthService : SettingsSyncAuthService {

  companion object {
    private val LOG = logger<JBAAuthService>()
  }

  @Volatile
  private var invalidatedIdToken: String? = null

  override fun isLoggedIn(): Boolean {
    return isTokenValid(getAccountInfoService()?.idToken)
  }

  private fun isTokenValid(token: String?): Boolean {
    return token != null && token != invalidatedIdToken
  }

  override fun getUserData() = fromJBAData(
      if (ApplicationManagerEx.isInIntegrationTest()) {
        DummyJBAccountInfoService.userData
      } else {
        getAccountInfoService()?.userData
      }
    )

  private fun fromJBAData(jbaData: JBAccountInfoService.JBAData?) : SettingsSyncUserData {
    if (jbaData == null) {
      return SettingsSyncUserData.EMPTY
    } else {
      return SettingsSyncUserData(
        jbaData.id,
        jbaData.email,
        jbaData.loginName
      )
    }
  }

  val idToken: String?
    get() {
      val token = getAccountInfoService()?.idToken
      if (!isTokenValid(token)) return null
      return token
    }
  override val providerCode: String
    get() = "jba"

  override fun login() {
    if (!isLoggedIn()) {
      getAccountInfoService()?.invokeJBALogin(
        {
          SettingsSyncEvents.Companion.getInstance().fireLoginStateChanged()
        },
        {
          SettingsSyncEvents.Companion.getInstance().fireLoginStateChanged()
        })
    }
  }

  override fun isLoginAvailable(): Boolean = getAccountInfoService() != null

  fun invalidateJBA(idToken: String) {
    if (invalidatedIdToken == idToken) return

    LOG.warn("Invalidating JBA Token")
    invalidatedIdToken = idToken
    SettingsSyncEvents.Companion.getInstance().fireLoginStateChanged()
  }

  // Extracted to simplify testing
  fun getAccountInfoService(): JBAccountInfoService? {
    if (ApplicationManagerEx.isInIntegrationTest() || System.getProperty("settings.sync.test.auth") == "true") {
      return DummyJBAccountInfoService
    }
    return JBAccountInfoService.getInstance()
  }
}