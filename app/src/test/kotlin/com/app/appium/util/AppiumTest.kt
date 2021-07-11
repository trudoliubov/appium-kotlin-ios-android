package com.app.appium.util

import com.app.appium.page.MainPage
import org.openqa.selenium.remote.DesiredCapabilities
import io.appium.java_client.android.AndroidDriver
import java.net.URL
import java.nio.file.Paths
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import java.util.concurrent.TimeUnit

open class AppiumTest {

  val mainPage by lazy { MainPage(driver!!) }


  var os: OS = OS.ANDROID
  var driver: AppiumDriver<MobileElement>? = null

  @BeforeTest
  fun setup() {
    os = OS.valueOf(System.getProperty("platform", OS.ANDROID.name))
    val capabilities = DesiredCapabilities()
    val userDir = System.getProperty("user.dir")
    val serverAddress = URL("http://127.0.0.1:4723/wd/hub")

    if (os == OS.ANDROID) {
      capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.7.1")
      capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
      capabilities.setCapability(MobileCapabilityType.UDID, "11cdd392")
      capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10")
      capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2" )

      val localApp = "android.apk"
      val appPath = Paths.get(userDir, localApp).toAbsolutePath().toString()
      capabilities.setCapability(MobileCapabilityType.APP, appPath)

      driver = AndroidDriver(serverAddress, capabilities)
    } else {
      capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.7.1")
      capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS")
      capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE")
      capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest")

      val localApp = "ios.app"
      val appPath = Paths.get(userDir, localApp).toAbsolutePath().toString()
      capabilities.setCapability("app", appPath)

      driver = IOSDriver(serverAddress, capabilities)
    }

    driver?.let {
      it.manage()?.timeouts()?.implicitlyWait(30, TimeUnit.SECONDS)
    }
  }

  @AfterTest
  fun tearDown() {
    driver?.quit()
  }
}
