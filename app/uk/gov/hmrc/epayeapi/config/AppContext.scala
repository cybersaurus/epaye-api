/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.epayeapi.config

import javax.inject.{Inject, Singleton}

import play.api.{Configuration, Environment, Logger}
import uk.gov.hmrc.play.config.inject.DefaultServicesConfig

@Singleton
case class AppContext @Inject() (config: DefaultServicesConfig) {
  val current: Configuration = config.runModeConfiguration
  val env = config.environment.mode

  val appName: String = current.getString("appName").getOrElse(throw new RuntimeException("appName is not configured"))
  val appUrl: String = current.getString("appUrl").getOrElse(throw new RuntimeException("appUrl is not configured"))
  val serviceLocatorUrl: String = config.baseUrl("service-locator")
  val apiContext: String = current.getString(s"api.context").getOrElse(throw new RuntimeException(s"Missing Key $env.api.context"))
  val apiStatus: String = current.getString("api.status").getOrElse(throw new RuntimeException(s"Missing Key $env.api.status"))
  val useSandboxConnectors: Boolean = current.getBoolean("useSandboxConnectors").getOrElse(false)
  val whitelistedApplications: Seq[String] =
    current.getString("whitelistedApplications").getOrElse("").split(",").filter(_.nonEmpty).map(_.trim)

  Logger.info(s"AppContext startup: " +
              s"env=$env " +
              s"appName=$appName " +
              s"appUrl=$appUrl " +
              s"serviceLocatorUrl=$serviceLocatorUrl " +
              s"apiContext=$apiContext " +
              s"apiStatus=$apiStatus " +
              s"useSandboxConnectors=$useSandboxConnectors " +
              s"whitelistedApplications=$whitelistedApplications")
}
