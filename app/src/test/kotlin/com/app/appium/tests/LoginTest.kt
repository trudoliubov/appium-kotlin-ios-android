package com.app.appium.tests

import com.app.appium.page.MainPage
import com.app.appium.util.AppiumTest
import org.testng.Assert.assertTrue
import org.testng.annotations.Test


class LoginTest : AppiumTest() {

  /**
   * User should be able to login
   *
   * Given: I am at login page
   *  Then I login with my name
   *  I should see success status
   */
  @Test
  fun login() {
    //val mainPage = MainPage(driver!!)
    mainPage.loginWith("appium", "1234")
    assertTrue(mainPage.isLoginSuccess())
  }
}
